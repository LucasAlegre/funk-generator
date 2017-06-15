package com.company;

import javafx.util.Pair;

import java.util.*;


public class EarlyParser {

    private Grammar grammar;
    private ArrayList<Grammar> states;
    private String sentence;
    private String funkGenerated;

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
                for(Production p : stateZero.getProductions(variables.get(i))){
                    String b = p.getFirstElement();

                    if(EarlyParser.isVariable(b)){
                        variables.add(b);
                        for(Production p2 : grammar.getProductions(b)){
                            Production prod = new Production(p2);
                            prod.setDotPos(0);
                            prod.setProductionSet(0);
                            stateZero.addRule(b, prod);
                            increased = true;
                        }
                    }
                }
                count = i;
            }

        }while(increased); // While there is rules to add

        states.add(stateZero);
    }

    public void parse(String s){

        String[] sentence = s.split(" ");

        buildStateZero();

        for(int i = 1; i <= sentence.length; i++){
            Grammar state = new Grammar();  
            Grammar previousState = states.get(i-1);
            	for( String a: previousState.getVariables()){ //etapa 2, retorna todos o lado esq das regras
            		for(Production p: previousState.getProductions(a) ){//p cada lado esq tamos vendo os lados direitos
            			if(p.isDotEnd() == false){
	            			if(p.getElementAtDot().equals(sentence[i-1])){//se o elemento pos ponto for igual a palavra da pessoa
	            				//significa que eu tenho de adicionar a regra no conjunto de produções atual
	            				Production newP = new Production(p); 
	            				newP.incDot();
	            				state.addRule(a, newP);
	            			}
            			}
            		}
            		
            	}
            //end etapa (2)

            boolean increased;
            //inicio etapa 3
            ArrayList<String> variables = new ArrayList<String>(state.getVariables());
            int count = 0;
            do{
                increased = false;
                    for(int j = count; j < variables.size(); j++){
                        for(Production p : state.getProductions(variables.get(j))){
                            if(p.isDotEnd()){
                             int s2 = p.getProductionSet();//devolve a zero essa linha
                             String A = variables.get(j);//Np->b*/0 devolve a np essa linha
                             for(String b: states.get(s2).getVariables()){ //states.get devolve a gramatica e pego as variaveis dela
                            	 //buscar todas as regras que tem np no ds2, tipo, no d0
                            	 for(Production producaoDeCadaVariavel: states.get(s2).getProductions(b)){
                            		 if(!producaoDeCadaVariavel.isDotEnd()){
                            			 if(producaoDeCadaVariavel.getElementAtDot().equals(A)){
                            				 Production novissima = new Production(producaoDeCadaVariavel);
                            				 novissima.incDot();
                            				 state.addRule(b, novissima);
                            			 }
                            		 }
                            	 }
                             }
                            	//vai pro estado 3 se nao vai pro 4
                            }
	                         else{
	                            	System.out.println("estado 3");
	                            	String b = p.getElementAtDot();
	                            	for(Production backToGramatica: grammar.getProductions(b)){
	                            		Production nova = new Production(backToGramatica);
	                            		nova.setDotPos(0);
	                            		nova.setProductionSet(i);
	                            		state.addRule(b, nova);
	                            	}
	                            }
                        }
                        count = j;
                    }
                //end: etapa(3)*/


                //TODO: etapa(4)
                // se o ponto esta no final da palavra, pegar o valor que esta no /numero e avançar o ponto, por fim, 
                //colocá-lo no array de variaveis do estado atual
                
              /*  for( String a: state.getVariables()){
                    for (Production p : state.getProductions(a)){
                        	if(p.isDotEnd() == true){
                            	//ta no final da palavra, vou pegar a nova coisa pra add aq
                        		ArrayList<Production> newProductions= new ArrayList<>();
                        		newProductions.add(p);//pego a production aq
			//state.addRule(a, newP); fazer p array list
                            }
                            	
                        }
                }*/
                    	
            }while (increased);

            states.add(state);
        }

        //TODO: check se parseou ou nÃ£o

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