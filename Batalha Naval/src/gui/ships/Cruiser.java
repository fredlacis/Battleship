package gui.ships;

public class Cruiser extends Ship {

	private final int CRUISER_SIZE = 4; 

	public Cruiser(int x, int y) {

		setBounds(x, y, SQUARE_SIZE*CRUISER_SIZE, SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(CRUISER_SIZE);
	}

}