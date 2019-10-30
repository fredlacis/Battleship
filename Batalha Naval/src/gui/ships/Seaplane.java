package gui.ships;

import java.awt.event.MouseEvent;

import main.K;

public class Seaplane extends Ship {

	private final int SEAPLANE_SIZE_X = 3; 
	private final int SEAPLANE_SIZE_Y = 2; 

	public Seaplane(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*SEAPLANE_SIZE_X, K.SQUARE_SIZE*SEAPLANE_SIZE_Y);
		setOpaque(false);

		//paintSquares(BATTLESHIP_SIZE);
	}

}