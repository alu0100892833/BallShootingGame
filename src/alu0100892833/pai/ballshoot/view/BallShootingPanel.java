package alu0100892833.pai.ballshoot.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import alu0100892833.pai.ballshoot.BallShooting;
import alu0100892833.pai.ballshoot.elements.*;

/**
 * Panel where the Ball Shooting Game can be played.
 * The interaction should be made exclusively with the mouse, pointing at the position where the user wants to throw a ball.
 * @author Óscar Darias Plasencia
 * @since 3-5-2017
 */
public class BallShootingPanel extends JPanel {
	private static final long serialVersionUID = 4631647511943817494L;
	private static final int DELAY = 50;
	
	private BallShooting data;
	private ShotCannon cannon;
	private double objectiveAngle; 
	private Timer shootingTimer;
	private String successClip, failureClip;
	
	/**
	 * Constructor with parameters. 
	 * Creates the model and establishes the size of the panel.
	 * Also adds a MouseInteraction listener.
	 * @param size
	 * @param ballRadius
	 */
	public BallShootingPanel(Dimension size, int ballRadius) {
		this.setLayout(new BorderLayout());
		objectiveAngle = Double.POSITIVE_INFINITY;
		data = new BallShooting(size, ballRadius);
		cannon = new ShotCannon(data.getPlayingBall().getCenter());
		setSize(size);
		
		MouseInteraction listener = new MouseInteraction();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
		shootingTimer = new Timer(DELAY, new ShotGestion());
	}
	
	/**
	 * Saves the names of the clips to be played when missing and when having success in the game.
	 * @param sucess The name of the file with the success sound.
	 * @param failure The name of the file with the failure sound.
	 */
	public void loadSounds(String sucess, String failure) {
		this.successClip = sucess;
		this.failureClip = failure;
	}
	
	/**
	 * Allows to get the name of the file with the success clip.
	 * @return String.
	 */
	public String getSuccessClip() {
		return successClip;
	}

	/**
	 * Allows to get the name of the file with the failure clip.
	 * @return String.
	 */
	public String getFailureClip() {
		return failureClip;
	}

	/**
	 * Plays the sound contained in a file specified by parameter.
	 * @param fileName The name of the file that contains the sound you want to play.
	 */
	private void playClip(String fileName) {
		if (fileName != null) {
			Clip sucess = null;
			try {
				File sound = new File(fileName);
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
			    AudioFormat format = audioStream.getFormat();
			    DataLine.Info soundInfo = new DataLine.Info(Clip.class, format);
			    sucess = (Clip) AudioSystem.getLine(soundInfo);
			    sucess.open(audioStream);
			    sucess.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads the given image in a JLabel and situates it in the bottom-right corner of the panel.
	 * Also adds a listener to show an InformationFrame when the imagen is clicked.
	 * @param infoPicture
	 */
	public void loadImageForInfo(Image infoPicture) {
		JLabel picLabel = new JLabel(new ImageIcon(infoPicture));
		JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		infoPanel.add(picLabel);
		add(infoPanel, BorderLayout.SOUTH);
		picLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {  
				InformationFrame infoFrame = new InformationFrame();
				infoFrame.setVisible(true);
		    }  
		});
	}
	
	/**
	 * Getter
	 * @return ShootingTimer.
	 */
	public Timer getShootingTimer() {
		return shootingTimer;
	}

	/**
	 * Setter
	 * @param shootingTimer New shooting timer.
	 */
	public void setShootingTimer(Timer shootingTimer) {
		this.shootingTimer = shootingTimer;
	}

	/**
	 * Getter.
	 * @return The model.
	 */
	public BallShooting getData() {
		return data;
	}
	
	/**
	 * Getter.
	 * @return The cannon.
	 */
	public ShotCannon getCannon() {
		return cannon;
	}
	
	/**
	 * Getter.
	 * @return Objective Point.
	 */
	public double getObjectiveAngle() {
		return objectiveAngle;
	}

	/**
	 * Setter.
	 * @param objective New objective Point.
	 */
	public void setObjectiveAngle(double objectiveAngle) {
		this.objectiveAngle = objectiveAngle;
	}

	/**
	 * This method is overwritten to set the background all white and draw the model.
	 * For this second task, it calls the paint() method of the BallShooting objects.
	 */
	@Override
	protected void paintComponent(Graphics graphics) {
		setBackground(Color.WHITE);
		getData().paint(graphics);
		getCannon().paint(graphics);
	}
	
	
	/**
	 * Listener class (adapter) for the interaction with the panel using the mouse.
	 * Implements mouseCliked for throwing the ball and MouseMoved for the movement of the vector.
	 * @author Óscar Darias Plasencia
	 * @since 3-5-2017
	 */
	protected class MouseInteraction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (getObjectiveAngle() == Double.POSITIVE_INFINITY) {
				setObjectiveAngle(Math.toDegrees(Math.atan2(e.getY() - getData().getPlayingBall().getCenter().y, 
						e.getX() - getData().getPlayingBall().getCenter().x)));
				getShootingTimer().start();
			} else {
				getShootingTimer().stop();
				getData().interrupt();
				setObjectiveAngle(Double.POSITIVE_INFINITY);
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			getCannon().endPointsTo(new Point(x, y));
			repaint();
		}
	}
	
	/**
	 * Listener class for the behavior of the timer.
	 * @author Óscar Darias Plasencia
	 * @since 4-5-2017
	 */
	protected class ShotGestion implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getObjectiveAngle() == Double.POSITIVE_INFINITY) {
				getShootingTimer().stop();
			} else {
				getShootingTimer().stop();
				getData().shootingTo(getObjectiveAngle());
				int impact = getData().thereIsCollision();
				if (impact == BallShooting.SINGLE_COLLISION) {
					setObjectiveAngle(Double.POSITIVE_INFINITY);
					playClip(getSuccessClip());
				} else if (impact == BallShooting.MULTIPLE_COLLISION) {
					setObjectiveAngle(Double.POSITIVE_INFINITY);
					playClip(getFailureClip());
				} else {
					getShootingTimer().start();
				}
			}
			revalidate();
			repaint();
			if (getData().getObjectives().isEmpty())
				System.exit(0);
		}
	}

}












//END
