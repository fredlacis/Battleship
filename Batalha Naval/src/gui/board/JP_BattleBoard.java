package gui.board;

import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JP_BattleBoard extends JP_Board{
	
	private JP_Grid battleGrid = new JP_Grid();

	public JP_BattleBoard(int jogador) {
		
		setLayout(null);
		setBounds(0,0,BOARD_SIZE,BOARD_SIZE);
		setOpaque(false);
		
		battleGrid.paintCells( RulesFacade.getRules().getTabuleiro(jogador) );
		
		addLabels();
		
		if(battleGrid!=null)
			add(battleGrid);
		else {
			System.out.println("Grid Nula");
		}
		
	}
	
}
