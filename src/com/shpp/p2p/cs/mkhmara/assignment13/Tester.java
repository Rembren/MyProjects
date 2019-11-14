package com.shpp.p2p.cs.mkhmara.assignment13;

import java.util.Arrays;
import java.util.List;

/**
 * this is a tester application that has the combination of test pictures and expected silhouettes count for them.
 * <p>
 * if every test has passed - everything works as intended.
 * <p>
 * I hope the archive with test images won't exceed the maximum upload size, otherwise this class is useless.
 */
public class Tester {

    private static List<String> test = Arrays.asList("test", "test1", "test3", "test5",
            "test9", "test11", "test12", "test15", "test17", "test18");
    private static List<Integer> results = Arrays.asList(7, 2, 4, 3, 5, 83, 25, 14, 2, 9);

    public static void main(String[] args) {
        for (int repeats = 0; repeats < test.size(); repeats++) {
            BinaryImage binaryImage = new BinaryImage(test.get(repeats) + ".jpg");
            SilhouetteCounter silhouettes = new SilhouetteCounter(binaryImage);
            int result = silhouettes.getCount();
            for (int i = 0; i < silhouettes.getRepeats(); i++) {
                binaryImage.eraseBorders();
            }
            SilhouetteCounter silhouettes2 = new SilhouetteCounter(binaryImage);
            int newCount = silhouettes2.getCount();
            System.out.print((repeats + 1) + ". ");
            if (newCount > result) {
                result = newCount;
            }
            System.out.print(result + " =? " + results.get(repeats));
            if (result == results.get(repeats)) {
                System.out.println("...PASS");
            } else {
                System.out.println("...FAIL");
            }
        }
    }
}
