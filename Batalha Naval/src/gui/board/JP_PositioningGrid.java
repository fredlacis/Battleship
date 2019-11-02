package gui.board;

import java.awt.Color;

import main.K;

@SuppressWarnings("serial")
public class JP_PositioningGrid extends JP_Grid{
	
	static JP_PositioningGrid g = null;
	
	private int[][] currentCellsToPaint;
	
	public static JP_PositioningGrid getGrid() {
		
        if(g==null)
            g=new JP_PositioningGrid();
        
        return g;
    }
	
	private JP_PositioningGrid() {
		super();
		
		definedCellsToPaint = new int[K.SQUARE_COUNT][K.SQUARE_COUNT];
		
		for(int i = 0; i < K.SQUARE_COUNT; i++)
			for(int j = 0; j < K.SQUARE_COUNT; j++)
				definedCellsToPaint[j][i] = 0;
	}
	
	public void paintTemporaryCells(boolean isValid, int cellsToPaint[][]) {
		
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
	
	public void unpaintTemporaryCells(int x, int y) {
		
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
	
	public void reset(){
		
		Cell cell;
		
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				cell = grid[j][i];
				cell.setShipColor(null);
				cell.setColor(cell.getOriginalColor());
				cell.repaint();
				
				definedCellsToPaint[j][i] = 0;
			}
		}
	}
	
	
	public int[][] getFinalGrid() {
		return definedCellsToPaint;
	}
}
