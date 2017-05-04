package alu0100892833.pai.ballshoot.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

/**
 * Inner class that defines the behavior of the bottom arrow.
 * It should follow the direction pointed by the user with the mouth.
 * @author Óscar Darias Plasencia
 * @since 3-5-2017
 */
public class ShotCannon {
	public static final int DEFAULT_LENGTH = 150;
	public static final int LINE_STROKE = 23;
	public static final double ARROW_ANGLE = 0.0174533;
	public static final int ARROW_LENGTH = 20;
	protected final Color DEFAULT_COLOR = Color.DARK_GRAY;
	Point origin, end;
	
	/**
	 * Creates a Shot Cannon with its final origin and an initial end.
	 * @param origin
	 */
	public ShotCannon(Point origin) {
		this.origin = origin;
		this.end = new Point(origin.x, origin.y - DEFAULT_LENGTH);
	}
	
	/**
	 * It makes the line from origin to end point to the given Point.
	 * @param mousePoint Point that should represent the position pointed by the user using the mouse.
	 */
	public void endPointsTo(Point mousePoint) {
		int length = (int) Math.min(DEFAULT_LENGTH, Point.distance(mousePoint.x, mousePoint.y, origin.x, origin.y) - DEFAULT_LENGTH);
		if (length < 0)
			length = 0;
		double angle = Math.toDegrees(Math.atan2(mousePoint.y - origin.y, 
				mousePoint.x - origin.x));
		int endX = (int) (origin.x + Math.cos(Math.toRadians(angle)) * length);
		int endY = (int) (origin.y + Math.sin(Math.toRadians(angle)) * length);
		this.end = new Point(endX, endY);
	}

	/**
	 * Paints the cannon, knowing its origin and end.
	 * @param g
	 */
	public void paint(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setColor(DEFAULT_COLOR);
		graphics2d.setStroke(new BasicStroke(LINE_STROKE));
		graphics2d.draw(new Line2D.Double(origin.x, origin.y, end.x, end.y));
	}
}