package com.company;

import java.io.FileNotFoundException;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Grammar funk = new Grammar("txtTest.txt");
        System.out.println("Grammar read:");
        funk.printGrammar();

        EarleyParser parser = new EarleyParser();
        parser.setGrammar(funk);

        boolean isRecognized = parser.parse("the dog eats the meat");
        if(isRecognized) {
            parser.printStates();
            System.out.println("The sentence given was successfully parsed!");
        }
        else
        	System.out.println("The sentence given is not part of the language!");

    }

}
