package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JP_Board extends JPanel{
	
	final float STROKE_WIDTH = 1.0f;
	final int LINES_COUNT = 14;
	final float LINES_SPACE = 30.6666666f;
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
		setBackground(new Color(200,200,200));
		setOpaque(true);
		
		border  = new Rectangle2D.Double(JPANEL_BORDER,JPANEL_BORDER,460,460);
		
		int labelSpace = JPANEL_BORDER;
		
		for(int i = 0; i < LABELS_COUNT; i++) {
			
			if(i < LINES_COUNT) {
				float lineSpace = JPANEL_BORDER + ( (i+1) * LINES_SPACE);
				linesHorizontal[i] = new Line2D.Double(lineSpace, JPANEL_BORDER, lineSpace, 460 + JPANEL_BORDER);
				linesVertical[i] = new Line2D.Double(JPANEL_BORDER, lineSpace, 460 + JPANEL_BORDER, lineSpace);
			}
			
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
	
	public void paintComponent(Graphics g){
		System.out.println("BOARD_PaintComponent");
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(mainColor);
		
		g2d.setStroke(new BasicStroke(STROKE_WIDTH ,BasicStroke.CAP_BUTT ,BasicStroke.JOIN_MITER ,10.0f));
		
		g2d.draw(border);
		
		
		for(int i = 0; i < LINES_COUNT; i++) {
			g2d.draw(linesHorizontal[i]);
			g2d.draw(linesVertical[i]);
		}
		
	}

}
