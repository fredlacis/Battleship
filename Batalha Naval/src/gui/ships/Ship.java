package gui.ships;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import gui.board.JP_Grid;
import main.K;
import main.K.ORIENTATION;
import rules.designPatterns.Facade;

@SuppressWarnings("serial")
public abstract class Ship extends JComponent implements MouseListener {

	protected Rectangle2D.Double squares[];
	
	private Color shipColor;
	private Color shipBorderColor;
	
	public int shipSize;
	public ORIENTATION orientation = ORIENTATION.RIGHT;

	public void paintSquares(int squareNumbers) {

		squares = new Rectangle2D.Double[squareNumbers];

		for(int i = 0; i < squareNumbers; i++) {
			squares[i] = new Rectangle2D.Double();
			squares[i].height = K.SQUARE_SIZE;
			squares[i].width = K.SQUARE_SIZE;
			squares[i].x = i * K.SQUARE_SIZE;
			squares[i].y = 0;
		}
		
		shipSize = squares.length;
		
		setColor(getOriginalColor());
		setBorderColor(getOriginalColor().darker());
				
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
			g2d.setColor(getBorderColor());
			g2d.draw(squares[i]);
		}
	}
	
	public void setColor(Color color) {
		shipColor = color;
	}
	
	public void setBorderColor(Color color) {
		shipBorderColor = color;
	}
	
	public Color getColor() {
		return shipColor;
	}
	
	public Color getBorderColor() {
		return shipBorderColor;
	}

	
	public Color getOriginalColor() {
		switch(squares.length) {
			case 1: return new Color( 106, 221, 221 ); //Cyan
			case 2: return new Color( 57, 170, 99 ); //Green
			case 3: return new Color( 235, 235, 52 ); //Yellow
			case 4: return new Color( 34, 95, 167 ); //DarkBlue
			case 5: return new Color( 253, 64, 117 ); //Pink
		}
		return Color.BLACK;
	}
	
	public void rotate() {
		Ship selectedShip = Facade.getFacade().selectedShip();
				
		orientation = orientation.next();
		System.out.printf("Rotating ship %s to position: %s\n", selectedShip.getClass().getName(), orientation.name());
	}

	public void mouseEntered(MouseEvent e) {
		Ship selectedShip = Facade.getFacade().selectedShip();
		if(selectedShip != null && selectedShip == this) {
			return;
		}
		
		setColor(shipColor.darker());
		setColor(shipBorderColor.darker());
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		Ship selectedShip = Facade.getFacade().selectedShip();
		
		if(selectedShip != null) {
			selectedShip.setColor(selectedShip.getOriginalColor());
			selectedShip.setBorderColor(selectedShip.getOriginalColor().darker());
			selectedShip.repaint();
			selectedShip.orientation = ORIENTATION.TOP;
			Facade.getFacade().unsetSelectedShip();
		}
		
		Facade.getFacade().setSelectedShip(this);
		
		setColor(Color.GREEN);
		setBorderColor(Color.GREEN.darker());
		
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Ship selectedShip = Facade.getFacade().selectedShip();
		
		if(selectedShip != null && selectedShip == this) {
			return;
		}
		
		setColor(getOriginalColor());
		setBorderColor(shipColor.darker());
		
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