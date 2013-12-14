/*
	Charles Daniel
	COMP 440 Summer 2002
	July 15, 2002
	Problem Set 3
	Problem #1: Calculate PI using Buffon's Needle Method
*/

public class Simulator {
	protected int dropsCrossingCrack=0;
	protected int totalDrops=0;
	protected double estimatedPI=0;
	protected Floor floor;
	protected Needle needle;

	public static void main(String [] args) {

		// Define some constants to speed up some calculations
		final int maxNeedleLength = 6;
		final int maxDrops = 10000;
		final int precision = 5;
		final double floorSideSize = 60;
		final double boardSize = 6;

		// Create a floor of dimensions 60x60 with boards having a width of 6
		Simulator s = new Simulator(floorSideSize, boardSize, maxNeedleLength, maxDrops, precision);

	}
		

	Simulator() {
	}

	Simulator(double floorSideSize, double boardSize, int maxNeedleLength, int maxDrops, int precision) {

		floor = new Floor(floorSideSize, floorSideSize, boardSize);

		System.out.println("Floor created of size [" + 
								floor.getSizeX() + "x" + floor.getSizeY() +
								"] having boards of width [" + floor.getBoardWidth() + "]");

		// For each needle length 1..6 (inclusive) do a run of maxDrops drops
		for(int needleLength = 1; needleLength <= maxNeedleLength; needleLength++) {

			System.out.println("\nNeedle Length=["+needleLength+"] :");

			needle = new Needle(needleLength);

			if(runDrops(maxDrops, precision)) {  // An accurate estimation was reached

				System.out.println("  Found an estimation to "+precision+" decimal place(s) !");
				System.out.println("  DropsCrossingCrack=["+dropsCrossingCrack+"] TotalDrops=["+totalDrops+"]");
				System.out.println("  The estimation comes to ["+estimatedPI+"]");
			}
			else {   // No good estimation (within the precision) was reached
				System.out.println("  NO estimation was found to "+precision+" decimal place(s) !");
				System.out.println("  DropsCrossingCrack=["+dropsCrossingCrack+"] TotalDrops=["+totalDrops+"]");
				System.out.println("  Final estimation was ["+estimatedPI+"]");
			}
		}
	}

	public boolean runDrops(int maxDrops, int precision) {

			final double precisionMultiplicative = Math.pow(10, precision);
			final double precisePI = Math.round(Math.PI * precisionMultiplicative);

			// Reset our counters etc.
			dropsCrossingCrack=0;
			totalDrops=0;
			estimatedPI=0;
			boolean found=false;

			// For a maximum of maxDrops drops (we may break early)
			for(totalDrops=0; totalDrops < maxDrops; totalDrops++) {
				// Drop the needle, Drop() guarantees the needle lies
				// within the Floor floor.
				try {
					needle.Drop(floor);
				}
				catch (TerminateException te) {
					break;
				}

				// Determine if the needle falls across a crack.
				// Note: Uncomment the println()s to see the drops as
				//       they happen ( '+'=needle is across crack,
				//       '.'=needle not across crack)
				if(floor.isOnCrack(needle)) {
					//System.out.print("+");
					dropsCrossingCrack++;
				}
				else {
					//System.out.print(".");
				}

				// Calculate an estimation of PI (found in Problem description)
				double P = (double)dropsCrossingCrack / (double)totalDrops;
				estimatedPI = (2 * needle.getLength()) / (P * floor.getBoardWidth());

				// Check if the estimation is accurate upto the precision
				// required with rounding.
				if((Math.round(estimatedPI * precisionMultiplicative)) == precisePI) {
					found=true;
					break;
				}
			}

			return found;
	}
}
