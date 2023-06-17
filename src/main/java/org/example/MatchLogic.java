package org.example;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class MatchLogic {

    static int Prec(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    public String infixToPostfix(String expression) {
        String result = "";
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

    public double evaluatePostfixExpression(String expression, ReplaceNumberToLetter replaced) {
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
