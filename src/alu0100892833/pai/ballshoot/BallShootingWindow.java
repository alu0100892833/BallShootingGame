package alu0100892833.pai.ballshoot;

import java.awt.Dimension;
import java.awt.Toolkit;

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
		add(mainPanel);
		setSize(size);
		System.out.println();
	}

}
