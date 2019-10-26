package gui.ships;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Destroyer extends Ship {
	
	@Override
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.setColor(Color.RED);
        
        int x1 = (int)Math.round(Math.random()*200);
        int y1 = (int)Math.round(Math.random()*200);
        int x2 = (int)Math.round(Math.random()*200);
        int y2 = (int)Math.round(Math.random()*200);
        
        g2d.drawLine(x1,y1,x2,y2);
    }
	
	public Destroyer() {
		setPreferredSize(new Dimension(200, 200));
	}
	
}
