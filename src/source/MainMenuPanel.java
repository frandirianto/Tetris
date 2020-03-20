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
	
	private JLabel label;
	private BufferedImage cube;
	private BufferedImage img;
	public static Clip clip;

	public MainMenuPanel() {
		initLayout();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		setBackground(Helper.backgroundColor);
		g.drawImage(img, 40, 320, null);
		g.drawImage(cube, 250, 30, 130, 130, null);
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
	
	public JLabel setLabel(String text, JLabel label){
		final JLabel labelx = Label.init(text, label, "Menu");
		labelx.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				menu(text);
			}
		});
		return labelx;
	}
	
	public void menu(String text){
		if(text.equals("Play")){
			play();
		}else if((text.equals("How to Play"))){
			howToPlay();
		}else if(text.equals("Exit")){
			System.exit(9);
		}
	}
	
	private void play(){
		MainMenuFrame.window.remove(MainMenuPanel.this);
		Board board = new Board();
		MainMenuFrame.window.add(board);
		MainMenuFrame.window.setGlassPane(new GlassPane());
		MainMenuFrame.window.getGlassPane().setVisible(true);
		MainMenuFrame.window.addKeyListener(board);
		MainMenuFrame.window.setVisible(true);
	}
	
	private void howToPlay(){
		MainMenuFrame.window.remove(MainMenuPanel.this);
		MainMenuFrame.window.add(new HowToPlayPanel());
		MainMenuFrame.window.setVisible(true);
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
