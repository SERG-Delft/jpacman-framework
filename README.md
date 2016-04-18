![build status](https://travis-ci.org/SERG-Delft/jpacman-framework.svg?branch=master)

JPacman-Framework
=================

About
-----

Pacman-like game used for teaching software testing.
It exposes students to the use of git, maven, JUnit, and mockito.

Parts of the code are well tested, whereas others are left untested intentionally. As a student in software testing, you can extend the test suite, or use the framework to build extensions in a test-driven way. As a teacher, you can use the framework to create your own testing exercises.

We have developed and are using this code at a software testing course at Delft University of Technology, The Netherlands. Teachers interested in seeing the exercises I use there are invited to contact me.

Other universities who have used this material include Antwerp, Mons, Eindhoven, and UBC (Vancouver).
At TU Delft, we use it in combination with [DevHub](https://github.com/devhub-tud/devhub) as git, continuous integration, and feedback server.

If you have any suggestions on how to improve this framework, please do not hesitate to contact us, open issue, or provide a pull request. Since testing is deliberately left as an exercise, pull requests that "solve" exercises or offer full coverage are less likely to be merged.

Main contributors:

*	Arie van Deursen (versions 1.0-5.x, 2003-2013, updates to versions 6.x and further, 2014-...)
*	Jeroen Roosen (major rewrite, version 6.0, 2014)


Getting Started
---------------

1. Git clone the project
2. If you use Eclipse:
	1. Import
	2. Right Click -> Configure -> Convert to Maven Project
3. To see JPacman in action: run `nl.tudelft.jpacman.Launcher`
4. To run the test suite in maven: `mvn test`
5. To run the test suite in Eclipse: right click -> run as -> JUnit Test.
	 
