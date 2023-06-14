package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    //a+b/c/d*e^(f-a)/c

    public static final char[] alphabet = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'V', 'X', 'Y'};

    public static void main(String[] args) {
        while (true){
            Scanner input = new Scanner(System.in);
            String equation = input.nextLine();
            String[] mappedEquation = equation.split("");
            boolean isEquation = false;

            boolean brackets = checkBrackets(equation);
            boolean expression = checkExpression(equation);
            System.out.println("Brackets:" + brackets);
            System.out.println("Expression:" + expression);
            if (brackets && expression){
                ReplaceNumberToLetter replaced = new ReplaceNumberToLetter();

                String replaceWithLetters = equation;

                Matcher match = Pattern.compile("=\\d+(\\.\\d+)?").matcher(equation);

                if (match.find()){
                    isEquation = true;
                    replaceWithLetters = replaceWithLetters.replaceFirst(match.group(0), "");
                    double x = input.nextDouble();
                    replaceWithLetters = replaceWithLetters.replaceAll("x", String.valueOf(x));
                }

                replaceWithLetters = replaceNumberToLetters(replaceWithLetters, replaced, Pattern.compile("\\d+(\\.\\d+)?"));

//                System.out.println("Expression: " + replaceWithLetters);
//                System.out.println("Numbers: " + replaced.getReplacedNumbers());

                String postfix = infixToPostfix(replaceWithLetters);
//                System.out.println("Postfix: " + postfix);
//                System.out.println("Numbers: " + replaced.getReplacedNumbers());
                if (isEquation){
                    System.out.println("equation is solved correctly: " + (evaluatePostfixExpression(postfix, replaced) == Double.parseDouble(match.group(0).replaceAll("=", ""))));
                }else {
                    System.out.println("Answer: " + evaluatePostfixExpression(postfix, replaced));
                }
            }
        }

    }

    public static String replaceNumberToLetters(String expression, ReplaceNumberToLetter replaced, Pattern regex){
        String replaceWithLetters = expression;

        Matcher match = regex.matcher(replaceWithLetters);
        while (match.find()) {
            String number = match.group(0);
            replaceWithLetters = replaceWithLetters.replaceFirst(number, replaced.addNumber(Double.valueOf(number)));
            match = regex.matcher(replaceWithLetters);
        }
        return replaceWithLetters;
    }

    public static boolean checkBrackets(String equation){
        String[] mappedEquation = equation.split("");
        Long openBrackets = Arrays.stream(mappedEquation).filter("("::equals).count();
        Long closingBrackets = Arrays.stream(mappedEquation).filter(")"::equals).count();

        return openBrackets.equals(closingBrackets);
    }

    public static boolean checkExpression(String expression) {
        return !Pattern.compile("[+\\-*\\/]{2}(?<!\\*-|/-)").matcher(expression).find();
    }

    static int Prec(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    static String infixToPostfix(String expression) {
        String result = new String("");
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); ++i) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                result += c;
            }

            else if (c == '(')
                stack.push(c);

            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result += stack.peek();
                    stack.pop();
                }
                stack.pop();
            }
            else
            {
                while (!stack.isEmpty()
                        && Prec(c) <= Prec(stack.peek())) {

                    result += stack.peek();
                    stack.pop();
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek() == '(')
                return "Invalid expression";
            result += stack.peek();
            stack.pop();
        }

        return result;
    }

    public static double evaluatePostfixExpression(String expression, ReplaceNumberToLetter replaced) {
        Stack<Double> st = new Stack<>();

        for (int i = 0; i < expression.length(); i++){
            char c = expression.charAt(i);

            if (Character.isLetter(c)){
                st.push(replaced.getReplaced(String.valueOf(c)));
            }
            else{
                double op1 = st.pop();
                double op2 = st.pop();

                switch (c) {
                    case '+' -> st.push(op2 + op1);
                    case '-' -> st.push(op2 - op1);
                    case '*' -> st.push(op2 * op1);
                    case '/' -> st.push(op2 / op1);
                    case '^' -> st.push(Math.pow(op2, op1));
                }
            }
        }
        return st.peek();
    }
}

//2+5/4/1*-2^(6-2)/4