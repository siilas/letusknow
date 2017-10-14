package br.com.silas.letusknow.utils;

public class ArrayUtils {

    private ArrayUtils() {
    }

    public static boolean isNotEmpty(Object[] array) {
        return array != null && array.length > 0;
    }

}
