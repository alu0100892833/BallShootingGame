package alu0100892833.pai.ballshoot.elements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class that allows to instantiate balls for the Ball Shooting Game. 
 * It saves its center point, its radius and generates a random color for its representation.
 * @author Óscar Darias Plasencia
 * @since 3-5-2017
 */
public class GameBall {
	private static final Color[] BALL_COLORS = {Color.RED, Color.BLUE, Color.YELLOW, Color.GRAY, Color.BLACK, Color.GREEN};
	private static final int ADVANCE = 10;
	
	private Point center;
	private int radius;
	private Color color;
	
	/**
	 * Constructor using direct parameters.
	 * @param center Point where the center of the ball is.
	 * @param radius Integer value. 
	 */
	public GameBall(Point center, int radius) {
		this.center = center;
		this.radius = radius;
		setRandomColor();
	}
	
	/**
	 * Copy constructor.
	 * @param copy
	 */
	public GameBall(GameBall copy) {
		this.center = copy.getCenter();
		this.radius = copy.getRadius();
		this.color = copy.getColor();
	}
	
	/**
	 * Constructor using indirect parameters.
	 * @param x The X coordinate of the center.
	 * @param y The Y coordinate of the center.
	 * @param radius Integer value.
	 */
	public GameBall(int x, int y, int radius) {
		this.center = new Point(x, y);
		this.radius = radius;
		setRandomColor();
	}
	
	/**
	 * Constructor using indirect parameters.
	 * @param x The X coordinate of the center.
	 * @param y The Y coordinate of the center.
	 * @param radius Integer value.
	 */
	public GameBall(Point center, int radius, Color color) {
		this.center = center;
		this.radius = radius;
		this.color = color;
	}

	/**
	 * Selects a random color from the constant array BALL_COLORS.
	 */
	private void setRandomColor() {
		Random colorSelector = new Random();
		setColor(BALL_COLORS[colorSelector.nextInt(BALL_COLORS.length)]);
	}
	
	/**
	 * Allows to obtain the diameter of the ball directly.
	 * @return Diameter of the ball, double the radius.
	 */
	public int getDiameter() {
		return getRadius() * 2;
	}
	
	/**
	 * Paints the ball.
	 * @param graphics Where the ball is going to be painted.
	 */
	public void paintBall(Graphics graphics) {
		graphics.setColor(getColor());
		int originX = getCenter().x - getRadius();
		int originY = getCenter().y - getRadius();
		graphics.fillOval(originX, originY, getDiameter(), getDiameter());
	}
	
	/**
	 * Checks if this ball is touching another one given by parameters.
	 * In other words, checks if the two balls have any point in common.
	 * @param otherBall
	 * @return True, if they are touching, or false.
	 */
	public boolean isTouching(GameBall otherBall) {
		if (otherBall == null)
			return false;
		double distanceBetweenCenters = Point.distance(getCenter().x, getCenter().y, otherBall.getCenter().x, otherBall.getCenter().y);
		if (distanceBetweenCenters <= getRadius() + otherBall.getRadius())
			return true;
		return false;
	}
	
	public boolean isCloseTo(GameBall otherBall, int closeBy) {
		if (otherBall == null)
			return false;
		double distanceBetweenCenters = Point.distance(getCenter().x, getCenter().y, otherBall.getCenter().x, otherBall.getCenter().y);
		if (distanceBetweenCenters <= getRadius() + otherBall.getRadius() + closeBy)
			return true;
		return false;
	}
	
	public boolean isCloseToAny(ArrayList<GameBall> balls, int closeBy) {
		if (balls.isEmpty())
			return false;
		for (GameBall ball : balls) {
			double distanceBetweenCenters = Point.distance(getCenter().x, getCenter().y, ball.getCenter().x, ball.getCenter().y);
			if (distanceBetweenCenters <= getRadius() + ball.getRadius() + closeBy)
				return true;
		}
		return false;
	}
	
	/**
	 * Causes the point to advance one position to the given destination.
	 * Uses the slope of the straight line that joins both points.
	 * @param destination Point where you expect to end up overtime.
	 */
	public void advanceTo(double angle) {
		int nextX = (int) (getCenter().x + Math.cos(Math.toRadians(angle)) * ADVANCE);
		int nextY = (int) (getCenter().y + Math.sin(Math.toRadians(angle)) * ADVANCE);
		this.setCenter(new Point(nextX, nextY));
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GameBall))
			return false;
		GameBall compare = (GameBall) obj;
		if (compare.getCenter().equals(getCenter()))
			return true;
		return false;
	}
	
	
	
	
	////////////////////////////
	////// GETTERS AND SETTERS
	////////////////////////////
	
	public Point getCenter() {
		return center;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}












//END