package gui.board;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import main.K;

public class Cell extends JComponent implements MouseListener{
	
	private int x;
	private int y;
	
	public Cell(int x, int y) {
		
		setBounds(x, y, K.SQUARE_SIZE, K.SQUARE_SIZE);
		setOpaque(false);
		
	}
	
	public void paintComponent(Graphics g){
		System.out.println("CELL_PaintComponent");
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
