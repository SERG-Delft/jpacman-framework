# 4th Report - ESOF

## Index

1. [Introduction](#introduction)
2. [Testability](#testability)
3. [Test Statistics](#test-statistics)
4. [Bugs] (#bugs)
4. [Critical Analysis](#critical-analysis)



## Introduction

> Software testability is the degree to which a software artifact (i.e. a software system, software module, requirements- or design document) supports testing in a given test context. If the testability of the software artifact is high, then finding faults in the system (if it has any) by means of testing is easier.

>  _Definition from [Wikipedia]_
[Wikipedia]: https://en.wikipedia.org/wiki/Software_testability

In this report we will check **JPacman**'s degree of testability by analising some factors like:
- **Controllability**: The degree to which it is possible to control the state of the component under test (CUT) as required for testing.
- **Observability**: The degree to which it is possible to observe (intermediate and final) test results.
- **Isolateability**: The degree to which the component under test (CUT) can be tested in isolation.
- **Separation of concerns**: The degree to which the component under test has a single, well defined responsibility.
- **Understandability**: The degree to which the component under test is documented or self-explaining.
- **Heterogeneity**: The degree to which the use of diverse technologies requires to use diverse test methods and tools in parallel.



## Testability 

By talking with some contributtors to the **JPacman Project** and by reading some information about the project on **Google** we learned that **JPacman** turns the test driven approach very easy and that the language lends itself well to testing because first **JPacman** project is written in Java that is a language with an easy approach and because the project itself is very well organized in different packages that turn a lot more easy tofind the information we are looking for to test. 

This distribution makes also very easy to issolate the different components of the project we want to test that by consequence makes more easy to analyse the information gathered after testing which makes more easy to raise the Test Coverage and to find new bugs. For example, when we want to give a certain behavior to a set of types, we create a trait and implement it for each type and the trait itself can be tested independently. 

During the tests, the compiler performs the heavy lifting when it comes to common bugs, so you can focus on other things and the language is a good match with unitary tests which leads to easily composable code. It is very simple to maintain coverage and to deploying the service. 

For JPacman the usual practice is to have tests for some specific action like create board,move player,capture food,collision,etc.

One of the biggest concerns about the testability of JPacman is the lack of support for handeling Exceptions and some type of Collisions. 


As with any project, the project itself plays a big role. That said, for unit testing JPacman´s existing test tools are more than adequate. Integration testing may be more difficult, depending on what you're integrating, but Eclipse supports all the tools to create custom tests that sometimes may be hard to write but that in the end work very well.



## Test Statistics

In this Chapter we will analyse all the statistics gathered during the analyze of **JPacman** project.

We used JUnit plgu-in of Eclipse to run unitary tests made to the project and we found that we can reach the maximum coverage 82% due to the problems we explained in the previous chapter.

As you can see in the next image there are some areas that are not very well covered in this project:

![alt tag](https://raw.githubusercontent.com/hpnog/jpacman-framework/master/ESOF-docs/test.png)


## Bugs

By analising jpacman we found out that the game has some bugs in the colision matter. 

The game doesnt handle bery well the colissions between ghosts and pacman itself and sometimes during the gameplay pacman passes by a ghost and nothing happens situation that isnt very correct.

In other matter we foun out that the game also doesnt recognize end-game situation because every time you win or loose the game stops working and the screen stays static until you close the apllication.

Also from what we discovered in the Testability chapter the program doesnt handle very well when an exception appears.

## Critical Analysis
**JPacman** is a well organized project with a lot of contributors and very active. 

The information was very easy to obtain and from all the information we gathered together we can say that the project is well organized and structured.

 The code is organized in different packages(level,board,enemy,player,sprite,etc) what makes even more easy to analyze the different tests made for this project.
 
 In the end we can say tha beside some bugs the project may have we enjoyed even more working in this project because the theme is very motivating and the language in which the project was made(Java) is very acessible and atractive to work with.

Regarding the _test statistics_ we used Eclemma to discouver the test coverage regardin any information about any _test statistics_ related to tests run on JPacman.

