# FunkGenerator

This is a third semester university project in which we did a Funk Generator using the Earley algorithm.
A parse implemantation using the Earley algorithm.
It is possible to check if a sentence is part of a given grammar and also to generate a random sentence of any grammar.

## To run the project you have to do:

Run the FunkGenerator.jar

It is important to keep the "batidao.mp3" file in the same folder as the executable

You can also import it as a Java Project

## How the archive with your grammar has to be sent

```
Terminais        # comments are preceded by '#'
[ the ]          # terminals section
[ a ]
[ an ]
[ dog ]
[ cat ]
[ apple ]
[ park ]
[ in ]
[ with ]
[ ate ]
[ chased ]
[ liked ]
Variaveis       # variables section
[ S ]
[ NP ]
[ VP ]
[ PP ]
[ Det ]
[ N ]
[ P ]
[ V ]
Inicial         # the initial variable (it must be only one)
[ S ]
Regras          # the rules of the grammar
[ S ] > [ NP ] [ VP ] 
[ NP ] > [ Det ] [ N ] 
[ NP ] > [ NP ] [ PP ] 
[ VP ] > [ V ] 
[ VP ] > [ V ] [ NP ]
[ VP ] > [ VP ] [ PP ] 
[ PP ] > [ P ] [ NP ] 
[ Det ] > [ the ]
[ Det ] > [ a ] 
[ Det ] > [ an ] 
[ N ] > [ dog ]
[ N ] > [ cat ] 
[ N ] > [ apple bla bla ] 
[ N ] > [ park ]
[ P ] > [ in ] 
[ P ] > [ with ] 
[ V ] > [ ate ] 
[ V ] > [ chased ]
[ V ] > [ liked ]
```

## Authors:

* **Lucas Alegre** https://github.com/LucasAlegre
* **Catarina Nogueira** https://github.com/cvrnogueira
* **Guilherme Haetinger** https://github.com/gujueno
