package gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import gui.ships.Ship;
import main.K;
import rules.designPatterns.Facade;

@SuppressWarnings("serial")
public class Cell extends JPanel implements MouseListener{
	
	private int x;
	private int y;
	
	private Rectangle2D.Double square;
	
	private Color cellColor;
	private Color borderColor;
	
	public Cell(int x, int y) {
		
		this.x = x;
		this.y = y;
		
		setBounds(x, y, K.SQUARE_SIZE, K.SQUARE_SIZE);
		setOpaque(false);
		
		cellColor = new Color(150,150,150);
		borderColor = new Color(250,250,250);
		square = new Rectangle2D.Double(0, 0, K.SQUARE_SIZE, K.SQUARE_SIZE);
		
		addMouseListener(this);
		
	}
	
	public Cell cloneCell() {
		Cell clonedCell = new Cell(x, y);
		
		clonedCell.cellColor = cellColor;
		clonedCell.borderColor = borderColor;
		clonedCell.square = square;
				
		return clonedCell;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setStroke(new BasicStroke(K.STROKE_WIDTH));
		
		g2d.setColor( cellColor );
		g2d.fill(square);
		
		g2d.setColor( borderColor );
		g2d.draw(square);
		
		
	}
	
	public Color getOriginalColor() {
		return new Color(150,150,150);
	}
	
	public void setColor(Color color) {
		cellColor = color;
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if(!SwingUtilities.isRightMouseButton(e)) {
			Facade.getFacade().positionShip(x/K.SQUARE_SIZE, y/K.SQUARE_SIZE);
			return;
		}
		
		Ship selectedShip = Facade.getFacade().selectedShip();
		if(selectedShip == null) return;
		
		selectedShip.rotate();
		Facade.getFacade().checkPos(x/K.SQUARE_SIZE, y/K.SQUARE_SIZE);
		
		JP_Grid.getGrid().repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		//System.out.printf("Mouse Entered Cell (%d, %d)\n", x/K.SQUARE_SIZE, y/K.SQUARE_SIZE);
		
		Object[] pair = new Object[2];
		boolean isValid;
		int cellsToPaint[][];
		
		pair = Facade.getFacade().checkPos(x/K.SQUARE_SIZE, y/K.SQUARE_SIZE);
		
		if(pair == null) {
			setColor(cellColor.darker());
			repaint();
			return;
		}
		
		isValid = (boolean)pair[0];
		cellsToPaint = (int[][])pair[1];
		
		JP_Grid.getGrid().paintCells(x/K.SQUARE_SIZE, y/K.SQUARE_SIZE, isValid, cellsToPaint);
		
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		//System.out.printf("Mouse Exited Cell (%d, %d)\n", x/K.SQUARE_SIZE, y/K.SQUARE_SIZE);
		
		JP_Grid.getGrid().unpaintCells(x, y);
		
		setColor(getOriginalColor());
		
		repaint();
		
	}

}
