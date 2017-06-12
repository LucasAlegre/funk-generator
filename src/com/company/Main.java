package com.company;

import java.lang.annotation.ElementType;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class Main {

    public static void main(String[] args) {

        Grammar funk = new Grammar();
        funk.setInitialVariable("N");
        funk.addRule("N", new ArrayList<String>(Arrays.asList("V", "VP")));
        funk.addRule("N", new ArrayList<String>(Arrays.asList("tchu tcha")));
        funk.addRule("V", new ArrayList<String>(Arrays.asList("VP")));
        funk.addRule("V", new ArrayList<String>(Arrays.asList("vaiii")));

        funk.printGrammar();
        EarlyParser e = new EarlyParser();
        e.setGrammar(funk);
        e.buildStateZero();
        e.printStates();

    }

}
