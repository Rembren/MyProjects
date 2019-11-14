package com.shpp.p2p.cs.mkhmara.assignment11;

import com.shpp.p2p.cs.mkhmara.assignment16.MyArrayList;
import com.shpp.p2p.cs.mkhmara.assignment16.MyStack;
import com.shpp.p2p.cs.mkhmara.assignment17.MyHashMap;

import java.util.StringTokenizer;

/**
 * This class is made to parse given arguments into reversed polish notation and get the values of variables
 * and put them into hashMap.
 */
public class Parser {

    //possible delimiters between signs
    private static String delimiters = "*/-+^()";
    //possible math functions
    private static String[] functions = {"sin", "cos", "tan", "atan",  "sqrt", "log10", "log2"};
    //expression written in polish notation
    private MyArrayList<String> RPNExpression = new MyArrayList<>();
    //variables and their values
    private MyHashMap<String, Double> variables = new MyHashMap<>();

    /**
     * Chechs if the expression is correct, transforms it to RPN and finds variables.
     * @param args arguments  given by user
     */
    Parser(String[] args) {
        String expression = checkExpression(args);
        transformToRPN(expression);
        findVars(args);
    }

    /**
     * If length of the arguments is more than zero, and there are no obvious mistakes returns the first
     * element of args[]
     * @param args arguments given by user
     * @return first element that is supposedly
     */
    private String checkExpression(String[] args) {
        String expression = "";
        if (args.length > 0) {
            expression = args[0];
        }
        if (!isCorrect(expression)) {
            System.exit(1);
        }
        return expression;
    }

    /**
     * Checks the expression for basic mistakes
     * @param expression the first element of args[]
     * @return true if the expression has passed the check
     */
    private boolean isCorrect(String expression) {
        if (expression.length() == 0) {
            System.err.println("There is no expression");
            return false;
        }
        if (expression.contains("=")) {
            System.err.println("\"=\" shouldn't be in this formula");
            return false;
        }
        if (expression.charAt(0) == '*' || expression.charAt(0) == '/' || expression.charAt(0) == ')' || expression.charAt(0) == '^') {
            System.err.println("Incorrect expression. Unexpected operator at start");
            return false;
        }
        return true;
    }

    /**
     * Transforms the string with expression into RPN
     * @param expression math expression
     */
    private void transformToRPN(String expression) {
        MyStack<String> stack = new MyStack<>();
        StringTokenizer tokenizer = new StringTokenizer(expression, delimiters, true);
        String currentElement, previousElement = "";

        while (tokenizer.hasMoreTokens()) {
            currentElement = tokenizer.nextToken();
            if (isFunction(currentElement)) { //if element os function - puts it into stack
                stack.add(currentElement);
            } else if (isDelimiter(currentElement)) {
                if (currentElement.equals("(") || currentElement.equals("^")) { //open brackets and powers are fine too
                    stack.add(currentElement);
                    //if finds closed brackets - looks for the first open ones, puts all elements between them into RPN
                } else if (currentElement.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        RPNExpression.add(stack.pop());
                        if (stack.isEmpty()) {
                            System.err.println("Troubles with brackets");
                            System.exit(-1);
                        }
                    }
                    stack.pop();
                    if (!stack.isEmpty() && isFunction(stack.peek())) {
                        RPNExpression.add(stack.pop());
                    }
                } else {
                    //if there is a minus in teh first position or after delimiter that is not ) adds it to stack
                    if (currentElement.equals("-") && (previousElement.equals("")
                            || (isDelimiter(previousElement) && !previousElement.equals(")")))) {
                        stack.add("NEGATIVE");
                    } else {
                        //adds all elements with priority higher that current element to RPN
                        while (!stack.isEmpty() && getPriority(currentElement) <= getPriority(stack.peek())) {
                            RPNExpression.add(stack.pop());
                        }
                        stack.add(currentElement);
                    }
                }
            } else {
                RPNExpression.add(currentElement);
            }
            previousElement = currentElement;
        }
        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) {
                RPNExpression.add(stack.pop());
            }
        }
    }

    /**
     * @param element Element to check
     * @return  true if the element ls an operator
     */
    private boolean isOperator(String element) {
        String operators = "*/-+^";
        return operators.contains(element);
    }

    /**
     * @param currentElement Element to check
     * @return the priority of the element
     */
    private int getPriority(String currentElement) {
        switch (currentElement) {
            case "(":
                return 0;
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return 3;
        }
    }

    /**
     * @param expression string to check
     * @return true if the current element is math function
     */
    private boolean isFunction(String expression) {
        for (String string : functions) {
            if (string.equals(expression)) {
                return true;
            }
        }
        return false;
    }
    /**
     * @param currentElement string to check
     * @return true if the current element is delimiter
     */
    private boolean isDelimiter(String currentElement) {
        if (currentElement.length() > 1) {
            return false;
        }
        return delimiters.contains(currentElement);
    }

    /**
     * Finds the values of variables given to us by user in args
     * @param args arguments written by user
     */
    private void findVars(String[] args) {
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                String currentElement = args[i];
                if (currentElement.matches("(^\\w+=\\d+$)")) {
                    String[] entry = args[i].split("=");
                    variables.put(entry[0], Double.valueOf(entry[1]));
                } else {
                    System.err.println("Wrong variable entries");
                    System.exit(-1);
                }
            }
        }
    }

    /**
     * @return Expression converted to RPN
     */
    MyArrayList getRPNExpression() {
        return RPNExpression;
    }

    /**
     * @return HashMap with variables
     */
    MyHashMap getVariables() {
        return variables;
    }
}
