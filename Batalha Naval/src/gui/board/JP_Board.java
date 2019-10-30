package gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JP_Board extends JPanel{
	
	final float STROKE_WIDTH = 1.0f;
	final int LINES_COUNT = 14;
	final float LINES_SPACE = 30.6666666f;
	final int LINES_SPACE_INT = 31;
	final int LABELS_COUNT = LINES_COUNT + 1;
	final int LABELS_SIZE = 20;
	final int JPANEL_BORDER = 25;
	
	final Color mainColor = new Color(120,120,120);
	
	private Rectangle2D.Double border;
	
	private Line2D.Double linesHorizontal[] = new Line2D.Double[LINES_COUNT];
	private Line2D.Double linesVertical[] = new Line2D.Double[LINES_COUNT];
	
	private JPanel numbersContainers[] = new JPanel[LABELS_COUNT];
	
	private JPanel charactersContainers[] = new JPanel[LABELS_COUNT];
	
	
	public JP_Board() {
		
		setLayout(null);
		setBounds(0,0,500,500);
		setOpaque(false);
		
		addLabels();
		addGrid();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(mainColor);
		
		g2d.setStroke(new BasicStroke(STROKE_WIDTH ,BasicStroke.CAP_BUTT ,BasicStroke.JOIN_MITER ,10.0f));
		
		g2d.draw(border);
		
	}
	
	public void addLabels() {
		
		int labelSpace = JPANEL_BORDER;
		
		for(int i = 0; i < LABELS_COUNT; i++) {
			
			if(i > 0) labelSpace += (int)LINES_SPACE+1;
			
			numbersContainers[i] = new JPanel();
			numbersContainers[i].setLayout(new GridBagLayout());
			numbersContainers[i].setBounds(labelSpace, JPANEL_BORDER - LABELS_SIZE, (int)LINES_SPACE, LABELS_SIZE);
			numbersContainers[i].setOpaque(false);
			//numbersContainers[i].setBackground(new Color((i+1)*10,(i+1)*10,(i+1)*10));
			
			JLabel number = new JLabel(Integer.toString(i+1));
			number.setFont(new Font("SansSerif", Font.PLAIN, 13));
			number.setForeground(mainColor);
			numbersContainers[i].add(number);
			
			charactersContainers[i] = new JPanel();
			charactersContainers[i].setLayout(new GridBagLayout());
			charactersContainers[i].setBounds(JPANEL_BORDER - LABELS_SIZE, labelSpace, LABELS_SIZE, (int)LINES_SPACE);
			charactersContainers[i].setOpaque(false);
			char ch = (char)(i+65);
			JLabel letter = new JLabel(Character.toString(ch));
			letter.setFont(new Font("SansSerif", Font.PLAIN, 13));
			letter.setForeground(mainColor);
			charactersContainers[i].add(letter);
			
			add(numbersContainers[i]);
			add(charactersContainers[i]);
			
		}
		
	}
	
	public void addGrid() {
		
		JPanel grid = new JPanel();
		grid.setBounds(JPANEL_BORDER, JPANEL_BORDER, 460, 460);
		
		
	}
	
	public void addCells() {
		
	}
	
	public double getCellSize() {
		return LINES_SPACE;
	}
	
	public double getBoardBorder() {
		return JPANEL_BORDER;
	}

}
