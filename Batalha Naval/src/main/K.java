package main;

import gui.board.Cell;

public class K {
	
	public static final int LARG_DEFAULT = 1024;
	public static final int ALT_DEFAULT = 768;

	public static final int SQUARE_SIZE = 32;
	public static final int SQUARE_COUNT = 15;
	
	public static final int LINE_COUNT = SQUARE_COUNT + 1;
	public static final float STROKE_WIDTH = 1.0f;
	
	public static final int LABELS_SIZE = 20;
	
	public static final int JPANEL_BORDER = 20;
	
	public static enum ORIENTATION { 
	    TOP, RIGHT, DOWN, LEFT;
	    private static ORIENTATION[] vals = values();
	    public ORIENTATION next()
	    {
	        return vals[(this.ordinal()+1) % vals.length];
	    }
	}
	
	public static int[][] cloneGrid(int grid[][]){
		int newGrid[][] = new int[K.SQUARE_COUNT][K.SQUARE_COUNT];
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				newGrid[j][i] = grid[j][i];
			}
		}
		return newGrid;
	}
	
	public static Cell[][] cloneCellGrid(Cell grid[][]){
		Cell newGrid[][] = new Cell[K.SQUARE_COUNT][K.SQUARE_COUNT];
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				newGrid[j][i] = grid[j][i].cloneCell();
			}
		}
		return newGrid;
	}
	
	public static void printGrid(int grid[][]) {
		for(int i = 0; i < K.SQUARE_COUNT; i++)
		{
			for(int j = 0; j < K.SQUARE_COUNT; j++)
			{
				System.out.printf("%2d ", grid[j][i]);
			}
			System.out.println();
		}
	}
}
