package br.com.silas.letusknow.utils;

import java.util.List;

public class ListUtils {

    private ListUtils() {
    }

    public static boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

}
