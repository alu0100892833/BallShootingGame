package alu0100892833.pai.ballshoot.view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


/**
 * A window for the Ball Shooting Game. Just instantiates a BallShootingPanel in an appropriate size for the screen size and resolution.
 * @author Óscar Darias Plasencia
 * @since 3-5-2017
 */
public class BallShootingWindow extends JFrame {
	private static final long serialVersionUID = 5019270042630046629L;
	private static final double HEIGHT_PROPORTION = 1.2;
	private static final double WIDTH_PROPORTION = 1.2;
	private static final int BALL_RADIUS = 30;
	private static final Dimension INFO_ICON_DIMENSIONS = new Dimension(20, 20);
	private static final String SUCESS_CLIP = "sound/success.wav";
	private static final String FAILURE_CLIP = "sound/failure.wav";
	
	private BallShootingPanel mainPanel;
	
	/**
	 * Default constructor.
	 */
	public BallShootingWindow() {
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (fullScreen.getWidth() / WIDTH_PROPORTION), (int) (fullScreen.getHeight() / HEIGHT_PROPORTION));
		mainPanel = new BallShootingPanel(size, BALL_RADIUS);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BufferedImage info = null;
		Image picture = null;
		try {
			info = ImageIO.read(new File("img/info.png"));
			picture = info.getScaledInstance(INFO_ICON_DIMENSIONS.width, INFO_ICON_DIMENSIONS.height, 
					Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		mainPanel.loadImageForInfo(picture);
		mainPanel.loadSounds(SUCESS_CLIP, FAILURE_CLIP);
		
		add(mainPanel);
		setSize(size);
		System.out.println();
	}

}
