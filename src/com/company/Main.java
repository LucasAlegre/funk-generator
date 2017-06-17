package com.company;

import java.io.FileNotFoundException;
import java.lang.annotation.ElementType;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Grammar funk = new Grammar("txtTest.txt");
        System.out.println("Grammar read:");
        funk.printGrammar();

        EarlyParser e = new EarlyParser();
        e.setGrammar(funk);

        boolean isRecognized = e.parse("the dog eats the meat");
        if(isRecognized)
        	e.printStates();
        else
        	System.out.println("The sentence given is not part of the language!");

    }

}
