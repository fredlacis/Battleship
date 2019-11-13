package gui.shipPositioning;

import gui.board.JP_Board;

@SuppressWarnings("serial")
public class JP_SelectionBoard extends JP_Board{
	
	public JP_SelectionBoard() {
		super();
		addGrid();
	}
	
	public void addGrid() {
		add(JP_PositioningGrid.getGrid());
	}

}
