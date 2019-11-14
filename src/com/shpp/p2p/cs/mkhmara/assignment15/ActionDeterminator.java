package com.shpp.p2p.cs.mkhmara.assignment15;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * this class is used to determine the name of the input and output file, and the action that has to be
 * performed to the file
 */
class ActionDeterminator implements ArchiverConstants{

    public String[] fileInfo = new String[3];

    /**
     * gets the set of action - input name - output name
     * @param args arguments that the user input at the star of the program
     */
    ActionDeterminator(String[] args){
        fillActions(args);
    }

    /**
     * fills the array with the name of the action that has to be performed to the file, input file name
     * and output file name depending on the size of String[] args
     * @param args arguments that the user input at the star of the program
     */
    private void fillActions(String[] args) {
        int length = args.length;
        switch (length){
            case 0:
                fileInfo[ACTION] = "archive";
                fileInfo[INPUT_NAME] = "test.txt";
                fileInfo[OUTPUT_NAME] = "test.par";
                break;
            case 1:
                fileInfo[ACTION] = findAction(args[0]);
                fileInfo[INPUT_NAME] = findInputName(args[0]);
                fileInfo[OUTPUT_NAME] = findOutputName(args[0]);
                break;
            case 2:
                fileInfo[ACTION] = findAction(args[0]);
                fileInfo[INPUT_NAME] = findInputName(args[0]);
                fileInfo[OUTPUT_NAME] = findOutputName(args[1]);
                break;
            case 3:
                if (!args[0].equals("-a") && !args[0].equals("-u")){
                    System.err.println("Wrong flag :(");
                    System.exit(1);
                }
                fileInfo[ACTION] = findAction(args[0]);
                fileInfo[INPUT_NAME] = findInputName(args[1]);
                fileInfo[OUTPUT_NAME] = findOutputName(args[2]);
                break;
        }
    }

    /**
     * finds action that has to be performed based on file extension or flag
     * @param arg string on a specific position of String[] args
     * @return the action to be performed
     */
    private String findAction(String arg){
        if(arg.contains(".par") || arg.equals("-u")){
            return "unarchive";
        }
        return "archive";
    }

    /**
     * finds the input name of the file based on the given args. It will ask the user for a name of the file if it is
     * not found.
     * @param arg  string on a specific position of String[] args
     * @return the name of input file
     */
    private String findInputName(String arg) {
        boolean tryAgain = true;
        while (tryAgain) {
            tryAgain = false;
            try {
                FileReader check = new FileReader(arg);
            } catch (FileNotFoundException e) {
                tryAgain = true;
                System.err.println("It seems like the file you're trying to read doesn't exist. Еry writing it again: ");
                Scanner input = new Scanner(System.in);
                arg = input.next();
                fileInfo[ACTION] = findAction(arg);
            }
        }
        return arg;
    }

    /**
     * determines the name of the output file based on the given argument
     * @param arg string on a specific position of String[] args
     * @return the name of the output file
     */
    private String findOutputName(String arg){
        Pattern divider = Pattern.compile("\\.");
        String[] strings = divider.split(arg, 0);
        if (fileInfo[ACTION].equals("unarchive")){
            return strings[0] + ".txt";
        } else {
            return strings[0] + ".par";
        }
    }

    /**
     * @return returns the action that has to be performed to the file
     */
    String getAction(){
        return fileInfo[ACTION];
    }

    /**
     * @return returns the name of the input file
     */
    String getStartName(){
        return  fileInfo[INPUT_NAME];
    }

    /**
     * @return returns the name of the output file
     */
    String getFinalName(){
        return fileInfo[OUTPUT_NAME];
    }

    /**
     * @return the size of the inout file
     */
    long getStartingSize(){
        File file = new File(fileInfo[INPUT_NAME]);
        return file.length();
    }
}
