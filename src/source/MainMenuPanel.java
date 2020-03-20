package source;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {
	JLabel title, upTitle, upUpTitle;
	JLabel label;
	BufferedImage cube;
	BufferedImage img;
	static Clip clip;

	private float h = (float) 0.53358333;
	private float s = (float) 0.5697;
	private float b = (float) 0.9569;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Helper.backgroundColor);
		g.drawImage(img, 40, 320, null);
		g.drawImage(cube, 250, 30, 130, 130, null);
	}

	public MainMenuPanel() {
		initLayout();
	}

	private void initLayout(){
		setLayout(null);
		readImage();
		add(setLabel("Play",label));
		add(setLabel("How to Play",label));
		add(setLabel("Exit",label));
		setSound();
	}
	
	private void readImage(){
		img = Helper.getImage("../Sprites/totoro.png");
		cube = Helper.getImage("../Sprites/cube.png");
	}
	
	private JLabel setLabel(String text, JLabel label){
		final JLabel labelx = Helper.initLabel(text, label);
		labelx.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				labelx.setForeground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				labelx.setForeground(Color.YELLOW);
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				menu(text);
			}
		});
		return labelx;
	}
	
	private void menu(String text){
		if(text.equals("Play")){
			MainMenuFrame.window.remove(MainMenuPanel.this);
			Board board = new Board();
			MainMenuFrame.window.add(board);
			MainMenuFrame.window.setGlassPane(new GlassPane());
			MainMenuFrame.window.getGlassPane().setVisible(true);
			MainMenuFrame.window.addKeyListener(board);
			MainMenuFrame.window.setVisible(true);
		}else if((text.equals("How to Play"))){
			MainMenuFrame.window.remove(MainMenuPanel.this);
			MainMenuFrame.window.add(new HowToPlayPanel());
			MainMenuFrame.window.setVisible(true);
		}else if(text.equals("Exit")){
			System.exit(9);
		}
	}
	
	private void setSound(){
		try {
			clip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(Board.class.getResource("../Sprites/bg.wav"));
			clip.open(stream);
			clip.start();
		} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
	}
}
