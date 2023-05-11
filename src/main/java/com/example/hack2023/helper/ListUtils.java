package com.example.hack2023.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {

    public static <T> List<T> mergeAndRemoveDuplicates(List<T> list1, List<T> list2) {
        List<T> mergedList = new ArrayList<>();

        mergedList.addAll(list1);
        mergedList.addAll(list2);

        return mergedList.stream().distinct().collect(Collectors.toList());
    }

}
