/*
	Charles Daniel
	COMP 440 Summer 2002
	July 15, 2002
	Problem Set 3
	Problem #1: Calculate PI using Buffon's Needle Method
*/

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.*;

public class GraphicSimulator extends Simulator {
	StatusPanel statusPanel;
	ToolbarPanel toolbarPanel;
	MainFrame mainWin;

	public static void main(String[] args) {
      // Define some constants to speed up some calculations
      final int maxNeedleLength = 6;
      final int maxDrops = 100;// 10000;
      final int precision = 5;
		final int floorSideSize = 60;
		final int boardSize = 6;
		final int dropDelay = 0;

      // Create a floor of dimensions 60x60 with boards having a width of 6
      GraphicSimulator s = new GraphicSimulator(floorSideSize, boardSize, maxNeedleLength);

	}
	
	GraphicSimulator() {
	}

	GraphicSimulator(int floorSideSize, int boardSize, int maxNeedleLength) {

		floor = new Floor(floorSideSize, floorSideSize, boardSize, 10);

		statusPanel = new StatusPanel();
		toolbarPanel = new ToolbarPanel(this,floor);

		mainWin = new MainFrame(700, 700);

		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWin.show();
		Container contentPane = mainWin.getContentPane();

		contentPane.setFont(new Font("Helvetica", Font.PLAIN, 14));

		contentPane.setLayout(new BorderLayout());


		contentPane.add(toolbarPanel, BorderLayout.NORTH);
		contentPane.add(floor, BorderLayout.CENTER);
		contentPane.add(statusPanel, BorderLayout.SOUTH);

	}

	public void simulate(int needleSize, int maxDrops, int precision, int dropDelay) {

		needle = new GraphicNeedle(needleSize, dropDelay);
		statusPanel.resetStatus();
		statusPanel.setNeedleSize(needleSize);
		statusPanel.setPrecision(precision);

		if(runDrops(maxDrops, precision)) {  // An accurate estimation was reached
			statusPanel.setMessage("A GOOD Estimation was Found!");
		}
		else {   // No good estimation (within the precision) was reached
			statusPanel.setMessage("NO GOOD Estimation was Found!");
		}

		statusPanel.setDropsMax(maxDrops);
		statusPanel.setDropsTotal(totalDrops);
		statusPanel.setDropsCrossing(dropsCrossingCrack);
		statusPanel.setEstimation(estimatedPI);

	}

	synchronized public void pause() {
		if(needle != null) {
			((GraphicNeedle) needle).pause();
		}
	}

	synchronized public void resume() {
		if(needle != null) {
			((GraphicNeedle) needle).resume();
		}
	}

	synchronized public void terminate() {
		if(needle != null) {
			((GraphicNeedle) needle).terminate();
		}
	}

	synchronized public void reset() {
		floor.clean();
		needle = null;
	}

}
