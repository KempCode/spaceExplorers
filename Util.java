package com.mikeandliam.spaceexplorers;

import java.util.Random;

/**
 * General utility items used throughout the codebase
 */
public class Util {
    /**
     * A global random generator.
     * This is useful because you can make the entire project deterministic for testing by specifying the seed.
     */
    public static Random globalRandom = new Random();

    /**
     * Returns a random element from the specified array.
     * @param <T> The type of objects stored in the array
     */
    public static <T> T getRandomElement(T[] array) {
        return array[globalRandom.nextInt(array.length)];
    }
}
