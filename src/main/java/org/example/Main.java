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

            boolean brackets = checkBrackets(equation);
            boolean expression = checkExpression(equation);
            System.out.println("Brackets:" + brackets);
            System.out.println("Expression:" + expression);
            if (brackets && expression){
                ReplaceNumberToLetter replaced = new ReplaceNumberToLetter();
                String replacesWithLetters = equation;

                Pattern regex = Pattern.compile("\\d+(\\.\\d+)?");
                Matcher match = regex.matcher(replacesWithLetters);
                while (match.find()) {
                    String number = match.group(0);
                    replacesWithLetters = replacesWithLetters.replaceAll(number, replaced.addNumber(Integer.parseInt(number)));
                    match = regex.matcher(replacesWithLetters);
                }

                String postfix = infixToPostfix(replacesWithLetters);
                System.out.println("Postfix: " + postfix);
                System.out.println(evaluatePostfixExpression(postfix, replaced));
            }
        }

    }

    public static boolean checkBrackets(String equation){
        String[] mappedEquation = equation.split("");
        Long openBrackets = Arrays.stream(mappedEquation).filter("("::equals).count();
        Long closingBrackets = Arrays.stream(mappedEquation).filter(")"::equals).count();

        return openBrackets.equals(closingBrackets);
    }

    public static boolean checkExpression(String expression) {
        return !Pattern.compile("[-+*/]{2}").matcher(expression).find();
    }

    static int Prec(char ch)
    {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    static String infixToPostfix(String expression)
    {
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
                }
            }
        }
        return st.peek();
    }
}