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
		setBackground(Color.getHSBColor(h, s, b));
		super.paint(g);
		g.drawImage(img, 40, 320, null);
		g.drawImage(cube, 250, 30, 130, 130, null);
	}

	public MainMenuPanel() {
		setLayout(null);
		init();
	}

	private void init(){
		readImage();
		add(setLabel("Play",label));
		add(setLabel("How to Play",label));
		add(setLabel("Exit",label));
		setSound();
	}
	
	private void readImage(){
		try {
			img = ImageIO.read(Board.class.getResource("../Sprites/totoro.png"));
			cube = ImageIO.read(Board.class.getResource("../Sprites/cube.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JLabel setLabel(String text, JLabel label){
		label = new JLabel(text, JLabel.CENTER);
		if(text.equals("Play")) label.setBounds(60, 170, 500, 50);
		else if(text.equals("How to Play")) label.setBounds(60, 213, 500, 50);
		else if(text.equals("Exit")) label.setBounds(60, 250, 500, 50);
		
		label.setFont(new Font("Orange Kid", Font.BOLD, 36));
		label.setForeground(Color.BLACK);
		final JLabel labelx = label;
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
				if(text.equals("Play")){
					MainMenuFrame.window.remove(MainMenuPanel.this);
					new WindowFrame();
				}else if((text.equals("How to Play"))){
					MainMenuFrame.window.remove(MainMenuPanel.this);
					new HowToPlayFrame();
				}else if(text.equals("Exit")){
					System.exit(9);
				}
			}
		});
		return labelx;
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
