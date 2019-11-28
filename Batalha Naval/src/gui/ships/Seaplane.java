package gui.ships;

import java.awt.geom.Rectangle2D;

import main.K;

@SuppressWarnings("serial")
public class Seaplane extends Ship {

	private final int SEAPLANE_SIZE_X = 3; 
	private final int SEAPLANE_SIZE_Y = 2;
	
	private static final int SEAPLANE_POSITION = 1;
	
	private static Seaplane seaplane;
	
	public static Seaplane getSeaplane() {
		if(seaplane == null) {
			seaplane = new Seaplane(OFFSET_X, OFFSET_Y*SEAPLANE_POSITION-25);
		}
		
		return seaplane;
	}
	
	public static void selfDestroy() {
		seaplane = null;
	}


	private Seaplane(int x, int y) {

		setBounds(x, y, K.SQUARE_SIZE*SEAPLANE_SIZE_X, K.SQUARE_SIZE*SEAPLANE_SIZE_Y);
		setOpaque(false);
		
		paintSquares(3);		
	}
	
	@Override
	public void paintSquares(int squareNumbers) {
		
		squares = new Rectangle2D.Double[squareNumbers];
		
		squares[0] = new Rectangle2D.Double(0, K.SQUARE_SIZE, K.SQUARE_SIZE, K.SQUARE_SIZE);
		squares[1] = new Rectangle2D.Double(K.SQUARE_SIZE, 0, K.SQUARE_SIZE, K.SQUARE_SIZE);
		squares[2] = new Rectangle2D.Double(2*K.SQUARE_SIZE, K.SQUARE_SIZE, K.SQUARE_SIZE, K.SQUARE_SIZE);
		
		shipSize = squares.length;
		
		setColor(getOriginalColor());
		setBorderColor(getOriginalColor().darker());
		
		addMouseListener(this);

		repaint();
		
	}

}