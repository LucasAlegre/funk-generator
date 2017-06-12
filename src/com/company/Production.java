package com.company;

import java.util.*;

/**
 *  Class representing all right sides of a grammar's rule.
 */
public class Production {

    private ArrayList<String> varOrTerminals;
    private int dotPos;
    private int productionSet;

    public int getDotPos() {
        return dotPos;
    }

    public void setDotPos(int dotPos) {
        this.dotPos = dotPos;
    }

    public int getProductionSet() {
        return productionSet;
    }

    public void setProductionSet(int productionSet) {
        this.productionSet = productionSet;
    }


    public Production(ArrayList<String> varOrTerm){
        this.varOrTerminals = varOrTerm;
        this.dotPos = -1; // -1 indicates no dot
        this.productionSet = -1; // -1 indicates original grammar production
    }

    public Production(Production p){
        varOrTerminals = p.getVarOrTerminals();
        dotPos = p.getDotPos();
        productionSet = p.getProductionSet();

    }

    public ArrayList<String> getVarOrTerminals() {
        return varOrTerminals;
    }

    public void setVarOrTerminals(ArrayList<String> varOrTerm) {
        this.varOrTerminals = varOrTerm;
    }

    public void incDot(){
        dotPos++;
    }

    public boolean isDotEnd(){
        return dotPos == varOrTerminals.size();
    }

    public String getElementAtDot(){
        return varOrTerminals.get(dotPos);
    }

    public String getFirstElement(){
        return varOrTerminals.get(0);
    }

}