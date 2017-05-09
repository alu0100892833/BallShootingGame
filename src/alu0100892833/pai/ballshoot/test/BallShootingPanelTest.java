package alu0100892833.pai.ballshoot.test;

import static org.junit.Assert.*;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;

import alu0100892833.pai.ballshoot.view.BallShootingWindow;

/**
 * Test class for the BallShootingPanel class.
 * @author Óscar Darias Plasencia
 * @since 9-5-2017
 */
public class BallShootingPanelTest extends AssertJSwingJUnitTestCase {

	private FrameFixture frame;
	private BallShootingWindow program;
	
	@Override
	protected void onSetUp() {
		program = GuiActionRunner.execute(() -> new BallShootingWindow());
		frame = new FrameFixture(robot(), program);
		frame.show();
	}
	
	/**
	 * Test the info icon opens the information window.
	 */
	@Test
	public void testInformationFrameOpens() {
		frame.panel("Balls panel").label("InfoLabel").click();
		assertTrue(program.getMainPanel().getInfoFrame().isVisible());
	}
	
}
