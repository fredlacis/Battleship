package gui;

import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class JP_MouseBoard extends JPanel implements MouseListener{
	
	private JP_Board board = new JP_Board();
	
	public JP_MouseBoard() {
		setLayout(null);
		setBounds(0, 50, 500, 500);
		addMouseListener(this);
		
		add(board);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.printf("%d - %d\n", (int)((e.getX()-25)/30.6), (int)((e.getY()-25)/30.6));
		//precisa melhorar
		//double x = e.getX() - board.get, y = e.getY() - cellSize;
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
