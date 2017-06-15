package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by lucas on 01/06/17.
 */
public class Grammar {
    public HashMap<String, ArrayList<Production>> rules;
    private String initialVariable;
    private HashSet<String> variables;
    private HashSet<String> terminals;

    public Grammar(){

        this.rules = new HashMap<String, ArrayList<Production>>();
        this.variables = new HashSet<String>();
        this.terminals = new HashSet<String>();
    }

    public Grammar(String file){
        this();

        try{
            readFile(file);
        }
        catch (Exception e){
            System.out.println( e.getMessage() );
        }
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

    public void addRule(String var, Production production, float prob){
        production.setProbability(prob);
        if(rules.containsKey(var))

            rules.get(var).add(production);

        else{
            ArrayList<Production> newProduction = new ArrayList<Production>();
            newProduction.add(production);
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

    public void readFile(String text) throws FileNotFoundException{
        File tst = new File(text);
        Scanner sc = new Scanner(tst);
        String opFlag = "Terminais";
        String buff;
        buff = sc.nextLine();

        while(sc.hasNext()){
            buff = sc.nextLine();
            if(buff.compareTo("Variaveis") == 0 || buff.compareTo("Inicial") == 0|| buff.compareTo("Regras") == 0){
                opFlag = buff;
                buff = sc.nextLine();
            }
            switch(opFlag){
                case "Terminais":
                    String terminal;
                    terminal = buff.substring(buff.indexOf('[')+2, buff.indexOf(']')-1);
                    this.terminals.add(terminal);
                    break;
                case "Variaveis":
                    String var;
                    var = buff.substring(buff.indexOf('[')+2, buff.indexOf(']')-1);
                    this.variables.add(var);
                    break;
                case "Inicial":
                    String inicio;
                    inicio = buff.substring(buff.indexOf('[')+2, buff.indexOf(']')-1);
                    this.initialVariable = inicio;
                    break;
                case "Regras":
                    String variavel;
                    variavel = buff.substring(buff.indexOf('[')+2, buff.indexOf(']')-1);
                    ArrayList<String> bufferOfRules = new ArrayList<>();
                    float probability=0;
                    int indexS = 0, indexE = 0;
                    char c;
                    for(int j = buff.indexOf('>'); j<buff.length(); j++){
                        c = buff.charAt(j);
                        if(c == '[') indexS = j;
                        if(c == ']'){
                            indexE = j;
                            bufferOfRules.add(buff.substring(indexS+2, indexE-1));
                        }
                        if(c==';'){
                            indexS = j;
                            probability = Float.valueOf(buff.substring(indexS+1));
                        }
                    }
                    this.addRule(variavel, new Production(bufferOfRules), probability);
                    break;
            }
        }
        sc.close();

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
                //System.out.println("  Probability = " + p.probability);
                System.out.println();
            }
        }
    }

}
