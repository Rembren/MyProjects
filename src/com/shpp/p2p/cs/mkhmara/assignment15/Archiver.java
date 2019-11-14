package com.shpp.p2p.cs.mkhmara.assignment15;

/**
 * this class archives a text file into .par file by decreasing the amount of bits that are used to
 * mark a character in a text.
 */
public class Archiver implements ArchiverConstants {
    /**
     * this method gets the name of a file, starts time countdown, stops it when the archiving is finished
     * and outputs the data about the archiving process
     *
     * @param args argument that were written by user at the start of the program
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ActionDeterminator action = new ActionDeterminator(args);
        ArchiverArchive archive = null;
        if (action.getAction().equals("archive")){
            archive = new ArchiverArchive(action);
        } else {
            ArchiverUnarchive unarchive = new ArchiverUnarchive(action);
        }
        System.out.println(action.getAction() + " - " + action.getStartName() + " - " + action.getFinalName());
        long finishTime = System.currentTimeMillis();
        System.out.print("The execution of this program took " + (finishTime - startTime) + " milliseconds, " +
                "the initial size was " + action.getStartingSize() + " bytes");
        if (action.getAction().equals("archive")) {
            System.out.println(", size of archived data is " +
                    archive.getArchivedDataSize() + " bytes, which is " +
                    ((float) archive.getArchivedDataSize() / action.getStartingSize() * HUNDRED_PERCENT)
                    + "% of the initial size");
        }
    }
}
