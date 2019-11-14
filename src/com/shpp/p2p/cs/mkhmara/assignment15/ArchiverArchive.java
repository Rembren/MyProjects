package com.shpp.p2p.cs.mkhmara.assignment15;

import com.shpp.p2p.cs.mkhmara.assignment17.MyHashMap;

import java.io.*;

/**
 * Class made to archive files
 */
public class ArchiverArchive implements ArchiverConstants {

    private int finalSize;
    private MyHashMap<Character, String> archivingTable = new MyHashMap<>();
    private MyHashMap<Character, Integer> frequency = new MyHashMap<>();

    /**
     * Reads the whole file, calls class Huffman tree to generate encodings for each  character based on it's
     * frequency, writes the result into file.
     * @param action set of operation, input name - output name.
     */
    ArchiverArchive(ActionDeterminator action) {
        String fileData = readFileAndFindUniques(action);
        generateEncodingTable();
        byte[] data = generateBinaryLine(fileData);
        archivateFile(data, action);
    }

    /**
     * this method reads the whole file into string and sends it to method that archives data
     *
     * @param action set of action - file input name - file output name
     */
    private String readFileAndFindUniques(ActionDeterminator action) {
        StringBuilder bufferedString = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(action.getStartName()));
            String line;
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                findUniqueCharacters(line + "\n");
                bufferedString.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error while reading the file");
            System.exit(1);
        }
        return (bufferedString.toString());
    }

    /**
     * finds every unique character in the text and adds it to the hashMap with the number of it's repeats
     *
     * @param line full text that was written in the file
     */
    private void findUniqueCharacters(String line) {
        for (int character = 0; character < line.length(); character++) {
            frequency.put(line.charAt(character), frequency.getOrDefault(line.charAt(character), 0) + 1);
        }
    }

    /**
     * Fills hashMap with character - encoding sets.
     */
    private void generateEncodingTable() {
        HuffmanTree encodingTree = new HuffmanTree(frequency);
        archivingTable = encodingTree.getEncodingTable();
    }

    /**
     * generates a string based on encoding of symbols, also generates info about size of encoding table,
     * size of the file and encoding table itself and adds all of them to the string with encoded info
     *
     * @param text text that was written in the file and read into string
     * @return encoded byte array
     */
    private byte[] generateBinaryLine(String text) {
        StringBuilder bitLine = new StringBuilder();
        int zeroCounter = 0;
        //generates an encoded line by changing every symbol in original text with it encoding from the table
        for (int i = 0; i < text.length(); i++) {
            bitLine.append(archivingTable.get(text.charAt(i)));
        }
        //adds additional zeroes to the end of the line, so that it can be transformed into bytes
        while (bitLine.length() % BYTE_SIZE != 0) {
            bitLine.append("0");
            zeroCounter++;
        }
        //transforms the number of zeroes added into binary form to write it in the beginning of the text
        StringBuilder zeros = new StringBuilder(Integer.toBinaryString(zeroCounter));
        while (zeros.length() < BYTE_SIZE) {
            zeros.insert(0, "0");
        }
        bitLine.insert(0, zeros.toString());
        finalSize = (bitLine.length() + SUMMARY_SIZE) / BYTE_SIZE + archivingTable.size() * TABLE_ENTRY_LENGTH;
        //ads extra info concerning the number of added zeros, size of encoding table, size of the file, and the encoding table itself
        bitLine.insert(0, writeEncodingTable());
        //final length of the file in bytes with the heading
        bitLine.insert(0, writeTableAndFileSize(DATA_LENGTH, finalSize));
        //length of the encoding table
        bitLine.insert(0, writeTableAndFileSize(TABLE_LENGTH,
                archivingTable.size() * TABLE_ENTRY_LENGTH));
        byte[] result = new byte[bitLine.length() / BYTE_SIZE];
        //writes every 8 encoded bits into byte
        for (int position = 0; position < result.length; position++) {
            result[position] = (byte) Integer.parseInt(bitLine.substring(position * BYTE_SIZE,
                    (position + 1) * BYTE_SIZE), 2);
        }
        return result;
    }

    /**
     * this method generates an encoded string of given size with given number. made to add info about
     * table and file sizes
     *
     * @param sizeInBits      size of encoded info in bits
     * @param numberToEncrypt number that has to be transformed into bits
     * @return the string with encoded number
     */
    private String writeTableAndFileSize(int sizeInBits, int numberToEncrypt) {
        StringBuilder encryptedNum = new StringBuilder(Integer.toBinaryString(numberToEncrypt));
        while (encryptedNum.length() < sizeInBits) {
            encryptedNum.insert(0, "0");
        }
        return encryptedNum.toString();
    }

    /**
     * creates a string of bits where the info about characters and their encoding is stored
     *
     * @return the string with encoding table
     */
    private String writeEncodingTable() {
        StringBuilder character;
        StringBuilder codeLength;
        StringBuilder result = new StringBuilder();
        for (Character key : archivingTable.keySet()) {
            //the character itself
            character = new StringBuilder(Integer.toBinaryString(key));
            while (character.length() < CHAR_LENGTH) {
                character.insert(0, "0");
            }
            //the frequency
            codeLength = new StringBuilder(Integer.toBinaryString(frequency.get(key)));
            while (codeLength.length() < BYTE_SIZE) {
                codeLength.insert(0, "0");
            }
            result.append(character).append(codeLength);
        }
        return result.toString();
    }


    /**
     * this method gets the information required to be written into file and writes it into file
     *
     * @param fileData array of bytes to be stored in a file
     * @param action   set of action - input file name - output file name
     */
    private void archivateFile(byte[] fileData, ActionDeterminator action) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(action.getFinalName()));
            fileOutputStream.write(fileData);
            fileOutputStream.close();
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write into file");
            System.exit(1);
        }
    }

    /**
     * @return the size of the archived file
     */
    int getArchivedDataSize() {
        return finalSize;
    }

}
