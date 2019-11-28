package gui.ships;

import main.K;

@SuppressWarnings("serial")
public class Battleship extends Ship {

	private static final int BATTLESHIP_SIZE = 5;
	private static final int BATTLESHIP_POSITION = 2;
	
	private static Battleship battleship;
	
	public static Battleship getBattleship() {
		if(battleship == null) {
			battleship = new Battleship(OFFSET_X, OFFSET_Y*BATTLESHIP_POSITION);
		}
		
		return battleship;
	}
	
	public void selfDestroy() {
		battleship = null;
	}

	private Battleship(int x, int y) {

		setBounds(OFFSET_X, OFFSET_Y*BATTLESHIP_POSITION, K.SQUARE_SIZE*BATTLESHIP_SIZE, K.SQUARE_SIZE);
		setOpaque(false);

		super.paintSquares(BATTLESHIP_SIZE);
	}

}