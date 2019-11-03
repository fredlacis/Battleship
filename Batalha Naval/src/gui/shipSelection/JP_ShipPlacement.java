package gui.shipSelection;

import javax.swing.JPanel;

import gui.board.JP_SelectionBoard;
import main.K;

@SuppressWarnings("serial")
public class JP_ShipPlacement extends JPanel{
	
	JP_SelectionBoard board = new JP_SelectionBoard();

	public JP_ShipPlacement() {

		setBounds(K.LARG_DEFAULT/3, 0, K.LARG_DEFAULT-(K.LARG_DEFAULT/3), K.ALT_DEFAULT - 150);
		setLayout(null);
		setOpaque(false);
		
		
		
		board.setBounds((this.getBounds().width - board.BOARD_SIZE)/2, (this.getBounds().height - board.BOARD_SIZE)/2, board.BOARD_SIZE, board.BOARD_SIZE);
		
		add(board);
		
	}

}