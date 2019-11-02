package gui.ships;

import main.K;

@SuppressWarnings("serial")
public class Cruiser extends Ship {

	private final int CRUISER_SIZE = 4; 

	public Cruiser(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*CRUISER_SIZE, K.SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(CRUISER_SIZE);
	}

}