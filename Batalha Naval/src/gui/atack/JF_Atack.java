package gui.atack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import gui.JP_Title;
import gui.JP_Utilities;
import gui.board.JP_BattleBoard;
import main.K;

@SuppressWarnings("serial")
public class JF_Atack extends JFrame {

	JP_Title titlePanel = new JP_Title("Atack");
	
	public JF_Atack() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl/2-K.LARG_DEFAULT/2;
		int y = sa/2-K.ALT_DEFAULT/2;
		setBounds(x,y,K.LARG_DEFAULT,K.ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		getContentPane().setBackground(new Color(250, 250, 250));
		
		JP_BattleBoard board1 = new JP_BattleBoard(1);
		board1.setBounds(8, 100, board1.BOARD_SIZE, board1.BOARD_SIZE);
		
		JP_BattleBoard board2 = new JP_BattleBoard(2);
		board2.setBounds(8 + board2.BOARD_SIZE + 8, 100, board2.BOARD_SIZE, board2.BOARD_SIZE);
		
		getContentPane().add(titlePanel);
		getContentPane().add(board1);
		getContentPane().add(board2);
		getContentPane().add(new JP_Utilities());
		
	}
	
}
