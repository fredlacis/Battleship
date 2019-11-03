package gui.board;

import java.awt.Color;

import javax.swing.JPanel;

import main.K;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JP_Grid extends JPanel{
	
	protected Cell grid[][] = new Cell[K.SQUARE_COUNT][K.SQUARE_COUNT];
	
	protected int[][] definedCellsToPaint;
			
	public JP_Grid() {
		
		setLayout(null);
		setOpaque(false);
		setBounds(K.JPANEL_BORDER, K.JPANEL_BORDER, K.SQUARE_SIZE * K.SQUARE_COUNT, K.SQUARE_SIZE * K.SQUARE_COUNT);
		
		addCells();
		
	}
	
	private void addCells() {
		
		for(int i = 0; i < K.SQUARE_COUNT; i++) {
			for(int j = 0; j < K.SQUARE_COUNT; j++) {
				
				grid[i][j] = new Cell(i*K.SQUARE_SIZE, j*K.SQUARE_SIZE);
				add(grid[i][j]);
				
			}
		}
	}
	
	public void paintCells(int cellsToPaint[][]) {
		
		Color shipColor = RulesFacade.getRules().selectedShip().getOriginalColor();
		
		Cell cell;
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				if(cellsToPaint[j][i] != 0) {
					
					definedCellsToPaint[j][i] = cellsToPaint[j][i];
					
					cell = grid[j][i];
					cell.setShipColor(shipColor);
					cell.repaint();
				}
			}
		}
	}
	
}














