package com.company;

import java.util.*;

/**
 *  Class representing all right sides of a grammar's rule.
 */
public class Production {

    private ArrayList<String> varOrTerminals;
    private int dotPos;
    private int productionSet;

    public Production(ArrayList<String> varOrTerm){
        this.varOrTerminals = varOrTerm;
        this.dotPos = -1; // -1 indicates no dot
        this.productionSet = -1; // -1 indicates original grammar production
    }

    public ArrayList<String> getVarOrTerminals() {
        return varOrTerminals;
    }

    public void setVarOrTerminals(ArrayList<String> varOrTerm) {
        this.varOrTerminals = varOrTerm;
    }


}