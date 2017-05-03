package alu0100892833.pai.ballshoot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Panel where the Ball Shooting Game can be played.
 * The interaction should be made exclusively with the mouse, pointing at the position where the user wants to throw a ball.
 * @author Óscar Darias Plasencia
 * @since 3-5-2017
 */
public class BallShootingPanel extends JPanel {
	private static final long serialVersionUID = 4631647511943817494L;
	
	private BallShooting data;
	private ShotCannon cannon;
	
	/**
	 * Constructor with parameters. 
	 * Creates the model and establishes the size of the panel.
	 * Also adds a MouseInteraction listener.
	 * @param size
	 * @param ballRadius
	 */
	public BallShootingPanel(Dimension size, int ballRadius) {
		data = new BallShooting(size, ballRadius);
		cannon = new ShotCannon(data.getPlayingBall().getCenter());
		setSize(size);
		
		MouseInteraction listener = new MouseInteraction();
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);
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
			
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			getCannon().endPointsTo(new Point(x, y));
			repaint();
		}
	}
	
	protected class ShotCannon {
		public static final int DEFAULT_LENGTH = 150;
		protected final Color DEFAULT_COLOR = Color.RED;
		Point origin, end;
		
		public ShotCannon(Point origin) {
			this.origin = origin;
			this.end = new Point(origin.x, origin.y - DEFAULT_LENGTH);
		}
		
		public void endPointsTo(Point mousePoint) {
			int length = (int) Math.min(DEFAULT_LENGTH, Point.distance(mousePoint.x, mousePoint.y, origin.x, origin.y) - DEFAULT_LENGTH);
			if (length < 0)
				length = 0;
			if (mousePoint.x == origin.x) {
				end = new Point(origin.x, origin.y + length);
				return;
			}
			
			double slope = (mousePoint.y - origin.y) / (mousePoint.x - origin.x);
			int x = origin.x + length;
			int y = (int) (x * slope);
			end = new Point(x, y);
		}
		
		public void paint(Graphics g) {
			g.setColor(DEFAULT_COLOR);
			g.drawLine(origin.x, origin.y, end.x, end.y);
		}
	}

}












//END
