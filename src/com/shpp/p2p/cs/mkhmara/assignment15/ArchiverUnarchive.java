package com.shpp.p2p.cs.mkhmara.assignment15;

import com.shpp.p2p.cs.mkhmara.assignment17.MyHashMap;

import java.io.*;

/**
 * Class made to unarchive the file.
 *
 * First it reads everything that is in file, then gets info from first 96 bits, creates a frequency table,
 * and huffman tree based on it, decodes data
 */
public class ArchiverUnarchive implements ArchiverConstants{
    //hashmap with frequencies
    private MyHashMap<Character, Integer> frequency = new MyHashMap<>();

    /**
     * Reads the contents of the file into string, decodes it, writes it into file
     * @param action
     */
    ArchiverUnarchive(ActionDeterminator action){
        String encoded = readFile(action.getStartName());
        String result = unarchiveText(encoded);
        printResult(result, action.getFinalName());
    }



    /**
     * reads teh file byte by byte and transforms them into String with their bit presentation,
     * when this method reaches the end of the file it sends the line with bits ti the method that unarchives
     *
     * @param filename name of the file to be read
     */
    private String readFile(String filename) {
        StringBuilder encodedLine = new StringBuilder();

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(filename));
            while (fileInputStream.available() > 0) {
                int currentByte = fileInputStream.read();
                StringBuilder bitSet = new StringBuilder(Integer.toBinaryString(currentByte));
                while (bitSet.length() < BYTE_SIZE) {
                    bitSet.insert(0, "0");
                }
                encodedLine.append(bitSet);
            }
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedLine.toString();
    }

    /**
     * Unarchives file in a form of a string with bits into a normal string with text by
     * 1. finding the length of encoding table
     * 2. getting frequencies from the table
     * 3. sending it to huffman tree to decode
     *
     * @param setOfBits string with bits read from file
     * @return string with unarchived info
     */
    private String unarchiveText(String setOfBits) {

        int tableLength = Integer.parseInt(setOfBits.substring(0, TABLE_LENGTH), 2);
        int dataStart = SUMMARY_SIZE + (BYTE_SIZE * tableLength);

        getFrequencyTable(setOfBits.substring(SUMMARY_SIZE, dataStart), tableLength);

        HuffmanTree tree = new HuffmanTree(frequency);

        String zeros = setOfBits.substring(dataStart, dataStart + BYTE_SIZE);
        int zeroCount = Integer.parseInt(zeros, 2);

        String encodedData = setOfBits.substring(dataStart + BYTE_SIZE, setOfBits.length() - zeroCount);

        return tree.decode(encodedData);
    }

    /**
     * gets the encoding table from substring
     *
     * @param encodingTable the table with encoded chars and their value
     */
    private void getFrequencyTable(String encodingTable, int tableLength) {
        for (int position = 0; position < tableLength; position += TABLE_ENTRY_LENGTH) {
            String keyAndValue = encodingTable.substring(BYTE_SIZE * position, BYTE_SIZE * (position + TABLE_ENTRY_LENGTH));
            char value = (char) Integer.parseInt(keyAndValue.substring(0, CHAR_LENGTH), 2);
            int charFrequency = Integer.parseUnsignedInt(keyAndValue.substring(CHAR_LENGTH, CHAR_LENGTH + BYTE_SIZE), 2);
            frequency.put(value, charFrequency);
        }
    }

    /**
     * Writes the result into file
     *
     * @param result string with all the data to be written
     * @param filename name of a file to write into
     */
    private void printResult(String result, String filename) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
            bufferedWriter.write(result);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
