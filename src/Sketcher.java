import processing.core.*;
import java.util.*;

public class Sketcher {

	// Constants
	final int BORDER_BUFFER_NBR = 25;
	final int MAX_FAIL_CNT = 50;
	final int LRG_STEP_SZ_NBR = 10;
	final int SML_STEP_SZ_NBR = 5;
	
	// Variables
	PApplet parPApp = null;
	int xNbr = 0;
	int yNbr = 0;
	int lowTargetClrNbr = 0;
	int highTargetClrNbr = 0;
	PImage origImg = null;
	PImage newImg = null;
	int sketchClrNbr = 0;
	int lastSuccXNbr = 0;
	int lastSuccYNbr = 0;
	int failCnt = 0;
	int rtnSampleClrNbr = 0;
	int totClrSampleNbr = 0;
	int stepSzNbr = 0;
	
	/*
	 * Constructor
	 * 
	 * @param inpParPApp  The parent PApplet.
	 * @param inpStrtXNbr  The X coordinator where this sketcher should start.
	 * @param inpStrtYNbr  The Y coordinator where this sketcher should start.
	 * @param inpLowClrNbr  The low value of the range of colors to target.
	 * @param inpHighClrNbr  The high value of the range of colors to target.
	 * @param inpOrigImg  The image to analyze.
	 * @param inpNewImg  The new image to build.
	 */
	Sketcher(PApplet inpParPApp, int inpStrtXNbr, int inpStrtYNbr, int inpLowClrNbr, int inpHighClrNbr, 
			 PImage inpOrigImg, PImage inpNewImg, int inpSketchClrNbr) {
		parPApp = inpParPApp;
		xNbr = inpStrtXNbr;
		yNbr = inpStrtYNbr;
		lastSuccXNbr = inpStrtXNbr;
		lastSuccYNbr = inpStrtYNbr;
		lowTargetClrNbr = PApplet.abs(inpLowClrNbr);
		highTargetClrNbr = PApplet.abs(inpHighClrNbr);
		origImg = inpOrigImg;
		newImg = inpNewImg;
		sketchClrNbr = inpSketchClrNbr;
	}
	
	
	/*
	 * Causes this sketcher to take a random step and then determine if it should draw a new point.
	 */
	void draw() {
		// If we have not successfully colored a pixel in a while, move back to our last success location.
		// There is a good chance that there will be more to fill in around there.
		if (failCnt >= MAX_FAIL_CNT) {
			xNbr = lastSuccXNbr;
			yNbr = lastSuccYNbr;
			stepSzNbr = SML_STEP_SZ_NBR;
		} else {
			stepSzNbr = LRG_STEP_SZ_NBR;
		}
		
		// Take a step, making sure to stay within the image size.
		xNbr += PApplet.round(parPApp.random(-stepSzNbr, stepSzNbr));
		yNbr += PApplet.round(parPApp.random(-stepSzNbr, stepSzNbr)); 
		xNbr = PApplet.max(xNbr, 0);
		xNbr = PApplet.min(xNbr, origImg.width - 1);
		
		yNbr = PApplet.max(yNbr, 0);
		yNbr = PApplet.min(yNbr, origImg.height - 1);
		
		// If we hit the border, we'll move the point inward a bit so that it is able to do more work.
		if (xNbr == 0) {
			xNbr = BORDER_BUFFER_NBR;
		}
		if (xNbr == origImg.width - 1) {
			xNbr = origImg.width - BORDER_BUFFER_NBR;
		}
		if (yNbr == 0) {
			yNbr = BORDER_BUFFER_NBR;
		}
		if (yNbr == origImg.height - 1) {
			yNbr = origImg.height - BORDER_BUFFER_NBR;
		}
		
		// Take a sample of the color at this location. If it falls within this sketcher's target range,
		// set this pixel color for our new image.
		int sampleClrNbr = PApplet.abs(sampleCurrLocClr());
		if (sampleClrNbr >= lowTargetClrNbr && sampleClrNbr <= highTargetClrNbr && newImg.pixels[xNbr + (yNbr * origImg.width)] != sketchClrNbr) {
			newImg.pixels[xNbr + (yNbr * origImg.width)] = sketchClrNbr;
				
				lastSuccXNbr = xNbr;
				lastSuccYNbr = yNbr;
				failCnt = 0;
		} else {
			failCnt++;
		}
	}
	
	
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
				totClrSampleNbr += origImg.pixels[xNbr + (yNbr * origImg.width)];
		}
		
		return totClrSampleNbr / sampleLocs.size();
	}
	
}
