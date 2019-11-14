package com.shpp.p2p.cs.mkhmara.assignment11;

import com.shpp.p2p.cs.mkhmara.assignment16.MyArrayList;
import com.shpp.p2p.cs.mkhmara.assignment17.MyHashMap;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Calculates math expressions, works with basic operators and with  math functions.
 *
 * Add expression to be calculated as argument when launching the program. If you want to add any variables,
 * simply write their values after the expression for example a=2.
 */
public class Calculator {
    /**
     * Transforms args into RPN, adds variables and their  values to hashMap, cycles if needed
     * @param args arguments given by user
     */
    public static void main(String[] args) {
        Parser parser = new Parser(args);
        MyArrayList<String> expression = parser.getRPNExpression();
        MyHashMap<String, Double> variables = parser.getVariables();
        System.out.println(Calculate.calculate(variables, expression));
        if (!variables.isEmpty()) {
            repeat(variables, expression);
        }
    }

    /**
     * Reasks the values for all variables and counts the expression with new values.
     * @param variables hashMap with variables and values
     * @param expression expression to  count
     */
    private static void repeat(MyHashMap<String, Double> variables, MyArrayList<String> expression) {
        String answer;
        do {
            Scanner in = new Scanner(System.in);
            System.out.println("Try again with other variables? Y - yes");
            answer = in.nextLine().toLowerCase();
            if (answer.equals("y")) {
                for (String key : variables.keySet()) {
                    System.out.println("Enter new value for: " + key);
                    try {
                        variables.replace(key, in.nextDouble());
                    } catch (InputMismatchException e) {
                        System.err.println("please enter a number next time");
                        System.exit(1);
                    }
                }
                System.out.println(Calculate.calculate(variables, expression));
            } else {
                System.exit(0);
            }
        } while (answer.equals("y"));
    }

}
