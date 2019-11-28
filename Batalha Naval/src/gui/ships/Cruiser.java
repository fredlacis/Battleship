package gui.ships;

import main.K;

@SuppressWarnings("serial")
public class Cruiser extends Ship {

	private final int CRUISER_SIZE = 4;
	private static final int CRUISER_POSITION = 3;
	
	private static Cruiser cruiser;
	
	public static Cruiser getCruiser() {
		if(cruiser == null) {
			cruiser = new Cruiser(OFFSET_X, OFFSET_Y*CRUISER_POSITION);
		}
		
		return cruiser;
	}
	
	public void selfDestroy() {
		cruiser = null;
	}

	private Cruiser(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*CRUISER_SIZE, K.SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(CRUISER_SIZE);
	}

}