package gui.ships;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public abstract class Ship extends JComponent {
	
	private Rectangle2D.Double squares[];
	
	public void paintSquares(int squareNumbers) {
		
		squares = new Rectangle2D.Double[squareNumbers];
		
		for(int i = 0; i < squareNumbers; i++) {
			squares[i] = new Rectangle2D.Double();
			squares[i].height = 30.6666666;
			squares[i].width = 30.6666666;
			squares[i].x = i * 30.6666666;
			squares[i].y = 0;
		}
		
		repaint();
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		System.out.println("DestroyerPaintComponent");
		
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        for(int i = 0; i < squares.length; i++) {
        	g2d.setColor(Color.ORANGE);
			g2d.fill(squares[i]);
			g2d.setColor(Color.BLACK);
			g2d.draw(squares[i]);
		}
        
        
	}
	
}
