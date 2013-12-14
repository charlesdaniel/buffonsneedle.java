/*
	Charles Daniel
	COMP 440 Summer 2002
	July 15, 2002
	Problem Set 3
	Problem #1: Calculate PI using Buffon's Needle Method
*/

import java.util.Random;

public class Needle {

	private double startX, startY, stopX, stopY;
	private double length;
	private double angle;
	private Random randGen;

	// Constructor to create a needle of length length
	public Needle(double length) {
		this.length=length;
		randGen = new Random();
	}

	// Returns the length of the needle
	public double getLength() {
		return length;
	}

	// Returns the starting X position
	public double getStartX() {
		return startX;
	}

	// Returns the starting Y position
	public double getStartY() {
		return startY;
	}

	// Returns the stopping X position
	public double getStopX() {
		return stopX;
	}

	// Returns the stopping Y position
	public double getStopY() {
		return stopY;
	}

	// This method drops the needle and only returns if
	// the needle lies within the boundaries of the floor.
	// NOTE: There is a possibility of non-determinism here
	//       if the random number generator is not good and
	//       we keep falling off the floor.
	public void Drop(Floor f) throws TerminateException {
		double xMax = f.getSizeX();
		double yMax = f.getSizeY();
		boolean done=false;


		// Keeping dropping the needle until the entire
		// needle (start and stop points) lie within the
		// boundaries of the Floor.
		do {
				  // Get a random starting X,Y and angle
				  startX = (randGen.nextDouble() * 100.0) % xMax;
				  startY = (randGen.nextDouble() * 100.0) % yMax;
				  angle = (randGen.nextDouble() * 1000.0) % 360.0;

				  // Convert the angle to radians
				  angle = Math.toRadians(angle);

				  // Calculate the stop points using trigonometry (argh!)
				  // and compute in the offset (from the starting X,Y)
				  stopX = length * Math.cos(angle) + startX;
				  stopY = length * Math.sin(angle) + startY;

				  // Check to see if the entire needle lies within the floor
				  // boundary
				  if((startX > 0.0) && (startX < xMax) &&
				     (startY > 0.0) && (startY < yMax) &&
					  (stopX > 0.0) && (stopX < xMax) &&
					  (stopY > 0.0) && (stopY < yMax)) {

					  		done = true;
				  }
		} while (!done);

	}


}
