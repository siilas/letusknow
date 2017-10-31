package br.com.silas.letusknow.utils;

import java.util.Collections;
import java.util.List;

import br.com.silas.letusknow.model.Questao;

public class ListUtils {

    private ListUtils() {
    }

    public static boolean isNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static <T> List<T> getFirst(List<T>[] array) {
        if (array != null && array.length > 0) {
            return array[0];
        } else {
            return Collections.emptyList();
        }
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

}
