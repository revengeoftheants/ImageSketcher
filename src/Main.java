import processing.core.*;
import java.util.*;

public class Main extends PApplet {


	// Constants
	final int RNG_TYPS_CNT = 6;
	// Dark tones like hair.
	final int RNG1_HIGH_CLR_NBR = -18000000;
	final int RNG1_LOW_CLR_NBR = -15500000;
	final int RNG2_HIGH_CLR_NBR = -15500000;
	final int RNG2_LOW_CLR_NBR = -14000000;
	// Medium tones like the t-shirt and shadows.
	final int RNG3_HIGH_CLR_NBR = -14000000;
	final int RNG3_LOW_CLR_NBR = -11700000;
	final int RNG4_HIGH_CLR_NBR = -11700000;
	final int RNG4_LOW_CLR_NBR = -9300000;
	// Light tones like skin.
	final int RNG5_HIGH_CLR_NBR = -9300000;
	final int RNG5_LOW_CLR_NBR = -7000000;
	final int RNG6_HIGH_CLR_NBR = -7000000;
	final int RNG6_LOW_CLR_NBR = -4000000;
	final int RNG1_SKETCH_CLR_NBR = color(50);
	final int RNG2_SKETCH_CLR_NBR = color(60);
	final int RNG3_SKETCH_CLR_NBR = color(120);
	final int RNG4_SKETCH_CLR_NBR = color(140);
	final int RNG5_SKETCH_CLR_NBR = color(170);
	final int RNG6_SKETCH_CLR_NBR = color(190);
	final int TIMER_DELAY_NBR = 350; // 0.35 second

	// Variables
	PImage origImg = null;
	PImage newImg = null;
	ArrayList<Sketcher> sketchers = new ArrayList<Sketcher>();
	int highNbr = 0;
	int lowNbr = 0;
	int savedTmNbr = 0;
	int rngTypCntr = 0;


	public static void main(String args[]) {
		PApplet.main(new String[] { "Main" });
	}


	public void setup() {
		origImg = loadImage("Apparat.jpg");
		origImg.loadPixels();
		size(origImg.width, origImg.height);
		newImg = createImage(origImg.width, origImg.height, ARGB);
		//loadFont("LucidaBright-Demi-16.vlw");
	}


	public void draw() {
		background(255);
		//image(origImg, 0, 0);
		int elapsedTmNbr = millis();
		
		if (((elapsedTmNbr - savedTmNbr) > TIMER_DELAY_NBR) || sketchers.size() < 10) {
			crtesketcher();
			savedTmNbr = elapsedTmNbr;
		}

		for (Iterator<Sketcher> i = sketchers.iterator(); i.hasNext();) {
			Sketcher thisSketcher = i.next();
			thisSketcher.draw();
		}

		newImg.updatePixels();
		set(0, 0, newImg);


		/*
		int rtnSampleClrNbr = 0;
		ArrayList<PVector> sampleLocs = new ArrayList<PVector>();
		int totClrSampleNbr = 0;

		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				PVector thisLoc = new PVector(mouseX + x, mouseY + y);
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
				totClrSampleNbr += origImg.pixels[mouseX + (mouseY * origImg.width)];
		}

		//totClrSampleNbr / sampleLocs.size();

		//int pxlClrNbr = origImg.pixels[mouseX + (mouseY*origImg.width)];

		int txtXPosNbr = mouseX + 10;
		int txtYPosNbr = mouseY + 10;

		if (txtXPosNbr >= origImg.width - 80) {
			txtXPosNbr -= 90;
		}

		if (txtYPosNbr >= origImg.height) {
			txtYPosNbr -= 10;
		}

		text(totClrSampleNbr / sampleLocs.size(), txtXPosNbr, txtYPosNbr);
		 */

		/*
		if (mouseX <= origImg.width && mouseY <= origImg.height && mouseY >= 250) {
			if (abs(pxlClrNbr) > highNbr) {
				highNbr = abs(pxlClrNbr);
			}

			if (abs(pxlClrNbr) < lowNbr || lowNbr == 0) {
				lowNbr = abs(pxlClrNbr);
			}
		}
		 */
	}


	/*
	 * Creates the set of sketchers that will randomly walk around, filling in different parts of the image.
	 */
	void crtesketcher() {
		/*
		int xNbr = (int) random(0, origImg.width);
		int yNbr = (int) random(0, origImg.height);
		Sketcher rng1Sketcher = new Sketcher(this, xNbr, yNbr, RNG1_LOW_CLR_NBR, RNG1_HIGH_CLR_NBR, origImg, newImg, RNG1_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(0, origImg.height);
		Sketcher rng1Sketcher2 = new Sketcher(this, xNbr, yNbr, RNG1_LOW_CLR_NBR, RNG1_HIGH_CLR_NBR, origImg, newImg, RNG1_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(0, origImg.height);
		Sketcher rng1Sketcher3 = new Sketcher(this, xNbr, yNbr, RNG1_LOW_CLR_NBR, RNG1_HIGH_CLR_NBR, origImg, newImg, RNG1_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(0, origImg.height);
		Sketcher rng1Sketcher4 = new Sketcher(this, xNbr, yNbr, RNG1_LOW_CLR_NBR, RNG1_HIGH_CLR_NBR, origImg, newImg, RNG1_SKETCH_CLR_NBR);

		// We will start this close to the shirt
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4 * 3, origImg.height);
		Sketcher rng2Sketcher = new Sketcher(this, xNbr, yNbr, RNG2_LOW_CLR_NBR, RNG2_HIGH_CLR_NBR, origImg, newImg, RNG2_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4 * 3, origImg.height);
		Sketcher rng2Sketcher2 = new Sketcher(this, xNbr, yNbr, RNG2_LOW_CLR_NBR, RNG2_HIGH_CLR_NBR, origImg, newImg, RNG2_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4 * 3, origImg.height);
		Sketcher rng2Sketcher3 = new Sketcher(this, xNbr, yNbr, RNG2_LOW_CLR_NBR, RNG2_HIGH_CLR_NBR, origImg, newImg, RNG2_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4 * 3, origImg.height);
		Sketcher rng2Sketcher4 = new Sketcher(this, xNbr, yNbr, RNG2_LOW_CLR_NBR, RNG2_HIGH_CLR_NBR, origImg, newImg, RNG2_SKETCH_CLR_NBR);

		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(0, origImg.height);
		Sketcher rng3Sketcher = new Sketcher(this, xNbr, yNbr, RNG3_LOW_CLR_NBR, RNG3_HIGH_CLR_NBR, origImg, newImg, RNG3_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(0, origImg.height);
		Sketcher rng3Sketcher2 = new Sketcher(this, xNbr, yNbr, RNG3_LOW_CLR_NBR, RNG3_HIGH_CLR_NBR, origImg, newImg, RNG3_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(0, origImg.height);
		Sketcher rng3Sketcher3 = new Sketcher(this, xNbr, yNbr, RNG3_LOW_CLR_NBR, RNG3_HIGH_CLR_NBR, origImg, newImg, RNG3_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(0, origImg.height);
		Sketcher rng3Sketcher4 = new Sketcher(this, xNbr, yNbr, RNG3_LOW_CLR_NBR, RNG3_HIGH_CLR_NBR, origImg, newImg, RNG3_SKETCH_CLR_NBR);

		// We will start this close to the eyes.
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4, origImg.height/4 * 3);
		Sketcher rng4Sketcher = new Sketcher(this, xNbr, yNbr, RNG4_LOW_CLR_NBR, RNG4_HIGH_CLR_NBR, origImg, newImg, RNG4_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4, origImg.height/4 * 3);
		Sketcher rng4Sketcher2 = new Sketcher(this, xNbr, yNbr, RNG4_LOW_CLR_NBR, RNG4_HIGH_CLR_NBR, origImg, newImg, RNG4_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4, origImg.height/4 * 3);
		Sketcher rng4Sketcher3 = new Sketcher(this, xNbr, yNbr, RNG4_LOW_CLR_NBR, RNG4_HIGH_CLR_NBR, origImg, newImg, RNG4_SKETCH_CLR_NBR);
		xNbr = (int) random(0, origImg.width);
		yNbr = (int) random(origImg.height/4, origImg.height/4 * 3);
		Sketcher rng4Sketcher4 = new Sketcher(this, xNbr, yNbr, RNG4_LOW_CLR_NBR, RNG4_HIGH_CLR_NBR, origImg, newImg, RNG4_SKETCH_CLR_NBR);


		sketchers.add(rng1Sketcher);
		sketchers.add(rng1Sketcher2);
		sketchers.add(rng1Sketcher3);
		sketchers.add(rng1Sketcher4);
		sketchers.add(rng2Sketcher);
		sketchers.add(rng2Sketcher2);
		sketchers.add(rng2Sketcher3);
		sketchers.add(rng2Sketcher4);
		sketchers.add(rng3Sketcher);
		sketchers.add(rng3Sketcher2);
		sketchers.add(rng3Sketcher3);
		sketchers.add(rng3Sketcher4);
		sketchers.add(rng4Sketcher);
		sketchers.add(rng4Sketcher2);
		sketchers.add(rng4Sketcher3);
		sketchers.add(rng4Sketcher4);
		*/
		
		int xNbr = (int) random(0, origImg.width);
		int yNbr = (int) random(0, origImg.height);
		Sketcher thisSketcher = null;
		int rngTypNbr = rngTypCntr % RNG_TYPS_CNT + 1;
		
		switch (rngTypNbr) {
		case 1:
			thisSketcher = new Sketcher(this, xNbr, yNbr, RNG1_LOW_CLR_NBR, RNG1_HIGH_CLR_NBR, origImg, newImg, RNG1_SKETCH_CLR_NBR);
			break;
		case 2:
			thisSketcher = new Sketcher(this, xNbr, yNbr, RNG2_LOW_CLR_NBR, RNG2_HIGH_CLR_NBR, origImg, newImg, RNG2_SKETCH_CLR_NBR);
			break;
		case 3:
			thisSketcher = new Sketcher(this, xNbr, yNbr, RNG3_LOW_CLR_NBR, RNG3_HIGH_CLR_NBR, origImg, newImg, RNG3_SKETCH_CLR_NBR);
			break;
		case 4:
			thisSketcher = new Sketcher(this, xNbr, yNbr, RNG4_LOW_CLR_NBR, RNG4_HIGH_CLR_NBR, origImg, newImg, RNG4_SKETCH_CLR_NBR);
			break;
		case 5:
			thisSketcher = new Sketcher(this, xNbr, yNbr, RNG5_LOW_CLR_NBR, RNG5_HIGH_CLR_NBR, origImg, newImg, RNG5_SKETCH_CLR_NBR);
			break;
		case 6:
			thisSketcher = new Sketcher(this, xNbr, yNbr, RNG6_LOW_CLR_NBR, RNG6_HIGH_CLR_NBR, origImg, newImg, RNG6_SKETCH_CLR_NBR);
			break;
		default:
			thisSketcher = new Sketcher(this, xNbr, yNbr, RNG1_LOW_CLR_NBR, RNG1_HIGH_CLR_NBR, origImg, newImg, RNG1_SKETCH_CLR_NBR);
		}
	
		sketchers.add(thisSketcher);
		
		rngTypCntr++;
	}
}
