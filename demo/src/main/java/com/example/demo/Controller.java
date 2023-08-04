package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("")
    public String init (HttpServletRequest request) {
        int arraySize = 1000;
        List<String> randomList = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            int elementLength = random.nextInt(5) + 1;
            randomList.add(generateRandomString(elementLength));
        }
        request.setAttribute("randomList", randomList);

        long startTimeInsertionSort = System.currentTimeMillis();
        List<String> sortedList = insertionSort(randomList);
        long endTimeInsertionSort = System.currentTimeMillis();
        long executionTimeInsertionSort = endTimeInsertionSort - startTimeInsertionSort;
        request.setAttribute("sortedList", sortedList);
        request.setAttribute("executionTimeInsertionSort", executionTimeInsertionSort);

        long startTimeSelectionSort = System.currentTimeMillis();
        selectionSort(randomList);
        long endTimeSelectionSort = System.currentTimeMillis();
        long executionTimeSelectionSort = endTimeSelectionSort - startTimeSelectionSort;
        request.setAttribute("executionTimeSelectionSort", executionTimeSelectionSort);

        long startTimeBubbleSort = System.currentTimeMillis();
        bubbleSort(randomList);
        long endTimeBubbleSort = System.currentTimeMillis();
        long executionTimeBubbleSort = endTimeBubbleSort - startTimeBubbleSort;
        request.setAttribute("executionTimeBubbleSort", executionTimeBubbleSort);

        long startTimeMergeSort = System.currentTimeMillis();
        mergeSort(randomList);
        long endTimeMergeSort = System.currentTimeMillis();
        long executionTimeMergeSort = endTimeMergeSort - startTimeMergeSort;
        request.setAttribute("executionTimeMergeSort", executionTimeMergeSort);

        long startTimeQuickSort = System.currentTimeMillis();
        quickSort(randomList);
        long endTimeQuickSort = System.currentTimeMillis();
        long executionTimeQuickSort = endTimeQuickSort - startTimeQuickSort;
        request.setAttribute("executionTimeQuickSort", executionTimeQuickSort);

        return "index";
    }


    private static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public List<String> insertionSort(List<String> arrayList) {
        List<String> sortedList = new ArrayList<>(arrayList);
        int n = sortedList.size();
        for (int i = 1; i < n; ++i) {
            String key = sortedList.get(i);
            int j = i - 1;

            while (j >= 0 && sortedList.get(j).compareTo(key) > 0) {
                sortedList.set(j + 1, sortedList.get(j));
                j = j - 1;
            }
            sortedList.set(j + 1, key);
        }
        return sortedList;
    }

    public static List<String> selectionSort(List<String> arrayList) {
        List<String> sortedList = new ArrayList<>(arrayList);
        int n = sortedList.size();

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (sortedList.get(j).compareTo(sortedList.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                String temp = sortedList.get(i);
                sortedList.set(i, sortedList.get(minIndex));
                sortedList.set(minIndex, temp);
            }
        }
        return sortedList;
    }

    public static List<String> bubbleSort(List<String> arrayList) {
        List<String> sortedList = new ArrayList<>(arrayList);
        int n = sortedList.size();
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (sortedList.get(j).compareTo(sortedList.get(j + 1)) > 0) {
                    String temp = sortedList.get(j);
                    sortedList.set(j, sortedList.get(j + 1));
                    sortedList.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) {
                break;
            }
        }
        return sortedList;
    }

    public static List<String> mergeSort(List<String> arrayList) {
        int n = arrayList.size();
        if (n <= 1) {
            return arrayList;
        }

        int middle = n / 2;
        List<String> left = arrayList.subList(0, middle);
        List<String> right = arrayList.subList(middle, n);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    public static List<String> merge(List<String> left, List<String> right) {
        List<String> mergedList = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).compareTo(right.get(rightIndex)) <= 0) {
                mergedList.add(left.get(leftIndex));
                leftIndex++;
            } else {
                mergedList.add(right.get(rightIndex));
                rightIndex++;
            }
        }

        while (leftIndex < left.size()) {
            mergedList.add(left.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex < right.size()) {
            mergedList.add(right.get(rightIndex));
            rightIndex++;
        }

        return mergedList;
    }

    public static List<String> quickSort(List<String> arrayList) {
        if (arrayList.size() <= 1) {
            return arrayList;
        }

        int pivotIndex = new Random().nextInt(arrayList.size());
        String pivot = arrayList.get(pivotIndex);

        List<String> less = new ArrayList<>();
        List<String> equal = new ArrayList<>();
        List<String> greater = new ArrayList<>();

        for (String element : arrayList) {
            int cmp = element.compareTo(pivot);
            if (cmp < 0) {
                less.add(element);
            } else if (cmp > 0) {
                greater.add(element);
            } else {
                equal.add(element);
            }
        }

        List<String> sortedList = new ArrayList<>();
        sortedList.addAll(quickSort(less));
        sortedList.addAll(equal);
        sortedList.addAll(quickSort(greater));

        return sortedList;
    }
}
