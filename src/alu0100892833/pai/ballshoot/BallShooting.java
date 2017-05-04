package alu0100892833.pai.ballshoot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import alu0100892833.pai.ballshoot.elements.GameBall;

/**
 * Model for the Ball Shooting Game. Keeps all the necessary information.
 * Saves the objective balls in an ArrayList and the playing ball, using the GameBall class.
 * @author Óscar Darias Plasencia
 * @since 3-5-2017
 */
public class BallShooting {
	private static final int BALL_SEPARATION = 6;
	private static final int PLAYING_BALL_EXTRA_RADIUS = 20;

	private ArrayList<GameBall> objectives;			/* The balls at the top, the ones the player should make explode. */
	private GameBall playingBall;					/* The playing ball, the one the player can throw to the objectives. */
	private int ballRadius;
	private Dimension size;
	
	/**
	 * Constructor specifying the size of the panel where the game will be shown.
	 * Automatically generates the balls inside those dimensions.
	 * @param size Dimension of the available space for the game.
	 */
	public BallShooting(Dimension size, int ballRadius) {
		this.ballRadius = ballRadius;
		this.size = size;
		objectives = new ArrayList<>();
		createObjectives();
		newPlayingBall();
	}
	
	/**
	 * This method creates the objective balls, knowing the width of the available space.
	 * While there is enough space, it will keep painting balls.
	 * @param width
	 */
	private void createObjectives() {
		int height = ballRadius + BALL_SEPARATION; 
		int widthIterator = BALL_SEPARATION + ballRadius;
		int nextBallPosition = BALL_SEPARATION + ballRadius * 2;
		int neededSpace = BALL_SEPARATION * 2 + ballRadius * 3; 
		boolean keepPainting = true;
		while (keepPainting) {
			objectives.add(new GameBall(widthIterator, height, ballRadius));
			if (widthIterator + neededSpace > size.width)
				keepPainting = false;
			else
				widthIterator += nextBallPosition;
		}
	}
	
	/**
	 * Creates a new playing ball, always in the low-center of the available space.
	 * @param size Dimensions of the available space.
	 */
	private void newPlayingBall() {
		int playingBallRadius = ballRadius + PLAYING_BALL_EXTRA_RADIUS;
		int x = (int) (size.width / 2);
		int y = size.height - playingBallRadius - BALL_SEPARATION;
		playingBall = new GameBall(x, y, playingBallRadius);
	}
	
	/**
	 * Method that paints everything.
	 * @param graphics
	 */
	public void paint(Graphics graphics) {
		for (GameBall ball : getObjectives())
			ball.paintBall(graphics);
		getPlayingBall().paintBall(graphics);
	}

	/**
	 * Manages collisions, checking if the playing ball is touching one and just one of the objectives.
	 * If it is touching multiple objectives, the playing ball is destroyed.
	 * @return True, if there was an impact, and false in other case.
	 */
	public boolean thereIsCollision() { 
		boolean impact = false;
		for (int i = 0; i < getObjectives().size(); i++) {
			if ((getPlayingBall().isTouching(getObjective(i))) 
					&& (!getPlayingBall().isTouching(getObjective(i - 1))) 
					&& (!getPlayingBall().isTouching(getObjective(i + 1)))) {
				getObjectives().remove(getObjective(i));
				newPlayingBall();
				impact = true;
			} else if ((getPlayingBall().isTouching(getObjective(i))) 
					&& ((getPlayingBall().isTouching(getObjective(i - 1))) || (getPlayingBall().isTouching(getObjective(i + 1))))) {
				newPlayingBall();
				impact = true;
			}
		}
		return impact;
	}
	
	/**
	 * Causes the playing ball to advance to the given Point.
	 * @param destination
	 */
	public void shootingTo(double shootingAngle) {
		getPlayingBall().advanceTo(shootingAngle);
	}
	
	/**
	 * Interrupts any possible shot and resets.
	 */
	public void interrupt() {
		newPlayingBall();
	}
	
	/**
	 * Allows to obtain an specific objective.
	 * @param index Position of the objective: from left to right, starting from 0.
	 * @return null if the ball does not exist, and the ball itself otherwise.
	 */
	private GameBall getObjective(int index) {
		if ((index < 0) || (index >= getObjectives().size()))
			return null;
		else 
			return getObjectives().get(index);
	}
	
	
	
	
	
	////////////////////////////
	////// GETTERS AND SETTERS
	////////////////////////////
	
	public ArrayList<GameBall> getObjectives() {
		return objectives;
	}

	public void setObjectives(ArrayList<GameBall> objectives) {
		this.objectives = objectives;
	}

	public GameBall getPlayingBall() {
		return playingBall;
	}

	public void setPlayingBall(GameBall playingBall) {
		this.playingBall = playingBall;
	}

	public int getBallRadius() {
		return ballRadius;
	}

	public void setBallRadius(int ballRadius) {
		this.ballRadius = ballRadius;
	}

	public Dimension getSize() {
		return size;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}
	
	
	
}
