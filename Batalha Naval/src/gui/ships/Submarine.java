package gui.ships;

public class Submarine extends Ship {

	private final int SUBMARINE_SIZE = 1; 

	public Submarine(int x, int y) {

		setBounds(x, y, SQUARE_SIZE*SUBMARINE_SIZE, SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(SUBMARINE_SIZE);
	}

}
