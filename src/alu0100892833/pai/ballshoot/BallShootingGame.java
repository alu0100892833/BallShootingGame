package alu0100892833.pai.ballshoot;

//import java.applet.AudioClip;
import java.awt.Dimension;
//import java.awt.Image;
import java.awt.Toolkit;
//import java.net.URL;

//import javax.swing.ImageIcon;
import javax.swing.JApplet;

import alu0100892833.pai.ballshoot.view.BallShootingPanel;
import alu0100892833.pai.ballshoot.view.BallShootingWindow;

public class BallShootingGame extends JApplet {
	private static final long serialVersionUID = 8526336847971124887L;
	private static final double HEIGHT_PROPORTION = 1.2;
	private static final double WIDTH_PROPORTION = 1.2;
	private static final int BALL_RADIUS = 30;
	//private static final Dimension INFO_ICON_DIMENSIONS = new Dimension(20, 20);
	
	@Override
	public void init() {
		Dimension fullScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension size = new Dimension((int) (fullScreen.getWidth() / WIDTH_PROPORTION), (int) (fullScreen.getHeight() / HEIGHT_PROPORTION));
		BallShootingPanel game = new BallShootingPanel(size, BALL_RADIUS);
		
		//URL urlForInfo = getClass().getResource("img/info.gif");		
		//ImageIcon picture = new ImageIcon(urlForInfo);
		//Image infoPicture = picture.getImage();
		
		//AudioClip successClip = getAudioClip(getDocumentBase(), "sound/sucess.wav");
		//AudioClip failClip = getAudioClip(getDocumentBase(), "sound/failure.wav");
		
		//game.loadImageForInfo(infoPicture.getScaledInstance(INFO_ICON_DIMENSIONS.width, INFO_ICON_DIMENSIONS.height, Image.SCALE_SMOOTH));
		
		add(game);
		setSize(size);
	}

	public static void main(String[] args) {
		BallShootingWindow view = new BallShootingWindow();
		view.setVisible(true);
	}

}
