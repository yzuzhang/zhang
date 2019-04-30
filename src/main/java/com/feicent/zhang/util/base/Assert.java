package com.feicent.zhang.util.base;

import com.feicent.zhang.base.exception.BaseException;

import java.util.Collection;
import java.util.Map;

/**
 * @date: 2019/4/30 16:46
 * @desc: 断言工具类
 */
public class Assert {

    /**
     *
     * @param expression
     * @param message
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) { throw new BaseException(message); }
    }

    /**
     *
     * @param expression
     */
    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] - this expression must be true");
    }

    /**
     *
     * @param object
     * @param message
     */
    public static void isNull(Object object, String message) {
        if (object != null) { throw new BaseException(message); }
    }

    /**
     *
     * @param object
     */
    public static void isNull(Object object) {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     *
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) { throw new BaseException(message); }
    }

    /**
     *
     * @param object
     */
    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] - this argument is required; it must not be null");
    }

    /**
     *
     * @param text
     * @param message
     */
    public static void hasLength(String text, String message) {
        if (!hasLength((CharSequence) text)) { throw new BaseException(message); }
    }

    /**
     *
     * @param text
     */
    public static void hasLength(String text) {
        hasLength(text, "[Assertion failed] - this String argument must have length; it must not be null or empty");
    }

    /**
     *
     * @param text
     * @param message
     */
    public static void hasText(String text, String message) {
        if (!hasText((CharSequence) text)) { throw new BaseException(message); }
    }

    /**
     *
     * @param text
     */
    public static void hasText(String text) {
        hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
    }

    /**
     *
     * @param textToSearch
     * @param substring
     * @param message
     */
    public static void doesNotContain(String textToSearch, String substring, String message) {
        if (hasLength((CharSequence) textToSearch) && hasLength((CharSequence) substring) && textToSearch.contains(substring)) { throw new BaseException(message); }
    }

    /**
     *
     * @param textToSearch
     * @param substring
     */
    public static void doesNotContain(String textToSearch, String substring) {
        doesNotContain(textToSearch, substring, "[Assertion failed] - this String argument must not contain the substring [" + substring + "]");
    }

    /**
     *
     * @param array
     * @param message
     */
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) { throw new BaseException(message); }
    }

    /**
     *
     * @param array
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "[Assertion failed] - this array must not be empty: it must contain at least 1 element");
    }

    /**
     *
     * @param array
     * @param message
     */
    public static void noNullElements(Object[] array, String message) {
        if (array != null) {
            for (Object element : array) {
                if (element == null) { throw new BaseException(message); }
            }
        }
    }

    /**
     *
     * @param array
     */
    public static void noNullElements(Object[] array) {
        noNullElements(array, "[Assertion failed] - this array must not contain any null elements");
    }

    /**
     *
     * @param collection
     * @param message
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) { throw new BaseException(message); }
    }

    /**
     *
     * @param collection
     */
    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[Assertion failed] - this collection must not be empty: it must contain at least 1 element");
    }

    /**
     *
     * @param map
     * @param message
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) { throw new BaseException(message); }
    }

    /**
     *
     * @param map
     */
    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] - this map must not be empty; it must contain at least one entry");
    }

    /**
     *
     * @param clazz
     * @param obj
     */
    public static void isInstanceOf(Class<?> clazz, Object obj) {
        isInstanceOf(clazz, obj, "");
    }

    /**
     *
     * @param type
     * @param obj
     * @param message
     */
    public static void isInstanceOf(Class<?> type, Object obj, String message) {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) { throw new BaseException((hasLength((CharSequence) message) ? message + " " : "") + "Object of class [" + (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type); }
    }

    /**
     *
     * @param superType
     * @param subType
     */
    public static void isAssignable(Class<?> superType, Class<?> subType) {
        isAssignable(superType, subType, "");
    }

    /**
     *
     * @param superType
     * @param subType
     * @param message
     */
    public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) { throw new BaseException(message + subType + " is not assignable to " + superType); }
    }

    /**
     *
     * @param expression
     * @param message
     */
    public static void state(boolean expression, String message) {
        if (!expression) { throw new BaseException(message); }
    }

    /**
     *
     * @param expression
     */
    public static void state(boolean expression) {
        state(expression, "[Assertion failed] - this state invariant must be true");
    }

    /**
     *
     * @param enumClz
     * @param value
     * @param message
     * @param <T> T
     */
    public static <T extends Enum<T>> void enumValidity(Class<T> enumClz, String value, String message) {
        try {
            notNull(Enum.valueOf(enumClz, value), message);
        } catch (Exception e) {
            throw new BaseException(message);
        }
    }

    /**
     * hasLength
     * @param str
     * @return boolean
     */
    private static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     *
     * @param str
     * @return boolean
     */
    private static boolean hasText(CharSequence str) {
        if (!hasLength(str)) { return false; }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) { return true; }
        }
        return false;
    }
}
