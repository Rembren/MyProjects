package com.shpp.p2p.cs.mkhmara.assignment13;


/**
 * This is a program that counts the amount of silhouettes on a picture.
 * <p>
 * Since it is pretty hard to teach a computer to define what exactly is a human silhouette and we are not using
 * extra libraries like OpenCV, the main definition of a silhouette is the object that occupies area, that is bigger
 * than 35% of the biggest object found on the screen.
 */
public class silhouettes {

    /**
     * this method counts the number of silhouettes on the picture.
     *
     * if it finds out that after cleaning the junk there is a silhouette that is more that twice as big as the smallest
     * one it will start erasing the borders of silhouettes. The number of repeats depends on the size of the biggest
     * silhouette on the picture. One repeat for every 10 000 pixels that it occupies.
     * @param args arguments given by user at the start
     */
    public static void main(String[] args) {
        String filename = getFilename(args);
        BinaryImage binaryImage = new BinaryImage(filename);
        SilhouetteCounter silhouettes = new SilhouetteCounter(binaryImage);
        int initialCount = silhouettes.getCount();
//        binaryImage.createJPG("before");
        for (int i = 0; i < silhouettes.getRepeats(); i++){
            binaryImage.eraseBorders();
        }
//        binaryImage.createJPG("after");
        SilhouetteCounter silhouettes2 = new SilhouetteCounter(binaryImage);
        int newCount = silhouettes2.getCount();
        if (newCount > initialCount){
            System.out.println("heyy, i could find " + newCount);
        }  else {
            System.out.println("heyy, i could find " + initialCount);
        }
    }

    /**
     * this method makes the program to work with "test.jpg" file if we have no arguments given
     *
     * @param args arguments that are set by user before starting the program
     * @return the filename that we will be working with
     */
    private static String getFilename(String[] args) {
        if (args.length == 0) {
            return "test.jpg";
        }
        return args[0];
    }
}
