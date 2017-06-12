package com.company;

import java.util.*;

/**
 * Created by lucas on 01/06/17.
 */
public class Grammar {
    private HashMap<String, ArrayList<Production>> rules;
    private String initialVariable;


    public Grammar(){

        this.rules = new HashMap<String, ArrayList<Production>>();
    }

    public Set<String> getVariables(){
        return rules.keySet();
    }

    public String getInitialVariable() {
        return initialVariable;
    }

    public void setInitialVariable(String initialVariable) {
        this.initialVariable = initialVariable;
    }

    public ArrayList<Production> getProductions(String var){
        return rules.get(var);
    }

    public void addRule(String var, ArrayList<String> production){
        if(rules.containsKey(var))
            rules.get(var).add(new Production(production));

        else{
            ArrayList<Production> newProduction = new ArrayList<Production>();
            newProduction.add(new Production(production));
            rules.put(var, newProduction);
        }
    }

    public void addRule(String var, Production production){
        if(rules.containsKey(var))
            rules.get(var).add(production);

        else{
            ArrayList<Production> newProduction = new ArrayList<Production>();
            newProduction.add(production);
            rules.put(var, newProduction);
        }
    }

    public void printGrammar(){
        for(String var : rules.keySet()) {
            for (Production p : rules.get(var)){
                System.out.print(var + " -> ");
                ArrayList<String> rightSide = p.getVarOrTerminals();
                for(int i = 0; i < rightSide.size(); i++){
                    if(i == p.getDotPos())
                        System.out.print(" * ");
                    System.out.print("[ " + rightSide.get(i) + " ]");
                }
                System.out.println();
            }
        }
    }

}
