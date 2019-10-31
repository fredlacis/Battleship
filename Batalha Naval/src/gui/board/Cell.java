package gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import main.K;

@SuppressWarnings("serial")
public class Cell extends JPanel implements MouseListener{
	
	private int x;
	private int y;
	
	private Rectangle2D.Double square = new Rectangle2D.Double(0, 0, K.SQUARE_SIZE, K.SQUARE_SIZE);
	
	private Color cellColor = new Color(150,150,150);
	private Color borderColor = new Color(250,250,250);
	
	public Cell(int x, int y) {
		
		this.x = x;
		this.y = y;
		
		setBounds(x, y, K.SQUARE_SIZE, K.SQUARE_SIZE);
		setOpaque(false);
		
		addMouseListener(this);
		
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
	
	public void setColor(Color color) {
		cellColor = color;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		
		System.out.printf("Mouse Entered Cell (%d, %d)\n", x/K.SQUARE_SIZE, y/K.SQUARE_SIZE);
		
		setColor(cellColor.darker());
		
		repaint();
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		System.out.printf("Mouse Exited Cell (%d, %d)\n", x/K.SQUARE_SIZE, y/K.SQUARE_SIZE);
		
		setColor(cellColor.brighter());
		
		repaint();
		
	}

}
