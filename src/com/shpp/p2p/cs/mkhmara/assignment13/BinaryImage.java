package com.shpp.p2p.cs.mkhmara.assignment13;

import com.shpp.p2p.cs.mkhmara.assignment17.MyHashMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

/**
 * this class turns any given image into it's binary version.
 */
public class BinaryImage implements silhouettesConstants {

    //medium luminance of the picture's background
    private int mediumBackgroundLuminance;
    //writable raster of the picture
    private WritableRaster raster;
    private BufferedImage image = null;
    private MyHashMap<Integer, Integer> luminances = new MyHashMap<>();

    private boolean[][] binaryImage;

    /**
     * transforms a given image into boolean array, where false stands for background and true for object
     *
     * @param filename picture's name
     */
    BinaryImage(String filename) {
        File file = new File(filename);

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Whoopsie it seems like the given file name is incorrect");
            System.exit(0);
        }
        raster = image.getRaster();
        normalizeRaster();
        findBackgroundLuminance();
        binaryImage = new boolean[raster.getHeight()][raster.getWidth()];
        createBWImage();
    }

    /**
     * adds alpha chanel to picture formats that don't have such and corrects their pixel color
     */
    private void normalizeRaster() {
        for (int row = 0; row < raster.getHeight(); row++) {
            for (int col = 0; col < raster.getWidth(); col++) {
                int[] pixel = raster.getPixel(col, row, new int[4]);
                if (pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 0) {
                    for (int i = 0; i < 3; i++) {
                        pixel[i] = pixel[3];
                    }
                }
                raster.setPixel(col, row, pixel);
            }
        }
    }

    /**
     * this method finds medium luminance  of the image.
     * basically forms a rectangle created by image's bounds
     */
    private void findBackgroundLuminance() {
        int col;
        int row;
        for (int repeats = 0; repeats < 2; repeats++) {
            col = (repeats == 0 ? 0 : raster.getWidth() - 1);
            for (row = 0; row < raster.getHeight(); row++) {
                putOrIncrement(getLuminance(col, row));
            }
            row = (repeats == 0 ? 0 : raster.getHeight() - 1);
            for (col = 0; col < raster.getWidth(); col++) {
                putOrIncrement(getLuminance(col, row));
            }
        }
        findMostCommon();
    }

    /**
     * finds the most common luminance level and calls it the background luminnce
     */
    private void findMostCommon() {
        int max = 0;
        for (Integer key : luminances.keySet()) {
            if (luminances.get(key) > max) {
                max = luminances.get(key);
                mediumBackgroundLuminance = key;
            }
        }
    }

    /**
     * this method counts how many times we meet each individual luminance while going around the picture's perimeter
     *
     * @param luminance the luminance of a current pixel
     */
    private void putOrIncrement(int luminance) {
        if (luminances.containsKey(luminance)) {
                    luminances.replace(luminance, (luminances.get(luminance) + 1));
        } else {
            luminances.put(luminance, 1);
        }
    }

    /**
     * this method returns a luminance (basically a shade in black and white scale) of the current picture
     *
     * @param col x coordinate of the pixel in the image
     * @param row y coordinate of the pixel in the image
     * @return the shade B&W shade of the pixel
     */
    private int getLuminance(int col, int row) {
        int[] pixel = raster.getPixel(col, row, new int[4]);

        return (int) ((RED_MODIFIER * pixel[0] + GREEN_MODIFIER * pixel[1] + BLUE_MODIFIER * pixel[2]));
    }

    /**
     * this method turns the image into a binary one by comparing each pixel with medium luminance.
     */
    private void createBWImage() {
        for (int row = 0; row < raster.getHeight(); row++) {
            for (int col = 0; col < raster.getWidth(); col++) {
                int[] pixel = raster.getPixel(col, row, new int[4]);
                int luminance;
                luminance = (int) ((RED_MODIFIER * pixel[0] + GREEN_MODIFIER * pixel[1] + BLUE_MODIFIER * pixel[2]));
                if (Math.abs(mediumBackgroundLuminance - luminance) > BACKGROUND_THRESHOLD) {
                    binaryImage[row][col] = true;
                }
            }
        }
    }

    /**
     * this method erases the border pixels of objects, so the overlapped objects could separate
     */
    public void eraseBorders() {
        for (int row = 0; row < binaryImage.length; row++) {
            for (int col = 1; col < binaryImage[0].length - 2; col++) {
                if (binaryImage[row][col] && !(binaryImage[row][col - 1] & binaryImage[row][col + 1])) {
                    binaryImage[row][col] = false;
                    col++;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int col = 0; col < binaryImage[0].length; col++) {
                for (int row = 1; row < binaryImage.length - 2; row++) {
                    if (binaryImage[row][col] && !(binaryImage[row - 1][col] & binaryImage[row + 1][col])) {
                        binaryImage[row][col] = false;
                        row++;
                    }
                }
            }
        }
    }

    /**
     * creates a jpg file that is based on the current binaryImage array
     * @param name the name that we want to give the file
     */
    public void createJPG(String name) {
        for (int row = 0; row < binaryImage.length; row++) {
            for (int col = 0; col < binaryImage[0].length; col++) {
                int[] pixel = new int[4];
                if (binaryImage[row][col]) {
                    for (int i = 0; i < 3; i++) {
                        pixel[i] = 0;
                    }
                } else {
                    for (int i = 0; i < 3; i++) {
                        pixel[i] = MAX_LUMINANCE;
                    }
                }
                raster.setPixel(col, row, pixel);
            }
        }
        image.setData(raster);
        try {
            ImageIO.write(image, "jpg", new File(name + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * this method checks whether the current pixel is black or not.
     *
     * @param col x coordinate of the pixel
     * @param row y coordinate of the pixel
     * @return true of the pixel is black, false if it is not or out of bounds.
     * OOB pixels will never be able to become black. Poor things.
     */
    public boolean isBlack(int col, int row) {
        if (col >= 0 && row >= 0 && col < raster.getWidth() - 1 && row < raster.getHeight() - 1) {
            return binaryImage[row][col];
        }
        return false;
    }

    /**
     * returns the height of the image.
     *
     * @return height of the image in pixels
     */
    public int getHeight() {
        return raster.getHeight();
    }

    /**
     * returns the width of the image.
     *
     * @return width of the image in pixels
     */
    public int getWidth() {
        return raster.getWidth();
    }


}
