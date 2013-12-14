import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class GraphicNeedle extends Needle {
	private Graphics2D g2;
	private ArrayList needleLines;
	private int dropDelay;
	private boolean pauseFlag=false;
	private boolean stopFlag=false;

	private class needleLine {
		public double startX, startY, stopX, stopY;
		Color color;

		needleLine(double startX, double startY, double stopX, double stopY, Color color) {
			this.startX=startX;
			this.startY=startY;
			this.stopX=stopX;
			this.stopY=stopY;
			this.color=color;
		}

		void draw(Graphics2D g2, Color color, double pixelScaling) {
			Paint savePaint = g2.getPaint();
			g2.setPaint(color);

			double x1 = (startX * pixelScaling);
			double y1 = (startY * pixelScaling);
			double x2 = (stopX * pixelScaling);
			double y2 = (stopY * pixelScaling);

			g2.draw(new Line2D.Double(x1, y1, x2, y2));

			g2.setPaint(savePaint);
		}

		void draw(Graphics2D g2, double pixelScaling) {
			draw(g2, this.color, pixelScaling);
		}
	}

	GraphicNeedle(double length, int dropDelay) {
		super(length);
		this.dropDelay = dropDelay;
		needleLines = new ArrayList();
	}


	void repaintDroppings(Graphics2D g2, Floor f) {
		for(int i=0; i < needleLines.size(); i++) {
			((needleLine)needleLines.get(i)).draw(g2, f.getPixelScaling());
		}

	}

	public void Drop(Floor f) throws TerminateException {

		while((pauseFlag == true) && (stopFlag == false)) {
			Thread.yield();
		}

		if(stopFlag == true) {
			throw new TerminateException();
		}
		
		g2 = (Graphics2D) f.getGraphics();

/*
		if(needleLines.size() > 0) {
			((needleLine)needleLines.get(needleLines.size()-1)).draw(g2, Color.yellow, f.getPixelScaling());
		}
*/
		
		super.Drop(f);
		try {
			Thread.sleep(dropDelay);
		}
		catch (InterruptedException e) {
		}

		
		if(f.isOnCrack(this)) {
			needleLines.add(new needleLine(getStartX(), getStartY(), getStopX(), getStopY(), Color.green));
		}
		else {
			needleLines.add(new needleLine(getStartX(), getStartY(), getStopX(), getStopY(), Color.blue));
		}

		((needleLine)needleLines.get(needleLines.size()-1)).draw(g2, f.getPixelScaling());
	}

	synchronized public void pause() {
		pauseFlag = true;
	}

	synchronized public void resume() {
		pauseFlag = false;
	}

	synchronized public void terminate() {
		stopFlag = true;
	}

}
