package org.example;

import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionProcessor {
    private double valueAfterEquals = 0;
    private boolean isEquation = false;
    private int numCount = 0;
    public String replaceNumberToLetters(String expression, ReplaceNumberToLetter replaced, Pattern regex){
        String replaceWithLetters = expression;
        int count = 0;

        Matcher match = regex.matcher(replaceWithLetters);
        while (match.find()) {
            String number = match.group(0);
            replaceWithLetters = replaceWithLetters.replaceFirst(number, replaced.addNumber(Double.parseDouble(number)));
            match = regex.matcher(replaceWithLetters);
            count++;
        }
        numCount = count;
        return replaceWithLetters;
    }

    public String replaceRoot(Scanner input, String replacedExpression){
        double number = input.nextDouble();
        return replacedExpression.replaceAll("x", String.valueOf(number));
    }

    public boolean checkBrackets(String expression){
        String[] mappedEquation = expression.split("=");

        for (String s : mappedEquation) {
            String[] current = s.split("");
            Long openBrackets = Arrays.stream(current).filter("("::equals).count();
            Long closingBrackets = Arrays.stream(current).filter(")"::equals).count();
            if (openBrackets - closingBrackets != 0) return false;
        }

        return true;
    }

    public String getExpression(String replaceWithLetters) {
        if (replaceWithLetters.indexOf("x") > 0) {
            Matcher match = Pattern.compile("=\\d+(\\.\\d+)?").matcher(replaceWithLetters);
            if (match.find()) {
                replaceWithLetters = replaceWithLetters.replaceFirst(match.group(0), "");
                valueAfterEquals = Double.parseDouble(match.group(0).replaceFirst("=", ""));
                isEquation = true;
            }
        }
        return replaceWithLetters;
    }

    public double getValueAfterEquals() {
        return valueAfterEquals;
    }

    public boolean getIsEquation(){
        return isEquation;
    }

    public boolean checkExpression(String expression) {
        return !Pattern.compile("[+\\-*\\/]{2}").matcher(expression).find();//(?<!\*-|/-)
    }

    public int getNumCount() {
        return numCount;
    }
}
