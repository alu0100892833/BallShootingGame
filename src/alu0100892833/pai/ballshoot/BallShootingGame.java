package alu0100892833.pai.ballshoot;

import javax.swing.JApplet;

import alu0100892833.pai.ballshoot.view.BallShootingWindow;

public class BallShootingGame extends JApplet {
	private static final long serialVersionUID = 8526336847971124887L;

	public static void main(String[] args) {
		BallShootingWindow view = new BallShootingWindow();
		view.setVisible(true);
	}

}
