package org.example;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        while (true){
            Scanner input = new Scanner(System.in);
            String equation = input.nextLine();

            MatchLogic mathLogic = new MatchLogic();
            ExpressionProcessor expressionProcessor = new ExpressionProcessor();

            boolean brackets = expressionProcessor.checkBrackets(equation);
            boolean expression = expressionProcessor.checkExpression(equation);
            System.out.println("Brackets:" + brackets);
            System.out.println("Expression:" + expression);

            if (brackets && expression){

                ReplaceNumberToLetter replaced = new ReplaceNumberToLetter();
                String replaceWithLetters = equation;

                replaceWithLetters = expressionProcessor.getExpression(replaceWithLetters);
                if (expressionProcessor.getIsEquation()) replaceWithLetters = expressionProcessor.replaceRoot(input, replaceWithLetters);
                replaceWithLetters = expressionProcessor.replaceNumberToLetters(replaceWithLetters, replaced, Pattern.compile("\\d+(\\.\\d+)?"));
                String postfix = mathLogic.infixToPostfix(replaceWithLetters);

                System.out.println("Postfix: " + postfix);
                System.out.println("Count of nums: " + expressionProcessor.getNumCount());

                if (expressionProcessor.getIsEquation()){
                    System.out.println("equation is solved correctly: " + (mathLogic.evaluatePostfixExpression(postfix, replaced) == expressionProcessor.getValueAfterEquals()));
                }else {
                    System.out.println("Answer: " + mathLogic.evaluatePostfixExpression(postfix, replaced));
                }
            }
        }
    }
}