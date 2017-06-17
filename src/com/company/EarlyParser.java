package com.company;

import javafx.util.Pair;

import java.util.*;


public class EarlyParser {

    private Grammar grammar;
    private ArrayList<Grammar> states;
    private String sentence;
    private String sentenceGenerated;

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
                Grammar temp = new Grammar();
                for(Production p : stateZero.getProductions(variables.get(i))){
                    String b = p.getFirstElement();
                        if (EarlyParser.isVariable(b)) {
                            if(!variables.contains(b))
                                variables.add(b);
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
                stateZero.adiciona(temp);
            }

        }while(increased); // While there is rules to add

        states.add(stateZero);
    }

    public boolean parse(String s){

        // Sentence to be parsed
        String[] sentence = s.split(" ");

        // Build state 0
        buildStateZero();

        int numberOfWords = sentence.length;

        // Step (1)
        for(int i = 1; i <= numberOfWords; i++){
            Grammar state = new Grammar();
            String actualWord = sentence[i - 1];

            // Step (2)
            Grammar previousState = states.get(i-1);
            for( String a: previousState.getVariables()){ //etapa 2, retorna todos o lado esq das regras
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

            }//End Step (2)

            boolean increased;
            do{
                increased = false;

                //Step (3)
                boolean out = false;
                do{
                   Grammar temp = new Grammar();
            	   out = true;
            	   for(String A : state.getVariables()){
            	       for(Production p: state.getProductions(A)){
            	           if(out == true){
            	               if(p.isDotEnd() == false){
                                   String B = p.getElementAtDot();
                                   if(!state.getVariables().contains(B)) {
                                       if(EarlyParser.isVariable(B)) {
                                           for(Production prod : grammar.getProductions(B)) {
                                               Production newP = new Production(prod);
                                               newP.setDotPos(0); //seta nova posi��o do ponto
                                               newP.setProductionSet(i);//seta em qual produ��o veio-> o slash

                                               temp.addRule(B, newP);//adiciona a regra nova
                                               increased = true;
                                               out = false;
                                            }
                                        }
                                    }
		                		}
	                		}
	                	}
                    }
	                state.adiciona(temp);
	           }while(!out);  //End Step (3)

               //Step (4)
               out = false;
               do{
                   Grammar temp = new Grammar();
            	   out = true;
	                for(String A : state.getVariables()){
	                    for(Production p: state.getProductions(A)){
	                        if(out == true){
	                            if(p.isDotEnd() == true){
	                                int s2 = p.getProductionSet();
		                			Grammar stateS = states.get(s2);
		                			for(String varDeS: stateS.getVariables()){
		                			    for(Production pDeS : stateS.getProductions(varDeS)) {
		                			        if(pDeS.isDotEnd() == false) {
		                			            String alpha = pDeS.getElementAtDot();
                                                if(alpha.equals(A)) {
                                                    Production newP = new Production(pDeS);
                                                    newP.incDot(); //seta nova posi��o do ponto
                                                    if(!state.containsRule(varDeS, newP)) {
                                                        temp.addRule(varDeS, newP);//adiciona a regra nova
                                                        increased = true;
                                                        out = false;
                                                    }
                                                }
                                            }
                                        }
		                			}
		                		}
	                		}
	                	}
	                }
	                state.adiciona(temp);
               }while(!out); //End Step(4)

            }while (increased);

            states.add(state);            
        }

        //Check if the sentence was succesfully parsed
        //There must be a "InitialVariable -> a * /0" rule in the last state
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
    	int i = 0;
        for(Grammar g : states) {
            System.out.println("State " + i++  + ":");
            g.printGrammar();
        }
    }
}