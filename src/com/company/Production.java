package com.company;

import java.util.*;

/**
 *  Class representing all right sides of a grammar's rule.
 */
public class Production {

    private ArrayList<String> varOrTerminals;
    private int dotPos;
    private int productionSet;
    private float probability;

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }

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
        this.probability = -1;
    }

    public Production(Production p){
        varOrTerminals = p.getVarOrTerminals();
        dotPos = p.getDotPos();
        productionSet = p.getProductionSet();
        probability = p.getProbability();

    }

    public boolean equals(Production obj) {
        if(dotPos == obj.dotPos){
            if(this.varOrTerminals.size() == obj.getVarOrTerminals().size()){
                for(int i = 0; i < varOrTerminals.size(); i++){
                    if(!varOrTerminals.get(i).equals(obj.getVarOrTerminals().get(i)))
                        return false;
                }
                return true;
            }
            else
                return false;
        }
        else
            return false;
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