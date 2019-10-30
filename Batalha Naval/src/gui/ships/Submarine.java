package gui.ships;

import main.K;

public class Submarine extends Ship {

	private final int SUBMARINE_SIZE = 1; 

	public Submarine(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*SUBMARINE_SIZE, K.SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(SUBMARINE_SIZE);
	}

}
