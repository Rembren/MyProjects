package com.shpp.p2p.cs.mkhmara.assignment13;

/**
 * constants that are used in this program
 */
public interface silhouettesConstants {
    //this coefficients are used to turn any RGB pixel into shade of black.
    //the result of such metamorphosis will be called luminance further in the program.
    public static final double RED_MODIFIER = 0.2125;//0.3;
    public static final double GREEN_MODIFIER = 0.7154;//0.59;
    public static final double BLUE_MODIFIER = 0.0721;//0.11;
    //this is a number  of side checks that we have to make during BFS and DFS
    public static final int NUM_OF_CHECKS = 4;
    // this is a coefficient of transforming biggest object size into a number of erased layers
    public static final int REPEATS_DIVIDER = 15000;

    public static final int MAX_LUMINANCE = 255;
    public static final int BACKGROUND_THRESHOLD = 20;
    // by modifying this constant you are able to choose how big a silhouette should be to be counted.
    public static final double SILHOUETTE_AREA_COEFFICIENT = 0.225;
    // this coefficients modify medium luminance a bit, so that black pixels must be a bit darker that med luminance
    public static final double NORMAL_THRESHOLD_MODIFIER = 1.05;
    public static final double REVERSED_THRESHOLD_MODIFIER = 0.95;
}

