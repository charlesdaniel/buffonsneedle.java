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


public class Floor extends JPanel {
	double sizeX;
	double sizeY;
	double boardWidth;
	double pixelScaling;
	Needle needleCache;
	
	// Constructor to define a Floor of size X by Y having
	// board widths of boardWidth
	public Floor(double sizeX, double sizeY, double boardWidth) {
		this.sizeX=sizeX;
		this.sizeY=sizeY;
		this.boardWidth=boardWidth;
		this.needleCache=null;

	}

	public Floor(double sizeX, double sizeY, double boardWidth, double pixelScaling) {
		this(sizeX, sizeY, boardWidth);
		this.pixelScaling = pixelScaling;

		setSize(new Dimension((int)(sizeX*pixelScaling)+2, (int)(sizeY*pixelScaling)+2) );
	}

	// Returns true if the needle n that is passed to it lies
	// *across* a crack, false otherwise
	// NOTE: Boards & cracks are assumed to lie parallel to the X axis.
	//       Therefore the boardWidth is a value on the Y axis.
	public boolean isOnCrack(Needle n) {
		// Get and "round-up" the starting and stopping Y values from
		// the needle (ie. 5.01 should lie on the 6th board if boardWidth
		// was 5).
		double y1 = n.getStartY();
		double y2 = n.getStopY();
		int y1Int = (int) y1;
		int y2Int = (int) y2;

		this.needleCache = n;

		if((y1 - y1Int) > 0) { y1Int++; }
		if((y2 - y2Int) > 0) { y2Int++; }

		// Determine if the starting Y and stopping Y of the needle
		// lie within the same board. Dividing the Y by the boardWidth
		// tells us which board that point lies in.
		if((int)(y1Int / boardWidth) == (int)(y2Int / boardWidth)) {
			return false;
		}

		return true;
	}
	
	// Returns maximum X dimension of the floor
	public double getSizeX() {
		return sizeX;
	}

	// Returns maximum Y dimension of the floor
	public double getSizeY() {
		return sizeY;
	}

	// Returns the width of each board
	public double getBoardWidth() {
		return boardWidth;
	}

	public double getPixelScaling() {
		return pixelScaling;
	}

	public void setPixelScaling(double pixelScaling) {
		this.pixelScaling = pixelScaling;
	}

	public void clean() {
		needleCache = null;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		double numRows = sizeY / boardWidth;
		double pixelBoardWidth = boardWidth * pixelScaling;

		double xMaxPixel = pixelBoardWidth * numRows;
		double yMaxPixel = pixelBoardWidth * numRows;

		g2.setPaint(Color.red);
		for(int i=0; i<=numRows; i++) {
			for(double j=((i*pixelBoardWidth)-pixelScaling); j<=(i*pixelBoardWidth); j++) {
			//g2.draw(new Line2D.Double(1, i*pixelBoardWidth, xMaxPixel-1, i*pixelBoardWidth));
				g2.draw(new Line2D.Double(1, j, xMaxPixel-1, j));
			}
		}

		Rectangle2D outline = new Rectangle2D.Double(0,0, xMaxPixel, yMaxPixel);
		g2.setPaint(Color.black);
		g2.draw(outline);

		if((needleCache != null) && (needleCache instanceof GraphicNeedle)){
			((GraphicNeedle)needleCache).repaintDroppings(g2, this);
		}
	}
}
