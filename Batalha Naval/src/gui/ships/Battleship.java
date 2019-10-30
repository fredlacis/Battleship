package gui.ships;

import java.awt.event.MouseEvent;

import main.K;

public class Battleship extends Ship {

	private final int BATTLESHIP_SIZE = 5; 

	public Battleship(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*BATTLESHIP_SIZE, K.SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(BATTLESHIP_SIZE);
	}

}