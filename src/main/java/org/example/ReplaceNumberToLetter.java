package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReplaceNumberToLetter {
    private static final String[] alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "V", "X", "Y"};
    List<Double> replaced = new ArrayList<>();

    public String addNumber(double number){
        int index = replaced.indexOf(number);
        if (index == -1){
            replaced.add(number);
            return alphabet[replaced.size() - 1];
        }else {
            return alphabet[index];
        }
    }

    public double getReplaced(String letter){
        int index = findIndex(alphabet, letter);

        return replaced.get(index);
    }

    private int findIndex(String[] arr, String t)
    {
        int index = Arrays.binarySearch(arr, t);
        return (index < 0) ? -1 : index;
    }

    public List<Double> getReplacedNumbers(){
        return replaced;
    }
}
