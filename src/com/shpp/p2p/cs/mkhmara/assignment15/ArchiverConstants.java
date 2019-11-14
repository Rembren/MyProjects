package com.shpp.p2p.cs.mkhmara.assignment15;

public interface ArchiverConstants {
    //the position of the element that shows the action in the actionDeterminator array
    public static final int ACTION = 0;
    //the position of the element that shows the input name in the actionDeterminator array
    public static final int INPUT_NAME = 1;
    //the position of the element that shows the output name in the actionDeterminator array
    public static final int OUTPUT_NAME = 2;
    //size of byte in bits
    public static final int BYTE_SIZE = 8;
    //size of data concerning table length in bits
    public static final int TABLE_LENGTH =  32;
    //size of data concerning file length in bits
    public static final int DATA_LENGTH = 64;
    //amount of bytes that hold character - encoding length - bit value set
    public static final int TABLE_ENTRY_LENGTH = 3;
    //summary size of TABLE_LENGTH_INFO_SIZE and DATA_LENGTH_INFO_SIZE
    public static final int SUMMARY_SIZE = 96;
    //length of char in bits
    public static final int CHAR_LENGTH = 16;

    public static final float HUNDRED_PERCENT = 100.0f;
}
