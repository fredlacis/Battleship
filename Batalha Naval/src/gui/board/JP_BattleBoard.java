package gui.board;

import main.K;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;

@SuppressWarnings("serial")
public class JP_BattleBoard extends JP_Board implements IObserver{
	
	private int player;
	private JP_Grid battleGrid;
	
	private int[][] hiddenCells;

	public JP_BattleBoard(int player) {
		
		this.player = player;
		
		setLayout(null);
		setBounds(0, 0, BOARD_SIZE, BOARD_SIZE);
		setOpaque(false);
				
		battleGrid = new JP_Grid();
		
		addLabels();
		
		if(battleGrid!=null)
			add(battleGrid);
		else {
			System.out.println("Grid Nula");
		}
	}
	
	private void showHiddenCells(){
		battleGrid.paintCells(hiddenCells);
	}
	@Override
	public void notify(IObservable o) {
		Object lob[] = (Object []) o.get();
		
		int currentPlayer = (int) lob[K.objectValues.CURRENT_PLAYER.getValue()];
		
		if(player == 1) {
			hiddenCells = (int[][]) lob[K.objectValues.BOARD_1.getValue()];
		}
		else {
			hiddenCells = (int[][]) lob[K.objectValues.BOARD_2.getValue()];
		}
		
		if(player == currentPlayer) {
			showHiddenCells();
		}
		
	}	
}
