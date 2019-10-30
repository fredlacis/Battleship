package gui.ships;

import java.awt.event.MouseEvent;

import main.K;

public class Destroyer extends Ship {

	private final int DESTROYER_SIZE = 2; 

	public Destroyer(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*DESTROYER_SIZE, K.SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(DESTROYER_SIZE);
	}

}