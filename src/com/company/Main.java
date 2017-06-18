package com.company;

import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Grammar funk = new Grammar("txtTest.txt");
        System.out.println("Grammar read:");
        funk.printGrammar();

        EarleyParser e = new EarleyParser();
        e.setGrammar(funk);

        boolean isRecognized = e.parse("the dog eats the meat");
        if(isRecognized) {
            e.printStates();
            System.out.println("The sentence given was successfully parsed!");
        }
        else
        	System.out.println("The sentence given is not part of the language!");

    }

}
