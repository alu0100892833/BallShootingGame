package alu0100892833.pai.ballshoot.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

import alu0100892833.pai.ballshoot.BallShooting;

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
	
	/**
	 * Constructor with parameters. 
	 * Creates the model and establishes the size of the panel.
	 * Also adds a MouseInteraction listener.
	 * @param size
	 * @param ballRadius
	 */
	public BallShootingPanel(Dimension size, int ballRadius) {
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
				getData().shootingTo(getObjectiveAngle());
				if (getData().thereIsCollision()) {
					setObjectiveAngle(Double.POSITIVE_INFINITY);
					getShootingTimer().stop();
				}
			}
			revalidate();
			repaint();
			if (getData().getObjectives().isEmpty())
				System.exit(0);
		}
	}
	
	
	/**
	 * Inner class that defines the behavior of the bottom arrow.
	 * It should follow the direction pointed by the user with the mouth.
	 * @author Óscar Darias Plasencia
	 * @since 3-5-2017
	 */
	protected class ShotCannon {
		public static final int DEFAULT_LENGTH = 150;
		public static final double ARROW_ANGLE = 0.0174533;
		public static final int ARROW_LENGTH = 20;
		protected final Color DEFAULT_COLOR = Color.RED;
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
			g.setColor(DEFAULT_COLOR);
			g.drawLine(origin.x, origin.y, end.x, end.y);
			//drawArrowHead(g, end, origin);
		}
		
		/**
		 * Paints the arrows of the cannon.
		 * @param graphics
		 */
		/*private void drawArrowHead(Graphics graphics, Point p1, Point p2) {
			 double phi = Math.toRadians(40);
		     int barb = 20;
		     double dy = p1.y - p2.y;
		     double dx = p1.x - p2.x;
		     double theta = Math.atan2(dy, dx);
		     double rho = theta + phi;
		     
		     for (int j = 0; j < 2; j++) {
		    	int x = (int) (p1.x - barb * Math.cos(rho));
		     	int y = (int) (p1.y - barb * Math.sin(rho));
		     	graphics.drawLine(p1.x, p1.x, x, y);
		     	rho = theta - phi;
		     }
		}*/
	}

}












//END
