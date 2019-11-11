package gui.ships;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import main.K;
import main.K.ORIENTATION;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public abstract class Ship extends JComponent implements MouseListener {

	protected Rectangle2D.Double squares[];
	
	private Color shipColor;
	private Color shipBorderColor;
	
	public int shipSize;
	public ORIENTATION orientation = ORIENTATION.RIGHT;;
	
	private boolean available = true;

	public void paintSquares(int squareNumbers) {

		squares = new Rectangle2D.Double[squareNumbers];

		for(int i = 0; i < squareNumbers; i++) {
			squares[i] = new Rectangle2D.Double();
			squares[i].height = K.SQUARE_SIZE;
			squares[i].width = K.SQUARE_SIZE;
			squares[i].x = i * K.SQUARE_SIZE;
			squares[i].y = 0;
		}
				
		shipSize = squareNumbers;
		
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
		orientation = orientation.next();
	}
	
	public boolean getAvailability() {
		return available;
	}
	
	public void setAvailable() {
		available = true;
		
		setColor(getOriginalColor());
		setBorderColor(getOriginalColor().darker());
		repaint();
	}
	
	public void setUnavailable() {
		available = false;
		
		setColor(Color.GRAY);
		setBorderColor(Color.GRAY.darker());
		
		repaint();
	}
	
	public void unselectPreviousShip() {
		Ship selectedShip = RulesFacade.getRules().getSelectedShip();
		
		if(selectedShip == null || !selectedShip.available) {
			return;
		}
		
		selectedShip.setColor(selectedShip.getOriginalColor());
		selectedShip.setBorderColor(selectedShip.getOriginalColor().darker());
		selectedShip.repaint();
		selectedShip.orientation = ORIENTATION.RIGHT;
	}

	public void mouseEntered(MouseEvent e) {
		Ship selectedShip = RulesFacade.getRules().getSelectedShip();
		if(selectedShip == this || !available) {
			return;
		}
		
		setColor(shipColor.darker());
		setColor(shipBorderColor.darker());
		
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)) {
			Ship selectedShip = RulesFacade.getRules().getSelectedShip();
			if(selectedShip == null) return;
			
			selectedShip.rotate();
		}
		
		if(!available) {
			return;
		}
		
		unselectPreviousShip();
		
		RulesFacade.getRules().setSelectedShip(this);
		
		setColor(Color.GREEN);
		setBorderColor(Color.GREEN.darker());
		
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		Ship selectedShip = RulesFacade.getRules().getSelectedShip();
		
		if(selectedShip == this || !available) {
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