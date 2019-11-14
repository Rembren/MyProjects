package com.shpp.p2p.cs.mkhmara.assignment13;


import com.shpp.p2p.cs.mkhmara.assignment16.MyArrayList;
import com.shpp.p2p.cs.mkhmara.assignment16.MyQueue;

/**
 * this class is used to cunt silhouettes on the image
 */
public class SilhouetteCounter implements silhouettesConstants {


    private boolean[][] visited;
    private int silhouettesNumber;
    //area of currently processed object in pixels
    private int areaCounter;

    private MyQueue<Integer> bfs = new MyQueue<>();

    private int[] columns = {1, 0, -1, 0};
    private int[] rows = {0, 1, 0, -1};

    private BinaryImage image;
    private MyArrayList<Integer> sizes = new MyArrayList<>();

    /**
     * this class counts the number of objects on given binary image and dismisses the junk
     * @param image binary image
     */

    SilhouetteCounter(BinaryImage image) {
        silhouettesNumber = 0;
        areaCounter = 0;
        this.image = image;
        visited = new boolean[image.getHeight()][image.getWidth()];
        startCounting();
        int topSize = findBiggest();
        dismissTheJunk(topSize);
    }

    /**
     * this method is used to throw away the objects that are too small to be silhouettes
     * @param topSize area of the biggest object on the picture
     */
    private void dismissTheJunk(int topSize) {
        for (int size = 0; size < sizes.size(); size++) {
            if (sizes.get(size) > topSize * SILHOUETTE_AREA_COEFFICIENT) {
                silhouettesNumber++;
            } else {
                sizes.remove(size);
                size--;
            }
        }
    }

    /**
     * finds the area biggest object on the picture
     * @return the area of the biggest object on the picture
     */
    public int findBiggest() {
        int max = 0;
        for (Integer size : sizes) {
            if (size > max) {
                max = size;
            }
        }
        return max;
    }

    /**
     * this method returns the area of the smallest object on the picture after the cleanup
     * @return the area of the smallest object
     */
    public int findSmallest(){
        int min = sizes.get(0);
        for (Integer size : sizes){
            if (size < min){
                min = size;
            }
        }
        return min;
    }



    /**
     * this method goes through all the pixels on the image and the moment it touches silhouette the first time it starts
     * a deep first sears (DFS) to mark and find an area that this object occupies
     */
    private void startCounting() {
        boolean searchWithDfs = false;
        for (int row = 0; row < image.getHeight(); row++) {
            for (int col = 0; col < image.getWidth(); col++) {
                if (image.isBlack(col, row) && !visited[row][col]) {
                    if (searchWithDfs){
                        DFS(col, row);
                    } else {
                        visited[row][col] = true;
                        BFS(col, row);
                    }
                    sizes.add(areaCounter);
                    areaCounter = 0;
                }
            }
        }
    }

    /**
     * this method preforms a deep first search (DFS) starting from a given pixel
     *
     * @param col x coordinate fo a given pixel
     * @param row y coordinate of a given pixel
     */
    private void DFS(int col, int row) {
        areaCounter++;
        visited[row][col] = true;
        //checks the pixel to the right from the current. if it is black and unmarked adds it's coordinates to the stack
        if (image.isBlack(col + 1, row) && !visited[row][col + 1]) {
            DFS(col + 1, row);
        }
        //checks the pixel bellow the current. if it is black and unvisited adds it's coordinates to the stack
        if (image.isBlack(col, row + 1) && !visited[row + 1][col]) {
            DFS(col, row + 1);
        }
        //checks the pixel to the left from the current. if it is black and unvisited adds it's coordinates to the stack
        if (image.isBlack(col - 1, row) && !visited[row][col - 1]) {
            DFS(col - 1, row);
        }
        //checks the pixel above the current. if it is black and unvisited adds it's coordinates to the stack
        if (image.isBlack(col, row - 1) && !visited[row - 1][col]) {
            DFS(col, row - 1);
        }
    }
    /**
     * this method preforms a deep first search (DFS) starting from a given pixel
     *
     * @param col x coordinate fo a given pixel
     * @param row y coordinate of a given pixel
     */
    private void BFS(int col, int row) {
        bfs.add(col);
        bfs.add(row);
        while(!bfs.isEmpty()) {
            col = bfs.remove();
            row = bfs.remove();
            areaCounter++;
            for (int i = 0; i < NUM_OF_CHECKS; i++) {
                //checks the pixel to the right from the current. if it is black and unmarked adds it's coordinates to the stack
                if (image.isBlack(col + columns[i], row + rows[i])
                        && !visited[row + rows[i]][col + columns[i]]) {
                    bfs.add(col + columns[i]);
                    bfs.add(row + rows[i]);
                    visited[row + rows[i]][col + columns[i]] = true;
                }
            }
        }
    }

    /**
     * this method is used to print out the resulting amount of silhouettes on the picture
     */
    public void printNumber() {
        System.out.print("Heyyy, I see " + silhouettesNumber + " silhouette(s) on this picture.");
    }

    /**
     * this method is used to get the amount of silhouettes on the picture
     *
     * @return the amount of silhouettes this class found
     */
    public int getCount() {
        return silhouettesNumber;
    }

    /**
     *This method counts the number of border erases we have to make to count the silhouettes better
     * @return the number of border erases
     */
    public int getRepeats(){
        return findBiggest() / REPEATS_DIVIDER;
    }

}
