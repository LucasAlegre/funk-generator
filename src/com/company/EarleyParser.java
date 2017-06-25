package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *  Class to implement the Earley Algorithm:
 *
 *    D0 = ∅
 *    para toda S → α ∈ P (1)
 *       faça D0 = D0 ∪ { S → •α/0 }
 *    repita para toda A → •Bβ/0 ∈ D0 (2)
 *        faça para toda B → φ ∈ P
 *           faça D0 = D0 ∪ { B → •φ/0 }
 *    até que o cardinal de D0 não aumente  --- Build StateZero
 *
 *     para r variando de 1 até n (1)
 *     faça Dr = ∅;
 *        para toda A → α•arβ/s ∈ Dr-1      --- Step(2)
 *        faça Dr = Dr ∪ { A → αar•β/s };
 *        repita
 *           para toda A → α•Bβ/s ∈ Dr      --- Step(3)
 *           faça para toda B → φ ∈ P
 *               faça Dr = Dr ∪ { B → •φ/r }
 *           para toda A → α•/s de Dr       --- Step(4)
 *           faça para toda B → β•Aφ/k ∈ Ds
 *               faça Dr = Dr ∪ { B → βA•φ/k }
 *        até que o cardinal de Dr não aumente
 *
 */
public class EarleyParser {

    private Grammar grammar;
    private ArrayList<Grammar> states;
    private ArrayList<String> sentenceParsed;
    private String sentenceGenerated;
    private int numberOfWords;

    private Grammar temp; // Auxiliar to add new rules
    private boolean increased; // Flag to indicate that new rules were added

    /**
     *  Dafault constructor
     */
    public EarleyParser(){
        states = new ArrayList<Grammar>();
        sentenceParsed = new ArrayList<String>();
    }

    /**
     * Gets the actual grammar.
     * @return The grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }

    /**
     * Sets the grammar that will be used in the Earley Algorithm
     * @param grammar The grammar given
     */
    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    /**
     *  Builds the first set of productions,
     *  the productions that can be applied in successive applications of the initial variable.
     */
    private void buildStateZero(){


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
                Grammar temp = new Grammar();
                // For each production the variable at i
                for(Production p : stateZero.getProductions(variables.get(i))){
                    String b = p.getFirstElement();
                        if (EarleyParser.isVariable(b)) {
                            if(!variables.contains(b))
                                variables.add(b);
                            //Put in the stateZero all rules of the variable in the grammar
                            for (Production p2 : grammar.getProductions(b)) { //coloca todas regras no temp de tal variavel
                                Production prod = new Production(p2);
                                prod.setDotPos(0);
                                prod.setProductionSet(0);
                                if(!stateZero.getVariables().contains(b)) { //como iteramos sobre tudo varias vezes, isso evita de ser colocado duas vezes
                                    temp.addRule(b, prod);
                                    increased = true;
                                }
                            }
                        }
                }
                count = i;
                // Copy temp to stateZero
                stateZero.addRules(temp);
            }

        }while(increased); // While there is rules to add

        states.add(stateZero);
    }

    /**
     *  Step (2) : Parse the actualWord, adding the rules that generate it
     *  @param state The actual state
     *  @param i the index of the actual state
     */
     private void scanning(Grammar state, int i){

         Grammar previousState = states.get(i - 1);
         String actualWord = sentenceParsed.get(i - 1);

         for(String a: previousState.getVariables()){ //etapa 2, retorna todos o lado esq das regras
             for(Production p: previousState.getProductions(a) ){//p cada lado esq tamos vendo os lados direitos
                 if(p.isDotEnd() == false){
                     if(p.getElementAtDot().equals(actualWord)){//se o elemento pos ponto for igual a palavra da pessoa
                         //significa que eu tenho de adicionar a regra no conjunto de produ��es atual
                         Production newP = new Production(p);
                         newP.incDot();
                         state.addRule(a, newP);
                     }
                 }
             }
         }
     }

    /**
     *  Step (2) : Parse a random word, adding the rules that generate it
     *  @param state The actual state
     *  @param i the index of the actual state
     */
    private void randomScanning(Grammar state, int i){

        Grammar previousState = states.get(i - 1);
        ArrayList<String> terminals = new ArrayList<String>();

        for(String a : previousState.getVariables()){
            for(Production p : previousState.getProductions(a)){
                if(p.isDotEnd() == false) {
                    String atDot = p.getElementAtDot();
                    if (isTerminal(atDot)) {
                        terminals.add(atDot);
                    }
                }
            }
        }

        Random random = new Random();
        String actualWord = terminals.get( random.nextInt(terminals.size()) );
        this.sentenceGenerated += actualWord + " ";
        this.sentenceParsed.add(actualWord);
        System.out.println(sentenceGenerated);

        for(String a: previousState.getVariables()){ //etapa 2, retorna todos o lado esq das regras
            for(Production p: previousState.getProductions(a) ){//p cada lado esq tamos vendo os lados direitos
                if(p.isDotEnd() == false){
                    if(p.getElementAtDot().equals(actualWord)){//se o elemento pos ponto for igual a palavra da pessoa
                        //significa que eu tenho de adicionar a regra no conjunto de produ��es atual
                        Production newP = new Production(p);
                        newP.incDot();
                        state.addRule(a, newP);
                    }
                }
            }
        }
    }


    /**
     *  Step (3) : Add rules that can generate the next word
     *  @param state The actual state
     *  @param i The index of the actual state
     *  @param p The production that contains at the dot the variable that will generate new rules to the state
     */
     private void predictor(Grammar state, int i, Production p){
         String B = p.getElementAtDot();
         if( isVariable(B) ) {
             for(Production prod : grammar.getProductions(B)) {

                 Production newP = new Production(prod);
                 newP.setDotPos(0); //seta nova posi��o do ponto
                 newP.setProductionSet(i);//seta em qual produ��o veio-> o slash

                 if( !state.containsRule(B, newP) && !temp.containsRule(B, newP)) {
                     temp.addRule(B, newP);//addRules a regra nova
                     increased = true;
                 }
             }
         }
     }

    /**
     *  Step (4) : Add rules that refer to the variable A
     *  @param state The actual state
     *  @param A the left side of the rule
     *  @param p the right side of the rule
     */
    private void completer(Grammar state, String A, Production p){
        int sIndex = p.getProductionSet();
        Grammar stateS = states.get(sIndex);
        // For each rule of stateS (Ds)
        for(String varDeS: stateS.getVariables()){
            for(Production pDeS : stateS.getProductions(varDeS)) {
                if(pDeS.isDotEnd() == false) {
                    String alpha = pDeS.getElementAtDot();
                    if(alpha.equals(A)) {
                        Production newP = new Production(pDeS);
                        newP.incDot();
                        if( !state.containsRule(varDeS, newP) && !temp.containsRule(varDeS, newP) ) {
                            temp.addRule(varDeS, newP);
                            increased = true;
                        }
                    }
                }
            }
        }
    }

    /**
     *   Check if the sentence was succesfully parsed.
     *   There must be a "InitialVariable -> a * /0" rule in the last state
     *   @return True if the sentence was successfully parse, false otherwise
     */
    private boolean checkParse(){

        Grammar lastState = states.get(numberOfWords);
        for(String A: lastState.getVariables() ){
            if(A == grammar.getInitialVariable()) {
                for (Production ofLastGrammar : lastState.getProductions(A)) {
                    if (ofLastGrammar.isDotEnd() == true && ofLastGrammar.getProductionSet() == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Parse the sentence given with the Earley Algorithm.
     * @param s The sentence given
     *    Obs:
     *
     * @return True if the sentence is part of the grammar and was successfully parsed, false otherwise.
     */
    public boolean parse(String s){

        // Clear from last parsing
        states.clear();

        // Sentence to be parsed
        this.sentenceParsed = sentenceToArrayList(s);

        // Build state 0
        buildStateZero();

        this.numberOfWords = sentenceParsed.size();

        // Step (1)
        for(int i = 1; i <= numberOfWords; i++){
            Grammar state = new Grammar();

            // Step (2)
            scanning(state, i);

            do{
                increased = false;

               temp = new Grammar();
               // For each rule in the state
               for(String A : state.getVariables()){
                       for(Production p : state.getProductions(A)){

                       //Step (3) : Add rules that can generate the next word
                       if(p.isDotEnd() == false){
                            predictor(state, i, p);
                        }
                       //Step (4) : Add rules that refer to the variable A
                       else{
                            completer(state, A, p);
                       }
                   }
               }
               state.addRules(temp);

            }while(increased); //While new rules are being added

            states.add(state);            
        }

        return checkParse();

    }

    public String generateRandom(int size){
        // Clear from last parsing
        this.states.clear();

        this.sentenceGenerated = new String();
        this.numberOfWords = size;

        // Build state 0
        buildStateZero();

        // Step (1)
        for(int i = 1; i <= numberOfWords; i++){
            Grammar state = new Grammar();

            // Step (2)
            randomScanning(state, i);

            do{
                increased = false;

                temp = new Grammar();
                // For each rule in the state
                for(String A : state.getVariables()){
                    for(Production p : state.getProductions(A)){

                        //Step (3) : Add rules that can generate the next word
                        if(p.isDotEnd() == false){
                            predictor(state, i, p);
                        }
                        //Step (4) : Add rules that refer to the variable A
                        else{
                            completer(state, A, p);
                        }
                    }
                }
                state.addRules(temp);

            }while(increased); //While new rules are being added

            states.add(state);
        }

        return this.sentenceGenerated;
    }

    /**
     * Check if the string given is a Variable
     * @param s
     * @return True if it's a variable
     */
    public static boolean isVariable(String s){
        if(Character.isUpperCase(s.charAt(0)))
            return true;
        else
            return false;
    }

    /**
     * Check if the word given is a terminal, not a variable.
     * @param s
     * @return True if it is a terminal.
     */
    public boolean isTerminal(String s){
        return grammar.containsTerminal(s);
    }

    /**
     * Prints all set of productions generated in the parsing of the sentence.
     */
    public void printStates(){
    	int i = 0;
        for(Grammar g : states) {
            System.out.println("D" + i  + ":  "  + (i != 0 ? sentenceParsed.get(i-1) : ""));
            g.printGrammar();
            i++;
        }
    }

    public String statesToString(){
        int i = 0;
        StringBuilder s = new StringBuilder();
        for(Grammar g : states) {
            s.append("\nD" + i + ":  " + (i != 0 ? sentenceParsed.get(i-1) : "") + "\n");
            s.append(g.grammarToString());
            i++;
        }
        return s.toString();
    }

    /**
     * Given the string to be parsed, split it into an ArrayList,
     * If the a token is between " ", it becomes a single terminal:
     * Ex: "Hi "Mr Robot" !" => [ "hi", "Mr Robot", "!" ]
     * @param s The string to be parsed
     * @return The ArrayList of the terminals of the sentence
     */
    public static ArrayList<String> sentenceToArrayList(String s){

        String regex = "\"([^\"]*)\"|(\\S+)";
        ArrayList<String> list = new ArrayList<String>();

        Matcher m = Pattern.compile(regex).matcher(s);
        while (m.find()) {
            if (m.group(1) != null) {
               // x.add( "\"" + m.group(1) + "\"" );
                list.add( m.group(1) );
            } else {
                list.add( m.group(2) );
            }
        }

        return list;
    }
}