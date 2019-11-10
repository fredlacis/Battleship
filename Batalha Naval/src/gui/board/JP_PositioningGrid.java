package gui.board;

import java.awt.Color;

import main.K;
import rules.designPatterns.IObservable;
import rules.designPatterns.IObserver;
import rules.designPatterns.RulesFacade;

@SuppressWarnings("serial")
public class JP_PositioningGrid extends JP_Grid implements IObserver{
	
	static JP_PositioningGrid g = null;
	
	private int[][] currentCellsToPaint;
	
	private int[][] cellsToPaint = K.createEmptyGrid();
	
	private boolean validation = false;
	
	public static JP_PositioningGrid getGrid() {
		
        if(g==null)
            g=new JP_PositioningGrid();
        
        return g;
    }
	
	private JP_PositioningGrid() {
		super();
		RulesFacade.getRules().register(this);
	}
	
	public void paintTemporaryCells(boolean validation, int[][] cellsToPaint) {
		
		Cell cell;
		
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
		return K.cloneGrid(definedCellsToPaint);
	}

	@Override
	public void notify(IObservable o) {
		
		System.out.println("Positioning Grid NOTIFICADO");
		
		Object lob[] = (Object []) o.get();
		
		cellsToPaint = (int[][]) lob[K.objectValues.CELLS_TO_PAINT.getValue()];
		validation = (boolean) lob[ K.objectValues.IS_VALID.getValue() ];
		
		//paintTemporaryCells();
		
	}
}
