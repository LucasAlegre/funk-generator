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

    /**
     * Default Constructor.
     * @param varOrTerm The list of strings of the right side of the rule
     */
    public Production(ArrayList<String> varOrTerm){
        this.varOrTerminals = varOrTerm;
        this.dotPos = -1; // -1 indicates no dot
        this.productionSet = -1; // -1 indicates original grammar production
        this.probability = -1;
    }

    /**
     * Copy constructor.
     * @param p the production to be copied
     */
    public Production(Production p){
        varOrTerminals = p.getVarOrTerminals();
        dotPos = p.getDotPos();
        productionSet = p.getProductionSet();
        probability = p.getProbability();

    }

    /**
     * Check if the production given is equal.
     * @param obj The production to be compared
     * @return True if they're equal, false otherwise
     */
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

    public void setVarOrTerminals(ArrayList<String> varOrTerm) {
        this.varOrTerminals = varOrTerm;
    }

    /**
     *  Moves the dot pointer in one position.
     */
    public void incDot(){
        dotPos++;
    }

    /**
     * Check if the dot is at the end of the right side of the rule.
     * @return True if the dot is at the end.
     */
    public boolean isDotEnd(){
        return dotPos == varOrTerminals.size();
    }

    /**
     * Return the string being pointed by the dot.
     * @return The string in the dot position
     */
    public String getElementAtDot(){
        return varOrTerminals.get(dotPos);
    }

    /**
     * Gets the first string on the right side of the rule.
     * @return The first string of the production
     */
    public String getFirstElement(){
        return varOrTerminals.get(0);
    }

    /**
     *   Getters And Setters
     */
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

    public ArrayList<String> getVarOrTerminals() {
        return varOrTerminals;
    }

}