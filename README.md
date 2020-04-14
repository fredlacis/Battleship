# Battleship
A project originally developed for a object oriented programming course at the Pontifical Catholic University of Rio de Janeiro, implemented in Java, with the help of the GUI widget toolkit, Swing. The entire project was developed with Eclipse, in 1024x768 resolution.
</br>

---

</br>

## Index
<!--ts-->
   * [Installation](#installation)
   * [Game Rules](#game-rules)
      * [Starting a New Game](#starting-a-new-game)
      * [Positioning Phase](#positioning-phase)
      * [Attack Phase](#attack-phase)
<!--te-->
</br>

---

</br>

## Installation
* To run the game simply download the .jar executable by [clicking here](https://github.com/fredlacis/Battleship/releases/download/1.0/BN_Iteracao4.jar). 

* To run the game in debug mode or fork the project it is recommended to clone the repository in Eclipse.

> In any of the cases you need to have a running installation of the Java SE Runtime Environment, [available here](https://www.oracle.com/technetwork/pt/java/javase/downloads/jre8-downloads-2133155.html).
</br>

---

</br>

## Game Rules

### Starting a new game
Upon opening the game, the players will be met with a menu, in which they are able to choose to play a new game, load a previously played game or exit back to the desktop. Upon starting a new game, the players are required to inform their names, so they can be identified further in the match.

</br>
![Starting Menu](https://i.imgur.com/XfstbUW.gif)
</br>

### Positioning Phase
Upon starting a game, both players must choose where to position their ships. Each one must position all ships to advance to the next phase. Player 1 goes first, while Player 2 looks away. Then Player 2 places his/her ships, while Player 1 looks away. The rules for placing ships are the following:
* The player cannot place ships outside the borders
* The player cannot place ships directly adjacent or diagonally to other ships

</br>
![Rotation and Validation](https://i.imgur.com/ffOX4Oo.gif)
</br>

### Attack Phase
In this phase, players take turns attacking each other. While one player attacks, the other looks away. For every turn, the player has 3 attacks available. If a player hits all the parts of a ship, it is considered sunk. Once a player sinks every ship of his adversary, he wins the game.

</br>
![Attacking Phase](https://i.imgur.com/ffOX4Oo.gif)
</br>
</br>
</br>


