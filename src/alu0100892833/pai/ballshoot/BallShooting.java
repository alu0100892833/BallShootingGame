package alu0100892833.pai.ballshoot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
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
	public static final int MULTIPLE_COLLISION = 2;
	public static final int NO_COLLISION = 0;
	public static final int SINGLE_COLLISION = 1;
	public static final int WRONG_COLOR = 3;
	public static final int END_OF_PANEL = 4;
	public static final int OUT_OF_PANEL_RIGHT = 5;
	public static final int OUT_OF_PANEL_LEFT = 9;
	public static final int ALL_RIGHT = 6;
	private static final int N_BALL_ROWS = 2;

	private ArrayList<GameBall> objectives;			/* The balls at the top, the ones the player should make explode. */
	private GameBall playingBall;					/* The playing ball, the one the player can throw to the objectives. */
	private int ballRadius;							/* The ball´s standard radius */
	private Dimension size;							/* The size of the gaming space */
	
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
		int iterator = 0;
		while (keepPainting) {
			objectives.add(new GameBall(widthIterator, height, ballRadius));
			if (widthIterator + neededSpace > size.width)
				if (iterator < N_BALL_ROWS) {
					if (iterator == 0)
						widthIterator = (int) (2 * ballRadius + BALL_SEPARATION * 1.5);
					else 
						widthIterator = BALL_SEPARATION + ballRadius;
					height += ballRadius * 2 + BALL_SEPARATION;
				    iterator++;
				} else 
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
		int x = (int) (size.width / 2);
		int y = (int) (size.height - ballRadius * 3 - BALL_SEPARATION);
		playingBall = new GameBall(x, y, ballRadius);
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
	public int thereIsCollision() { 
		int impact = NO_COLLISION;
		for (int i = 0; i < getObjectives().size(); i++) {
			if ((getPlayingBall().isTouching(getObjective(i)))) {
				if (getPlayingBall().getColor() == getObjective(i).getColor()) {
					explodeBall(i);
					newPlayingBall();
					impact = SINGLE_COLLISION;
				} else {
					accumulateBall(i);
					newPlayingBall();
					impact = WRONG_COLOR;
				}
			} else if (getPlayingBall().getCenter().y - getPlayingBall().getRadius() < 0) {
				getObjectives().add(new GameBall(getPlayingBall()));
				newPlayingBall();
				impact = END_OF_PANEL;
			}
		}
		return impact;
	}
	
	/**
	 * Manages a ball explosion, looking for any close one with a similar color.
	 * @param objective Index of the exploding ball.
	 */
	private void explodeBall(int objective) {
		Color explodingColor = getObjective(objective).getColor();
		ArrayList<GameBall> explodingBalls = new ArrayList<>();
		explodingBalls.add(getObjective(objective));
		for (int i = 0; i < getObjectives().size(); i++) {
			if ((getObjective(i).isCloseToAny(explodingBalls, BALL_SEPARATION * 3)) 
					&& (getObjective(i).getColor().equals(explodingColor))
					&& (!explodingBalls.contains(getObjective(i)))) {
				explodingBalls.add(getObjective(i));
				i = -1;
			}
		}
		getObjectives().removeAll(explodingBalls);
	}
	
	private void accumulateBall(int objective) {
		int xPos = getObjective(objective).getCenter().x;
		int yPos = getObjective(objective).getCenter().y + ballRadius * 2;
		if (getPlayingBall().getCenter().x >= xPos) {
			xPos = xPos + getObjective(objective).getRadius() + (BALL_SEPARATION / 2);
		} else {
			xPos = xPos - getObjective(objective).getRadius() - (BALL_SEPARATION / 2);
		}
		getObjectives().add(new GameBall(new Point(xPos, yPos), ballRadius, getPlayingBall().getColor()));
	}
	
	/**
	 * Causes the playing ball to advance to the given Point.
	 * @param destination
	 */
	public int shootingTo(double shootingAngle) {
		getPlayingBall().advanceTo(shootingAngle);
		return playingOutOfPanel();
	}
	
	private int playingOutOfPanel() {
		if (getPlayingBall().getCenter().x - ballRadius <= 0)
			return OUT_OF_PANEL_LEFT;
		else if (getPlayingBall().getCenter().x + ballRadius >= getSize().width) 
			return OUT_OF_PANEL_RIGHT;
		else
			return ALL_RIGHT;
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
