package gui.ships;

import main.K;

@SuppressWarnings("serial")
public class Destroyer extends Ship {

	private final int DESTROYER_SIZE = 2;
	private static final int DESTROYER_POSITION = 4;
	
	private static Destroyer destroyer;
	
	public static Destroyer getDestroyer() {
		if(destroyer == null) {
			destroyer = new Destroyer(OFFSET_X, OFFSET_Y*DESTROYER_POSITION);
		}
		
		return destroyer;
	}
	
	public void selfDestroy() {
		destroyer = null;
	}


	private Destroyer(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*DESTROYER_SIZE, K.SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(DESTROYER_SIZE);
	}

}