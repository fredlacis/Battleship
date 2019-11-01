package gui.board;

import java.awt.Color;

import javax.swing.JPanel;

import main.K;
import rules.designPatterns.Facade;

@SuppressWarnings("serial")

public class JP_Grid extends JPanel{
	
	private Cell grid[][] = new Cell[K.SQUARE_COUNT][K.SQUARE_COUNT];
	
	private int[][] currentCellsToPaint;
	
	static JP_Grid g = null;
	
	public static JP_Grid getGrid() {
        if(g==null)
            g=new JP_Grid();
        
        return g;    
    }
	
	private JP_Grid() {
		
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
	
	public void paintCells(int x, int y, boolean isValid, int cellsToPaint[][]) {
		
		Cell cell;
		
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				if(cellsToPaint[j][i] != 0) {
					cell = grid[j][i];
					if(isValid) {
						cell.setColor(Color.GREEN);
						cell.repaint();
					}
					else {
						cell.setColor(Color.RED);
						cell.repaint();
					}
				}
			}
		}
		
		currentCellsToPaint = cellsToPaint;
	}
	
	public void unpaintCells(int x, int y) {
		
		Cell cell;
		if(currentCellsToPaint == null) return;
		
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				if(currentCellsToPaint[j][i] != 0) {
					cell = grid[j][i];
					cell.setColor(cell.getOriginalColor());
					cell.repaint();
				}
			}
		}
	}
	
	public void repaint() {
		
		Cell cell;
		if(currentCellsToPaint == null) return;
		
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				if(currentCellsToPaint[j][i] != 0) {
					cell = grid[j][i];
					cell.setColor(cell.getOriginalColor());
					cell.repaint();
				}
			}
		}
	}
	
}














