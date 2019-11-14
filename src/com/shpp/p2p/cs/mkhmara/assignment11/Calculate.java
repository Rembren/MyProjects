package com.shpp.p2p.cs.mkhmara.assignment11;

import com.shpp.p2p.cs.mkhmara.assignment16.MyArrayList;
import com.shpp.p2p.cs.mkhmara.assignment16.MyStack;
import com.shpp.p2p.cs.mkhmara.assignment17.MyHashMap;

/**
 * Calculates the given RPN expression
 */
public class Calculate {
    /**
     * Calculates the expression and returns the result
     * @param variables hashMap with values of variables
     * @param RPNExpression RPN expression
     * @return result of calculations
     */
    static Double calculate(MyHashMap variables, MyArrayList<String> RPNExpression) {
        return calculate(RPNExpression, variables);
    }

    /**
     * Adds elements to stack, preforms actions to the ones on top on reaching the operator
     * @param RPNExpression rpn expression
     * @param variables hashmap with variables
     * @return result of calculations
     */
    private static Double calculate(MyArrayList<String> RPNExpression, MyHashMap<String, Double> variables) {
        MyStack<Double> stack = new MyStack<>();
        for (String element : RPNExpression) {
            switch (element) {
                case "sqrt":
                    sqrt(stack.pop(), stack);
                    break;
                case "tan":
                    stack.add(Math.tan(Math.toRadians(stack.pop())));
                    break;
                case "atan":
                    stack.add(Math.atan(Math.toRadians(stack.pop())));
                    break;
                case "sin":
                    stack.add(Math.sin(Math.toRadians(stack.pop())));
                    break;
                case "cos":
                    stack.add(Math.cos(Math.toRadians(stack.pop())));
                    break;
                case "log2":
                    stack.add(Math.log(stack.pop()) / Math.log(2));
                    break;
                case "log10":
                    stack.add(Math.log10(stack.pop()));
                    break;
                case "+":
                    stack.add(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.add(-stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.add(stack.pop() * stack.pop());
                    break;
                case "/":
                    divide(stack.pop(), stack.pop(), stack);
                    break;
                case "^":
                    pow(stack.pop(), stack.pop(), stack);
                    break;
                case "NEGATIVE":
                    stack.add(-stack.pop());
                    break;
                default:
                    interpretateVars(element, stack, variables);
                    break;
            }
        }
        return stack.pop();
    }

    /**
     * Calculates sqrt for element, added as method to coves some edgy cases
     * @param number number to get square root for
     * @param stack stack with values
     */
    private static void sqrt(Double number, MyStack<Double> stack) {
        if (number < 0) {
            System.err.println("Square root from negative");
            System.exit(-1);
        }
        stack.add(Math.sqrt(number));
    }

    /**
     * Divides divided divider, added as method to coves some edgy cases
     * @param divider number that is divided
     * @param divided number by  which we divide
     * @param stack stack with results
     */
    private static void divide(Double divider, Double divided, MyStack<Double> stack) {
        if (divider == 0) {
            System.err.println("Division by zero.");
            System.exit(-1);
        }
        stack.add(divided / divider);
    }
    /**
     * raises number to power added as method to coves some edgy cases
     * @param number number that is raised to power
     * @param power power to raise to
     * @param stack stack with results
     */
    private static void pow(Double power, Double number, MyStack<Double> stack) {
        if (number == 0 && power < 0) {
            System.err.println("zero to negative power");
            System.exit(-1);
        }
        stack.add(Math.pow(number, power));
    }

    /**
     * If we stumble upon a number or var we call this method. it adds number or variable's value to stack.
     * @param element current element
     * @param stack stack with results
     * @param variables hashMap with variables
     */
    private static void interpretateVars(String element, MyStack<Double> stack, MyHashMap<String, Double> variables) {
        try {
            stack.add(Double.parseDouble(element));
        } catch (NumberFormatException e) {
            if (variables.get(element) != null) {
                stack.add(variables.get(element));
            } else {
                System.err.println("no value for variable");
                System.exit(-1);
            }
        }
    }
}
