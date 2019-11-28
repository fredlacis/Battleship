package gui.shipPositioning;

import java.awt.Color;

import gui.board.Cell;
import gui.board.JP_Grid;
import main.K;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JP_PositioningGrid extends JP_Grid implements IObserver{
	
	static JP_PositioningGrid positioningGrid = null;
		
	private int[][] cellsToPaint;
	private boolean validation = false;
	
	public static JP_PositioningGrid getGrid() {
        if(positioningGrid==null)
            positioningGrid=new JP_PositioningGrid();
        
        return positioningGrid;
    }
	
	public void selfDestroy() {
		positioningGrid = null;
	}
	
	private JP_PositioningGrid() {
		super(0);
		RulesFacade.getRules().register(this);
		cellsToPaint = K.createEmptyGrid();
	}
	
	public void paintTemporaryCells() {
		
		Cell cell;
		
		if(cellsToPaint == null) return;
				
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				if(cellsToPaint[j][i] != 0) {
					cell = grid[j][i];
					if(validation) {
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
		
	}
	public void unpaintTemporaryCells() {
		
		Cell cell;
		
		if(cellsToPaint == null) return;
		
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				if(cellsToPaint[j][i] != 0) {
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
		return K.cloneGrid(definedCellsToPaint);
	}

	@Override
	public void notify(IObservable o) {
		
		unpaintTemporaryCells();
		
		Object lob[] = (Object []) o.get();
		
		cellsToPaint = (int[][]) lob[K.objectValues.CELLS_TO_PAINT.getValue()];
		validation = (boolean) lob[K.objectValues.IS_VALID.getValue()];
			
		paintTemporaryCells();
		
	}
}
