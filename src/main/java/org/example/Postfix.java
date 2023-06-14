package org.example;

import java.util.List;

public class Postfix {
    private String expression;
    private List<Integer> replaced;

    public Postfix(String expression, List<Integer> replaced){
        this.expression = expression;
        this.replaced = replaced;
    }

    public String getExpression(){
        return expression;
    }

    public List<Integer> getReplaced() {
        return replaced;
    }
}
