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

        boolean increased;
        ArrayList<String> variables = new ArrayList<String>(stateZero.getVariables());
        int count = 0;
        do{
            increased = false;

            for(int i = count; i < variables.size(); i++){
                for(Production p : stateZero.getProductions(variables.get(i))){
                    String b = p.getFirstElement();

                    if(EarlyParser.isVariable(b)){
                        variables.add(b);
                        for(Production p2 : grammar.getProductions(b)){
                            Production prod = new Production(p2);
                            prod.setDotPos(0);
                            prod.setProductionSet(0);
                            stateZero.addRule(b, prod);
                            increased = true;
                        }
                    }
                }
                count = i;
            }

        }while(increased); // While there is rules to add

        states.add(stateZero);
    }

    public void parse(String s){

        String[] sentence = s.split(" ");

        buildStateZero();

        for(int i = 1; i <= sentence.length; i++){
            Grammar state = new Grammar();

            //TODO: etapa (2)

            boolean increased;
            do{
                increased = false;

                //TODO: etapa(3)


                //TODO: etapa(4)



            }while (increased);

            states.add(state);
        }

        //TODO: check se parseou ou não

    }


    public static boolean isVariable(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return true;
        else
            return false;
    }

    public static boolean isTerminal(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return true;
        else
            return false;
    }

    public void printStates(){
        for(Grammar g : states) {
            int i = 0;
            System.out.println("State " + i++  + ":");
            g.printGrammar();
        }
    }
}