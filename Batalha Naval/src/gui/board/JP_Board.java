package gui.board;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.K;

@SuppressWarnings("serial")
public class JP_Board extends JPanel{

	public final int BOARD_SIZE = 500;
	
	final Color mainColor = new Color(120,120,120);
	
	private JPanel numbersContainers[] = new JPanel[K.SQUARE_COUNT];
	
	private JPanel charactersContainers[] = new JPanel[K.SQUARE_COUNT];
	
	public JP_Board() {
		
		setLayout(null);
		setBounds(0,0,BOARD_SIZE,BOARD_SIZE);
		setOpaque(false);
		
		addLabels();		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setPaint(mainColor);
		
		g2d.setStroke(new BasicStroke(K.STROKE_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER ,10.0f));
		
	}
	
	public void addLabels() {
		
		int labelSpace = K.JPANEL_BORDER;
		
		for(int i = 0; i < K.SQUARE_COUNT; i++) {
			
			if(i > 0) labelSpace += K.SQUARE_SIZE;
			
			numbersContainers[i] = new JPanel();
			numbersContainers[i].setLayout(new GridBagLayout());
			numbersContainers[i].setBounds(labelSpace, K.JPANEL_BORDER - K.LABELS_SIZE, K.SQUARE_SIZE, K.LABELS_SIZE);
			numbersContainers[i].setOpaque(false);
			//numbersContainers[i].setBackground(new Color((i+1)*10,(i+1)*10,(i+1)*10));
			
			JLabel number = new JLabel(Integer.toString(i+1));
			number.setFont(new Font("SansSerif", Font.PLAIN, 13));
			number.setForeground(mainColor);
			numbersContainers[i].add(number);
			
			charactersContainers[i] = new JPanel();
			charactersContainers[i].setLayout(new GridBagLayout());
			charactersContainers[i].setBounds(K.JPANEL_BORDER - K.LABELS_SIZE, labelSpace, K.LABELS_SIZE, K.SQUARE_SIZE);
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
	
}
