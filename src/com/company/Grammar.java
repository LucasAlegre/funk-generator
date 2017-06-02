package com.company;

import java.util.*;

/**
 * Created by lucas on 01/06/17.
 */
public class Grammar {
    private HashMap<String, ArrayList<Production>> rules;

    public Grammar(){
        this.rules = new HashMap<String, ArrayList<Production>>();
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

    public void printGrammar(){
        for(String s : rules.keySet()) {
            System.out.print(s + " -> ");
            for (Production p : rules.get(s))
                System.out.print(p.getVarOrTerminals() + " / ");
            System.out.println();
        }
    }
}
