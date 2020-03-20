package Helper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Item.Board;

public class Helper {

	public static Scanner scan = new Scanner(System.in);
	public static Random rand = new Random();
	public static Color backgroundColor = Color.getHSBColor(0.53358333f, 0.5697f, 0.9569f);
	public static Color color1 = Color.getHSBColor(0.472f, 0.5f, 0.76f);

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
}
