package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Grammar funk = new Grammar();
        funk.addRule("N", new ArrayList<String>(Arrays.asList("N", "VP")));
        funk.addRule("N", new ArrayList<String>(Arrays.asList("tchu tcha")));
        funk.addRule("V", new ArrayList<String>(Arrays.asList("VP")));
        funk.addRule("V", new ArrayList<String>(Arrays.asList("vaiii")));

        funk.printGrammar();

    }


}
