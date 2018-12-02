JPacman Scenarios
=================

Arie van Deursen, Delft University of Technology.


## 1. Background

This document describes a series of JPacman user scenarios, following the format of [behavior-driven development](http://dannorth.net/whats-in-a-story/).

Thus, each scenario is of the form:

---
 **Title** _one line describing the story_

**Narrative**

    As a [role]
     I want [feature]
    So that [benefit]

**Acceptance Criteria,** _presented as scenarios of the form:_

    Scenario 1: Title
    Given [context]
     and  [some more context]...
    When  [event] 
    Then  [outcome]
     and  [another outcome]...
----


## 2. JPacman Overview

JPacman is a very simple JPacman derivative, to be used for educational purposes. Essential features such as multiple levels and multiple players, energizers and different ghost types are postponed for later releases (and may be implemented by students as an exercise).  Furthermore, to give the game a somewhat different flavor, JPacman deviates from standard Pacman behavior in several ways.  This document describes the requirements as a series of use cases, and explains what the GUI should look like.

The JPacman game is played on a rectangular board.  A square on the board can be empty, or can contain the Pacman itself, one of the several ghosts, a pellet (worth 10 points), or a wall. Moveable characters such as the Pacman and the ghosts can make single-step horizontal or vertical moves.  Tunnels on the border make it possible to move from one border to the opposite border.  When the Pacman moves over a square containing a pellet, the player earns points and the pellet disappears. If a player and a ghost meet at the same square, the the game is over. The player wins the game once he or she has eaten all pellets.


## 3. User Stories

#### Story 1: Startup

```
As a player
 I want to start the game
so that I can actually play
 
Scenario S1.1: Start.
Given the user has launched the JPacman GUI;
When  the user presses the "Start" button;
Then  the game should start.
```


#### Story 2: Move the Player

```
As a player, 
 I want to move my Pacman around on the board;
So that I can earn all points and win the game.

Scenario S2.1: The player consumes
Given the game has started,
 and  my Pacman is next to a square containing a pellet;
When  I press an arrow key towards that square;
Then  my Pacman can move to that square,
 and  I earn the points for the pellet,
 and  the pellet disappears from that square.

Scenario S2.2: The player moves on empty square
Given the game has started,
 and  my Pacman is next to an empty square;
When  I press an arrow key towards that square;
Then  my Pacman can move to that square
 and  my points remain the same.

Scenario S2.3: The move fails
Given the game has started,
  and my Pacman is next to a cell containing a wall;
When  I press an arrow key towards that cell;
Then  the move is not conducted.

Scenario S2.4: The player dies
Given the game has started,
 and  my Pacman is next to a cell containing a ghost;
When  I press an arrow key towards that square;
Then  my Pacman dies,
 and  the game is over.
  
Scenario S2.5: Player wins, extends S2.1
When  I have eaten the last pellet;
Then  I win the game.
```


#### Story 3: Move The Ghost
```
As a ghost;
 I get automatically moved around;
So that I can try to kill the player.

Scenario S3.1: A ghost moves.
Given the game has started,
 and  a ghost is next to an empty cell;
When  a tick event occurs;
Then  the ghost can move to that cell.

Scenario S3.2: The ghost moves over a square with a pellet.
Given the game has started,
 and  a ghost is next to a cell containing a pellet;
When  a tick event occurs;
Then  the ghost can move to the cell with the pellet,
 and  the pellet on that cell is not visible anymore.

Scenario S3.3: The ghost leaves a cell with a pellet.
Given a ghost is on a cell with a pellet (see S3.2);
When  a tick even occurs;
Then  the ghost can move away from the cell with the pellet,
 and  the pellet on that cell is is visible again.

Scenario S3.4: The player dies.
Given the game has started,
 and  a ghost is next to a cell containing the player;
When  a tick event occurs;
Then  the ghost can move to the player,
 and  the game is over.
```

#### Story 4: Suspend the Game

```
As a player,
 I want to be able to suspend the game;
So  that I can pause and do something else.

Scenario S4.1: Suspend the game.
Given the game has started;
When  the player clicks the "Stop" button;
Then  all moves from ghosts and the player are suspended.

Scenario S4.2: Restart the game.
Given the game is suspended;
When  the player hits the "Start" button;
Then  the game is resumed.
```

## 3. User Interface

The user interface for JPacman is relatively simple.  The game is
rectangular board, which can be read from a special text file with
simple character encoding. On the GUI, special (animated) images or
colored squares are used for the ghost, food, empty cells, and wall
cells on the board.  The direction of the last (attempted) move is
reflected in the image used for the player. The GUI furthermore
contains a "Start", and "Stop" button (at the bottom of the
GUI), as well as an indicator for the amount of food eaten and the
game's overall state (playing, game won, player died, ready to start
the play). 

## 4. Development Requirements

JPacman should be developed in Java. It should be set up so that it
can easily be used by (the latest versions of) standard (open source) Java development tools, such as maven, JUnit, Eclipse/IntelliJ, cobertura, etc. To allow for working with maven, the maven standard directory structure should be used.  Since the educational purposes include testing, JPacman should be delivered with an extensive test suite.

----
[![Creative Commons License](http://i.creativecommons.org/l/by-sa/4.0/88x31.png)](http://creativecommons.org/licenses/by-sa/4.0/)
