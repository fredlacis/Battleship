package gui.attack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import gui.JP_Title;
import main.K;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JF_Attack extends JFrame implements IObserver{
	
	private String currentPlayerName;
	
	JP_Title titlePanel = new JP_Title("");
	
	public JF_Attack() {
		RulesFacade.getRules().register(this);
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int sl = screenSize.width;
		int sa = screenSize.height;
		int x = sl/2-K.LARG_DEFAULT/2;
		int y = sa/2-K.ALT_DEFAULT/2;
		setBounds(x,y,K.LARG_DEFAULT, K.ALT_DEFAULT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		getContentPane().setBackground(new Color(250, 250, 250));
		
		JP_BattleBoard board1 = new JP_BattleBoard(1);
		board1.setBounds(8, 60, board1.BOARD_SIZE, board1.BOARD_SIZE);
		
		JP_BattleBoard board2 = new JP_BattleBoard(2);
		board2.setBounds(8 + board2.BOARD_SIZE + 8, 60, board2.BOARD_SIZE, board2.BOARD_SIZE);
		
		getContentPane().add(JP_AttackUtilities.getAttackUtilites());
		getContentPane().add(titlePanel);
		getContentPane().add(board1);
		getContentPane().add(board2);
		
		RulesFacade.getRules().startGame();
	}

	@Override
	public void notify(IObservable o) {
		// TODO Auto-generated method stub
		Object lob[] = (Object []) o.get();
		
		int currentPlayer = (int) lob[K.objectValues.CURRENT_PLAYER.getValue()];
//		currentPlayerName = RulesFacade.getRules().getPlayerName(currentPlayer);
		if(currentPlayer == 1)
			currentPlayerName = (String) lob[ K.objectValues.PLAYER_1_NAME.getValue() ];
		else
			currentPlayerName = (String) lob[ K.objectValues.PLAYER_2_NAME.getValue() ];
		
		titlePanel.setText("ATTACKING PLAYER - " + currentPlayerName);
	}
	
}
