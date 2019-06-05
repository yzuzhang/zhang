package com.feicent.zhang.util;

import java.security.SecureRandom;

/**
 * @date: 2019/6/5 10:12
 *  * @desc:
 */
public class RandomUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static boolean nextBoolean() {
        return RANDOM.nextBoolean();
    }

    public static byte[] nextBytes(int count) {
        isTrue(count >= 0, "Count cannot be negative.", new Object[0]);

        byte[] result = new byte[count];
        RANDOM.nextBytes(result);
        return result;
    }

    public static int nextInt(int startInclusive, int endExclusive) {
        if (startInclusive == endExclusive) {
            return startInclusive;
        }

        return startInclusive + RANDOM.nextInt(endExclusive - startInclusive);
    }

    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }

    public static long nextLong(long startInclusive, long endExclusive) {
        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return (long) nextDouble(startInclusive, endExclusive);
    }

    public static long nextLong() {
        return nextLong(0L, Long.MAX_VALUE);
    }

    public static double nextDouble(double startInclusive, double endInclusive) {

        if (startInclusive == endInclusive) {
            return startInclusive;
        }

        return startInclusive + (endInclusive - startInclusive)
                * RANDOM.nextDouble();
    }

    public static double nextDouble() {
        return nextDouble(0.0D, Double.MAX_VALUE);
    }

    public static float nextFloat(float startInclusive, float endInclusive) {
        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + (endInclusive - startInclusive) * RANDOM.nextFloat();
    }

    public static float nextFloat() {
        return nextFloat(0.0F, Float.MAX_VALUE);
    }

    public static void isTrue(boolean expression, String message, Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

}
