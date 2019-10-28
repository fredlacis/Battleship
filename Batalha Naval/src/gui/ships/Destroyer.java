package gui.ships;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Destroyer extends Ship {
	
	private final int SIZE = 2; 
	
	public Destroyer() {
		setBounds(0, 0, SIZE*31, 31);
		setOpaque(false);
		
		super.paintSquares(SIZE);
		
		//setPreferredSize(new Dimension(200, 200));
	}
	
}
