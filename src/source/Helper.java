package source;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Helper {

	public static Scanner scan = new Scanner(System.in);
	public static Random rand = new Random();
	public static Color backgroundColor = Color.getHSBColor(0.53358333f, 0.5697f, 0.9569f);

	public static int randomNum(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
	
	public static BufferedImage getImage(String url){
		BufferedImage img = null;
		try {
			img = ImageIO.read(Board.class.getResource(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	public static JLabel initLabel(String text, JLabel label){
		label = new JLabel(text, label.CENTER);
		label = Helper.setLabelBounds(text, label);
		label.setFont(new Font("Orange Kid", Font.BOLD, 36));
		label = Helper.setLabelColor(text, label);
		return label;
	}
	
	public static JLabel setLabelBounds(String text, JLabel label){
		JLabel labelx = label;

		if(text.equals("HOW TO PLAY")) labelx.setBounds(100, 20, 500, 50);
		else if(text.equals("Rotate Brick")) labelx.setBounds(100, 100, 500, 50);
		else if(text.equals("Soft Drop")) labelx.setBounds(100, 160, 500, 50);
		else if(text.equals("Move To Left")) labelx.setBounds(100, 220, 500, 50);
		else if(text.equals("Move To Right")) labelx.setBounds(100, 280, 500, 50);
		else if(text.equals("Speed Down")) labelx.setBounds(100, 340, 500, 50);
		else if(text.equals("Change Brick")) labelx.setBounds(100, 400, 500, 50);
		else if(text.equals("Go To Main Menu After KO!")) labelx.setBounds(100, 450, 500, 50);
		else if(text.equals("BACK TO MAIN MENU")) labelx.setBounds(100, 570, 500, 50);
		else if(text.equals("Play")) labelx.setBounds(60, 170, 500, 50);
		else if(text.equals("How to Play")) labelx.setBounds(60, 213, 500, 50);
		else if(text.equals("Exit")) labelx.setBounds(60, 250, 500, 50);
		
		return labelx;
	}
	
	public static JLabel setLabelColor(String text, JLabel label){
		JLabel labelx = label;

		if(text.equals("Play") || text.equals("How to Play") || text.equals("Exit")) labelx.setForeground(Color.BLACK);
		else if(text.equals("HOW TO PLAY"))  labelx.setForeground(Color.YELLOW);
		else labelx.setForeground(Color.BLUE);
		
		return labelx;
	}
}
