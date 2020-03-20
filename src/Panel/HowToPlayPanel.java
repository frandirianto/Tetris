package Panel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Helper.Helper;
import Label.Label;

public class HowToPlayPanel extends JPanel {

	ArrayList<BufferedImage> images;
	public static int stat = 0;
	JLabel label;
	
	public HowToPlayPanel() {
		initLayout();
	}
	
	@Override
	public void paint(Graphics g) {
		setBackground(Helper.backgroundColor);
		super.paint(g);
		g.drawImage(images.get(0), 310, 400, 500, 740, null);
		g.drawImage(images.get(1), -170, 400, 500, 740, null);
		drawImage(g);
	}
	
	private Graphics drawImage(Graphics g){
		if(stat == 1)
			g.drawImage(images.get(stat+1), 150, 100, 60, 60, null);
		if (stat == 2)
			g.drawImage(images.get(stat+1), 150, 160, 60, 60, null);
		if (stat == 3)
			g.drawImage(images.get(stat+1), 150, 220, 60, 60, null);
		if (stat == 4) 
			g.drawImage(images.get(stat+1), 150, 280, 60, 60, null);
		if (stat == 5) 
			g.drawImage(images.get(stat+1), 150, 400, 60, 60, null);
		if (stat == 6) 
			g.drawImage(images.get(stat+1), 150, 340, 60, 60, null);
		if (stat == 7)
			g.drawImage(images.get(stat), 150, 450, 60, 60, null);
		return g;
	}
	
	private void readImage(){
		images = new ArrayList<>();
		images.add(Helper.getImage("../Sprites/icon.png"));
		images.add(Helper.getImage("../Sprites/icon1.png"));
		images.add(Helper.getImage("../Sprites/up.png"));
		images.add(Helper.getImage("../Sprites/down.png"));
		images.add(Helper.getImage("../Sprites/left.png"));
		images.add(Helper.getImage("../Sprites/right.png"));
		images.add(Helper.getImage("../Sprites/shift.png"));
		images.add(Helper.getImage("../Sprites/space.png"));
	}

	private void initLayout(){
		setLayout(null);
		readImage();
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
		return Label.init(text, label, "How to Play");
	}
	
	public static void changeStat(String text){
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
