package source;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class GlassPane extends JComponent {

	private BufferedImage border;
	private BufferedImage nexts;
	private BufferedImage hold;

	public GlassPane() {
		border = Helper.getImage("../Sprites/border.png");
		nexts = Helper.getImage("../Sprites/nextText.png");
		hold = Helper.getImage("../Sprites/holdText.png");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Helper.backgroundColor);
		g.fillRect(25, 50, 250, 100);
		g.drawImage(border.getScaledInstance(border.getWidth(), border.getHeight(), Image.SCALE_SMOOTH), 0, 117, null);
		g.drawImage(hold.getScaledInstance(hold.getWidth(), hold.getHeight(), Image.SCALE_SMOOTH), 540, 175, null);
		g.drawImage(nexts.getScaledInstance(nexts.getWidth(), nexts.getHeight(), Image.SCALE_SMOOTH), 350, 70, null);

	}
}
