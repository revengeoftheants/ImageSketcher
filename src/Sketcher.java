import processing.core.*;
import java.util.*;

public class Sketcher {

	// Constants
	final int BORDER_BUFFER_NBR = 25;
	final int MAX_FAIL_CNT = 50;
	final int LRG_STEP_SZ_NBR = 10;
	final int SML_STEP_SZ_NBR = 5;
	final int RNG1_HIGH_CLR_NBR = 18000000;
	final int RNG1_LOW_CLR_NBR = 15500000;
	final int RNG2_HIGH_CLR_NBR = 15500000;
	final int RNG2_LOW_CLR_NBR = 14000000;
	// Medium tones like the t-shirt and shadows.
	final int RNG3_HIGH_CLR_NBR = 14000000;
	final int RNG3_LOW_CLR_NBR = 11700000;
	final int RNG4_HIGH_CLR_NBR = 11700000;
	final int RNG4_LOW_CLR_NBR = 9300000;
	// Light tones like skin.
	final int RNG5_HIGH_CLR_NBR = 9300000;
	final int RNG5_LOW_CLR_NBR = 7000000;
	final int RNG6_HIGH_CLR_NBR = 7000000;
	final int RNG6_LOW_CLR_NBR = 4000000;
	final int RNG1_SKETCH_CLR_NBR = 50;
	final int RNG2_SKETCH_CLR_NBR = 60;
	final int RNG3_SKETCH_CLR_NBR = 120;
	final int RNG4_SKETCH_CLR_NBR = 140;
	final int RNG5_SKETCH_CLR_NBR = 170;
	final int RNG6_SKETCH_CLR_NBR = 190;

	// Variables
	PApplet parPApp = null;
	int xNbr = 0;
	int yNbr = 0;
	PShape cubeShape;
	ArrayList<Cube> remainingCubes, drawnCubes;
	int lastSuccXNbr = 0;
	int lastSuccYNbr = 0;
	int failCnt = 0;
	int rtnSampleClrNbr = 0;
	int totClrSampleNbr = 0;
	int stepSzNbr = 0;

	/*
	 * Constructor
	 * 
	 * @param inpParPApp  		The parent PApplet.
	 * @param inpStrtXNbr  		The X coordinator where this sketcher should start.
	 * @param inpStrtYNbr  		The Y coordinator where this sketcher should start.
	 * @param inpRemainingCubes	An array containing all the cubes that still need to be drawn.
	 * @param inpCubes  		An array containing all the cubes that have been drawn.
	 * @param inpCubeShape 		The PShape to use to create this Sketcher's Cubes.
	 */
	Sketcher(PApplet inpParPApp, ArrayList<Cube> inpRemainingCubes, ArrayList<Cube> inpDrawnCubes) {
		parPApp = inpParPApp;
		remainingCubes = inpRemainingCubes;
		drawnCubes = inpDrawnCubes;
	}


	/*
	 * Causes this sketcher to take a random step and then determine if it should draw a new point.
	 */
	void draw() {
		
		if (remainingCubes.size() > 0) {
			int randomNbr = (int) parPApp.random(0, remainingCubes.size());
			drawnCubes.add(remainingCubes.get(randomNbr));
			remainingCubes.remove(randomNbr);	
		}
	}


	/*
	 * Samples the color of the current pixel as well as the pixels in its vicinity and returns
	 * their average value.
	 */
	/*
	private int sampleCurrLocClr() {
		rtnSampleClrNbr = 0;
		ArrayList<PVector> sampleLocs = new ArrayList<PVector>();
		totClrSampleNbr = 0;

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				PVector thisLoc = new PVector(xNbr + x, yNbr + y);
				if (thisLoc.x >= 0 && thisLoc.x < origImg.width && 
						thisLoc.y >= 0 && thisLoc.y < origImg.height) {
					sampleLocs.add(thisLoc);
				}
			}
		}

		for(Iterator<PVector> i = sampleLocs.iterator(); i.hasNext(); ) {
			PVector thisLoc = i.next();
			// Get the color of this pixel.
			// N.B., the "color" data type only exists in Processing.
			totClrSampleNbr += origImg.pixels[(int) thisLoc.x + ((int) thisLoc.y * origImg.width)];
		}

		return totClrSampleNbr / sampleLocs.size();
	}
	*/

}
