package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *  Grammar Class
 */
public class Grammar {

    private HashMap<String, ArrayList<Production>> rules;
    private String initialVariable;
    private HashSet<String> variables;
    private HashSet<String> terminals;

    /**
     * Default constructor.
     */
    public Grammar(){

        this.rules = new HashMap<String, ArrayList<Production>>();
        this.variables = new HashSet<String>();
        this.terminals = new HashSet<String>();
    }

    /**
     *  Builds a grammar from the txt file.
     * @param file The file which contains the grammar.
     */
    public Grammar(String file){
        this();

        try{
            readFile(file);
        }
        catch (Exception e){
            System.out.println( e.getMessage() );
            e.printStackTrace();
        }
    }

    /**
     * Check if the grammar contains the given rule.
     * @param var Left side of the rule
     * @param production Right side of the rule
     * @return True if it contains, false otherwise.
     */
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

    /**
     * Check if the grammar contains the given rule.
     * @param var Left side of the rule
     * @param p Right side of the rule
     * @return True if it contains, false otherwise.
     */
    public boolean containsRule(String var, Production p){
        if(rules.containsKey(var)){
            for(Production p2 : rules.get(var)){
                if(p2.equals(p))
                    return true;
            }
        }
        return false;
    }

    /**
     * Add a rule to the grammar.
     * @param var Var on the lef side.
     * @param production Rigth side of the rule.
     */
    public void addRule(String var, Production production){

        if(rules.containsKey(var))

            rules.get(var).add(production);

        else{
            ArrayList<Production> newProduction = new ArrayList<Production>();
            newProduction.add(production);
            rules.put(var, newProduction);
        }
    }

    /**
     * Reads the variables, terminals and rules from the txt file.
     * Obs: There must not be '\t' tab character in the file.
     * @param text The name of the txt file
     * @throws FileNotFoundException
     */
    public void readFile(String text) throws FileNotFoundException{
        File tst = new File(text);
        Scanner sc = new Scanner(tst);
        String opFlag = "";
        String buff;
        buff = sc.nextLine().trim();

        while(sc.hasNext()) {

                buff = sc.nextLine().trim();
                if (buff.contains("Variaveis") || buff.contains("Inicial") || buff.contains("Regras") || buff.contains("Terminais")) {
                    if (buff.contains("Variaveis")) opFlag = "Variaveis";
                    if (buff.contains("Inicial")) opFlag = "Inicial";
                    if (buff.contains("Regras")) opFlag = "Regras";
                    if (buff.contains("Terminais")) opFlag = "Terminais";
                    buff = sc.nextLine().trim();
                }
            if (!buff.contains("</div></pre>")) {
                switch (opFlag) {
                    case "Terminais":
                        String terminal;
                        terminal = buff.substring(buff.indexOf('[') + 2, buff.indexOf(']') - 1);
                        this.terminals.add(terminal);
                        break;
                    case "Variaveis":
                        String var;
                        var = buff.substring(buff.indexOf('[') + 2, buff.indexOf(']') - 1);
                        this.variables.add(var);
                        break;
                    case "Inicial":
                        String inicio;
                        inicio = buff.substring(buff.indexOf('[') + 2, buff.indexOf(']') - 1);
                        this.initialVariable = inicio;
                        break;
                    case "Regras":
                        String variavel;
                        variavel = buff.substring(buff.indexOf('[') + 2, buff.indexOf(']') - 1);
                        ArrayList<String> bufferOfRules = new ArrayList<>();
                        float probability = 1; //default
                        int indexS = 0, indexE = 0;
                        char c;
                        for (int j = buff.indexOf('>'); j < buff.length(); j++) {
                            c = buff.charAt(j);
                            if (c == '[') indexS = j;
                            if (c == ']') {
                                indexE = j;
                                bufferOfRules.add(buff.substring(indexS + 2, indexE - 1));
                            }
                            if (c == ';') {
                                indexS = j;
                                try {
                                    probability = Float.valueOf(buff.substring(indexS + 1, indexS + 5));
                                } catch (IndexOutOfBoundsException exc) {
                                    probability = Float.valueOf(buff.substring(indexS + 1));
                                } catch (NumberFormatException e) {
                                    probability = 1;
                                }
                            }
                        }
                        this.addRule(variavel, new Production(bufferOfRules), probability);
                        break;
                }

            }
        }
        sc.close();
    }

    /**
     * Add all rules from the grammar giver to its rules
     * @param g Grammar to have its rules copied
     */
    public void addRules(Grammar g){
        for(String s : g.getVariables()){
            for(Production p : g.getProductions(s)){
                addRule(s, p);
            }
        }
    }

    /**
     *  Print all the rules of the grammar.
     *  In case it is a State, also prints the dot and the number of the set of each rule.
     */
    public void printGrammar(){
        for(String var : rules.keySet()) {
            for (Production p : rules.get(var)){
                System.out.print(var + " -> ");
                ArrayList<String> rightSide = p.getVarOrTerminals();
                for(int i = 0; i < rightSide.size()+1; i++){ //+1 due to possibility of * in the end of the world
                    if(i == p.getDotPos())
                        System.out.print(" * ");
                    if(i != rightSide.size())//p n dar erro de pegar um null no get
                    	System.out.print("[ " + rightSide.get(i) + " ]");
                }
                if(p.getProductionSet() != -1)
                    System.out.print("/" + p.getProductionSet());
               // System.out.println("  Probability = " + p.getProbability());
                System.out.println();
            }
        }
    }

    public String grammarToString(){
        StringBuilder gra = new StringBuilder();

        for(String var : rules.keySet()) {
            for (Production p : rules.get(var)){
                gra.append(var + " -> ");

                ArrayList<String> rightSide = p.getVarOrTerminals();
                for(int i = 0; i < rightSide.size()+1; i++){ //+1 due to possibility of * in the end of the world
                    if(i == p.getDotPos())
                        gra.append(" * ");
                    if(i != rightSide.size())//p n dar erro de pegar um null no get
                        gra.append("[ " + rightSide.get(i) + " ]");
                }
                if(p.getProductionSet() != -1)
                    gra.append("/" + Integer.toString(p.getProductionSet()));
                //System.out.println("  Probability = " + p.probability);
                gra.append("\n");
            }
        }

        return gra.toString();
    }

    /**
     *   Getters and Setters
     */
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
        if(rules.containsKey(var))
            return rules.get(var);
        return null;
    }

}
