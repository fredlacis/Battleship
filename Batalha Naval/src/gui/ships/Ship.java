package gui.ships;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public abstract class Ship extends JComponent implements MouseListener {

	protected final int SQUARE_SIZE = 31;
	private Rectangle2D.Double squares[];
	
	private Color shipColor;

	public void paintSquares(int squareNumbers) {

		squares = new Rectangle2D.Double[squareNumbers];

		for(int i = 0; i < squareNumbers; i++) {
			squares[i] = new Rectangle2D.Double();
			squares[i].height = SQUARE_SIZE;
			squares[i].width = SQUARE_SIZE;
			squares[i].x = i * SQUARE_SIZE;
			squares[i].y = 0;
		}

		setColor(getOriginalColor());
		
		addMouseListener(this);

		repaint();

	}

	@Override
	public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        for(int i = 0; i < squares.length; i++) {
        	g2d.setColor(getColor());
			g2d.fill(squares[i]);
			g2d.setColor(getColor().darker());
			g2d.draw(squares[i]);
		}
	}
	
	public void setColor(Color color) {
		shipColor = color;
	}
	
	public Color getColor() {
		return shipColor;
	}
	
	public Color getOriginalColor() {
		switch(squares.length) {
			case 1: return new Color( 106, 221, 221 ); //Cyan
			case 2: return new Color( 57, 170, 99 ); //Green
			case 4: return new Color( 34, 95, 167 ); //DarkBlue
			case 5: return new Color( 253, 64, 117 ); //Pink
		}
		return Color.BLACK;
	}

	public void mouseEntered(MouseEvent e) {
		System.out.println("Mouse IN");
		
		setColor(shipColor.darker());
		
		repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse CLICK");
	}

	@Override
	public void mouseExited(MouseEvent e) {
		System.out.println("Mouse OUT");
		
		setColor(getOriginalColor());
		
		repaint();

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}