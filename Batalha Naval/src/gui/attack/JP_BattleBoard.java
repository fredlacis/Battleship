package gui.attack;

import java.awt.Color;

import gui.board.JP_Board;
import gui.board.JP_Grid;
import main.K;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JP_BattleBoard extends JP_Board implements IObserver{
	
	private int player;
	private JP_Grid battleGrid;
	
	private int[][] shownCells;
	private int[][] hiddenCells;

	public JP_BattleBoard(int player) {
		RulesFacade.getRules().register(this);
		
		this.player = player;
		
		setLayout(null);
		setBounds(0, 0, BOARD_SIZE, BOARD_SIZE);
		setOpaque(false);
				
		battleGrid = new JP_Grid(player);
		
		addLabels();
		
		shownCells = K.createEmptyGrid();
		hiddenCells = K.createEmptyGrid();
		
		if(battleGrid!=null)
			add(battleGrid);
		else {
			System.out.println("Grid Nula");
		}
	}
	
	private void hideHiddenCells() {
		getShownCells();
		battleGrid.paintCells(shownCells);
	}
	
	private void showHiddenCells(){
		battleGrid.paintCells(hiddenCells);
	}
	
	private void getShownCells() {
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				if(hiddenCells[j][i] < 0) {
					shownCells[j][i] = hiddenCells[j][i];
				}
			}
		}
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
		else {
			hideHiddenCells();
		}
		
	}	
}
