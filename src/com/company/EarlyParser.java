package com.company;

import javafx.util.Pair;

import java.util.*;

/**
 * Created by lucas on 01/06/17.
 */
public class EarlyParser {

    private Grammar grammar;
    private ArrayList<Grammar> states;
    private String sentence;
    private String funkGenerated;

    public EarlyParser(){
        states = new ArrayList<Grammar>();
    }

    public Grammar getGrammar() {
        return grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    public void buildStateZero(){

        Grammar stateZero = new Grammar();

        // For each rule of the inital variable, clones it to the state zero
        String initialVariable = grammar.getInitialVariable();
        for(Production p : grammar.getProductions( initialVariable )){
            Production prod = new Production(p);
            prod.setDotPos(0);
            prod.setProductionSet(0);
            stateZero.addRule(initialVariable, prod);
        }




        states.add(stateZero);

        
    }

    public void printStates(){
        for(Grammar g : states) {
            int i = 0;
            System.out.println("State " + i++  + ":");
            g.printGrammar();
        }
    }
}