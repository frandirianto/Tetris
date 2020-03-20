package source;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HowToPlayPanel extends JPanel {
	BufferedImage up;
	BufferedImage down;
	BufferedImage right;
	BufferedImage left;
	BufferedImage shift;
	BufferedImage space;
	BufferedImage icon;
	BufferedImage icon1;
	int stat = 0;
	JLabel label;

	@Override
	public void paint(Graphics g) {
		setBackground(Helper.backgroundColor);
		super.paint(g);
		g.drawImage(icon, 310, 400, 500, 740, null);
		g.drawImage(icon1, -170, 400, 500, 740, null);
		if(stat == 1)
			g.drawImage(up, 150, 100, 60, 60, null);
		if (stat == 2)
			g.drawImage(down, 150, 160, 60, 60, null);
		if (stat == 3)
			g.drawImage(left, 150, 220, 60, 60, null);
		if (stat == 4) 
			g.drawImage(right, 150, 280, 60, 60, null);
		if (stat == 5) 
			g.drawImage(shift, 150, 400, 60, 60, null);
		if (stat == 6) 
			g.drawImage(space, 150, 340, 60, 60, null);
		if (stat == 7)
			g.drawImage(space, 150, 450, 60, 60, null);
	}
	
	private void readImage(){
		icon = Helper.getImage("../Sprites/icon.png");
		icon1 = Helper.getImage("../Sprites/icon1.png");
		shift = Helper.getImage("../Sprites/shift.png");
		space = Helper.getImage("../Sprites/space.png");
		up = Helper.getImage("../Sprites/up.png");
		down = Helper.getImage("../Sprites/down.png");
		left = Helper.getImage("../Sprites/left.png");
		right = Helper.getImage("../Sprites/right.png");
	}

	private void init(){
		setLayout(null);
		readImage();
	}
	public HowToPlayPanel() {
		init();
		add(setLabel("HOW TO PLAY", label));
		add(setLabel("Rotate Brick", label));
		add(setLabel("Soft Drop", label));
		add(setLabel("Move To Left", label));
		add(setLabel("Move To Right", label));
		add(setLabel("Speed Down", label));
		add(setLabel("Change Brick", label));
		add(setLabel("Go To Main Menu After KO!", label));
		add(setLabel("BACK TO MAIN MENU", label));
	}
	
	private JLabel setLabel(String text, JLabel label){
		final JLabel labelx = Helper.initLabel(text, label);	
		label.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(text.equals("HOW TO PLAY") || text.equals("BACK TO MAIN MENU") ) labelx.setForeground(Color.YELLOW);
				else labelx.setForeground(Color.BLUE);
				stat = 0;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				labelx.setForeground(Color.RED);
				changeStat(text);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(text.equals("BACK TO MAIN MENU")){
					MainMenuFrame.window.dispose();
					MainMenuPanel.clip.stop();
					new MainMenuFrame();
				}
			}
		});
		
		return labelx;
	}
	
	private void changeStat(String text){
		if(text.equals("HOW TO PLAY")) stat = 1;
		else if(text.equals("Rotate Brick")) stat = 1;
		else if(text.equals("Soft Drop")) stat = 2;
		else if(text.equals("Move To Left")) stat = 3;
		else if(text.equals("Move To Right")) stat = 4;
		else if(text.equals("Speed Down")) stat = 6;
		else if(text.equals("Change Brick")) stat = 5;
		else if(text.equals("Go To Main Menu After KO!")) stat = 7;
	}
}
