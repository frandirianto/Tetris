package Label;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import Frame.MainMenuFrame;
import Panel.HowToPlayPanel;
import Panel.MainMenuPanel;

public class Label  {

	public static JLabel init(String text, JLabel label, String context){
		label = new JLabel(text, label.CENTER);
		label = setLabelBounds(text, label);
		label = setLabelFonts(context, label);
		label = setLabelColor(text, label);
		label = setActionListener(text, label, context);
		return label;
	}
	
	public static JLabel setLabelFonts(String context, JLabel label){
		if(context.equals("How to Play"))
		label.setFont(new Font("Orange Kid", Font.BOLD, 20));
		else if(context.equals("Menu")) label.setFont(new Font("Orange Kid", Font.BOLD, 36));
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
	
	public static JLabel setActionListener(String text, JLabel label, String context){
		final JLabel labelx = label;
		labelx.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(context.equals("How to Play")){
					if(text.equals("HOW TO PLAY") || text.equals("BACK TO MAIN MENU") ) labelx.setForeground(Color.YELLOW);
					else labelx.setForeground(Color.BLUE);
					HowToPlayPanel.stat = 0;
				}else if(context.equals("Menu")){
					labelx.setForeground(Color.BLACK);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				if(context.equals("How to Play")){
					labelx.setForeground(Color.RED);
					HowToPlayPanel.changeStat(text);
				}else if(context.equals("Menu")){
					labelx.setForeground(Color.YELLOW);
				}
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(context.equals("How to Play")){
					if(text.equals("BACK TO MAIN MENU")){
						MainMenuFrame.window.dispose();
						MainMenuPanel.clip.stop();
						new MainMenuFrame();
					}
				}
			}
		});
		
		return labelx;
	}
}
