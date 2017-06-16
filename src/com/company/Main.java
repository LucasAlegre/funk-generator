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
        funk.printGrammar();

        EarlyParser e = new EarlyParser();
        e.setGrammar(funk);

        boolean isrecognized = e.parse("number + number * number");
        if(isrecognized){
        	e.printStates();
        }
        else
        	System.out.println("Sua palavra nao faz parte da linguagem :/");

    }

}
