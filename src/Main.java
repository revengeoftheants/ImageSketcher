import processing.core.*;
import peasy.*;
import java.util.*;

public class Main extends PApplet {

	// Constants
	final int RNG_TYPS_CNT = 6;
	final int TIMER_DELAY_NBR = 1000; // 1 second
	final int CUBE_EDGE_SZ_NBR = 1;

	static enum IMG_CLR_RNGS {
		RNG1(-15500000, -18000000), // Dark tones like hair.
		RNG2(-14000000, -15500000), 
		RNG3(-11700000, -14000000), // Medium tones like the t-shirt and shadows.
		RNG4(-9300000, -11700000), 
		RNG5(-7000000, -9300000), // Light tones like skin.
		RNG6(-3700000, -7000000);

		private final int LOW, HIGH;

		// enum constructor
		IMG_CLR_RNGS(int inpLowNbr, int inpHighNbr) {
			this.LOW = inpLowNbr;
			this.HIGH = inpHighNbr;
		}
	}

	static enum CLR_TRANS_NBRS {
		RNG1(60), 
		RNG2(70), 
		RNG3(140), 
		RNG4(160), 
		RNG5(215), 
		RNG6(225);

		private final int NBR;

		// enum constructor
		CLR_TRANS_NBRS(int inpClrNbr) {
			this.NBR = inpClrNbr;
		}
	}

	// Variables
	PImage origImg, trimmedImg;
	ArrayList<Sketcher> sketchers = new ArrayList<Sketcher>();
	Hashtable<CLR_TRANS_NBRS, PShape> myPShapes;
	ArrayList<Cube> remainingCubes;
	ArrayList<Cube> drawnCubes = new ArrayList<Cube>();
	int highNbr, lowNbr, savedTmNbr, rngTypCntr;
	PeasyCam cam;
	Controls myCntrls;
	// for the camera
	static int eyeXNbr, eyeYNbr, eyeZNbr, centerXNbr, centerYNbr, centerZNbr, upXNbr, upYNbr, upZNbr;

	/*
	 * main method to run as a Java application outside Processing.
	 */
	public static void main(String args[]) {
		PApplet.main(new String[] { "Main" });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#setup()
	 */
	public void setup() {
		size(668, 696, P3D);
		frameRate(24);
		origImg = loadImage("Apparat.jpg");
		origImg.loadPixels();
		trimmedImg = trimImg(origImg);

		cam = new PeasyCam(this, width / 2, height / 2, 0, 600);
		cam.setMinimumDistance(1);
		cam.setMaximumDistance(600);

		myPShapes = crteShapes();
		remainingCubes = crteCubes(trimmedImg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see processing.core.PApplet#draw()
	 */
	public void draw() {
		background(230);
		
		int elapsedTmNbr = millis();

		if (remainingCubes.size() > 0
				&& (((elapsedTmNbr - savedTmNbr) > TIMER_DELAY_NBR) || sketchers.size() < 10)) {
			sketchers.add(new Sketcher(this, remainingCubes, drawnCubes));
			savedTmNbr = elapsedTmNbr;
		}

		for (Iterator<Sketcher> i = sketchers.iterator(); i.hasNext();) {
			Sketcher thisSketcher = i.next();
			thisSketcher.draw();
		}

		for (Iterator<Cube> i = drawnCubes.iterator(); i.hasNext();) {
			Cube thisCube = i.next();
			thisCube.draw();
		}

		// camera(eyeXNbr, eyeYNbr, eyeZNbr, centerXNbr, centerYNbr, centerZNbr, upXNbr, upYNbr, upZNbr);

	}

	/*
	 * Trims the image to dimensions that allow it to be evenly divisible.
	 * 
	 * @param The original image to trim.
	 * @return Returns an ArrayList of the pixels that remain after the trimming.
	 */
	private PImage trimImg(PImage inpOrigImg) {
		int wdthRemainderNbr, hghtRemainderNbr;
		int leftTrimNbr = 0;
		int rightTrimNbr = 0;
		int topTrimNbr = 0;
		int bottomTrimNbr = 0;
		float equalSideTrimNbr = 0;
		PImage rtnTrimmedImg = null;

		wdthRemainderNbr = inpOrigImg.width % CUBE_EDGE_SZ_NBR;
		if (wdthRemainderNbr > 0) {
			equalSideTrimNbr = wdthRemainderNbr / (float) 2;
			leftTrimNbr = floor(equalSideTrimNbr);
			rightTrimNbr = ceil(equalSideTrimNbr);
		}

		hghtRemainderNbr = inpOrigImg.height % CUBE_EDGE_SZ_NBR;
		if (hghtRemainderNbr > 0) {
			equalSideTrimNbr = hghtRemainderNbr / (float) 2;
			topTrimNbr = floor(equalSideTrimNbr);
			bottomTrimNbr = ceil(equalSideTrimNbr);
		}

		rtnTrimmedImg = createImage(inpOrigImg.width - wdthRemainderNbr, inpOrigImg.height - hghtRemainderNbr, RGB);
		rtnTrimmedImg.loadPixels();

		int trimmedImgIdxNbr = 0;
		// Create an ArrayList of the images pixels, trimming off any portion of the edges as necessary.
		for (int y = topTrimNbr; y < inpOrigImg.height - bottomTrimNbr; y++) {
			for (int x = leftTrimNbr; x < inpOrigImg.width - rightTrimNbr; x++) {
				rtnTrimmedImg.pixels[trimmedImgIdxNbr] = inpOrigImg.pixels[x + (y * inpOrigImg.width)];

				trimmedImgIdxNbr++;
			}
		}

		rtnTrimmedImg.updatePixels();
		return rtnTrimmedImg;
	}

	/*
	 * Creates the fundamental PShapes that we will use. Each PShape can then be
	 * reused by objects that will share its same geometry and color.
	 * 
	 * @return A Hashtable of PShapes keyed by a range color enumeration.
	 */
	private Hashtable<CLR_TRANS_NBRS, PShape> crteShapes() {
		Hashtable<CLR_TRANS_NBRS, PShape> rtnTbl = new Hashtable<CLR_TRANS_NBRS, PShape>();

		for (CLR_TRANS_NBRS rng : CLR_TRANS_NBRS.values()) {
			PShape rngShape = createShape(BOX, CUBE_EDGE_SZ_NBR);
			rngShape.setStroke(false);
			rngShape.setFill(color(rng.NBR));
			rtnTbl.put(rng, rngShape);
		}

		return rtnTbl;
	}

	/*
	 * Creates our Cubes by breaking up the image into areas of a given square
	 * size and recording the colors of the pixels within those areas.
	 * 
	 * @param inpTrimmedImg The trimmed image.
	 * @return An ArrayList of Cubes.
	 */
	private ArrayList<Cube> crteCubes(PImage inpTrimmedImg) {
		ArrayList<Cube> rtnCubes = new ArrayList<Cube>();
		int sampleClrNbr;
		PShape clrPShape = null;

		for (int y = 0; y < inpTrimmedImg.height; y += CUBE_EDGE_SZ_NBR) {
			for (int x = 0; x < inpTrimmedImg.width; x += CUBE_EDGE_SZ_NBR) {

				int[] cubeImgPxlClrNbrs = new int[CUBE_EDGE_SZ_NBR * CUBE_EDGE_SZ_NBR];

				int cubePxlNbr = 0;
				for (int yOffsetNbr = 0; yOffsetNbr < CUBE_EDGE_SZ_NBR; yOffsetNbr++) {
					for (int xOffsetNbr = 0; xOffsetNbr < CUBE_EDGE_SZ_NBR; xOffsetNbr++) {
						cubeImgPxlClrNbrs[cubePxlNbr] = inpTrimmedImg.pixels[x + xOffsetNbr + ((y + yOffsetNbr) * inpTrimmedImg.width)];

						cubePxlNbr++;
					}
				}

				sampleClrNbr = samplePxlClrs(cubeImgPxlClrNbrs);
				clrPShape = null;

				// The color integer values are negative because they are opaque.
				// http://wiki.processing.org/w/What_is_a_color_in_Processing%3F
				if (IMG_CLR_RNGS.RNG1.LOW >= sampleClrNbr && sampleClrNbr > IMG_CLR_RNGS.RNG1.HIGH) {
					clrPShape = myPShapes.get(CLR_TRANS_NBRS.RNG1);
				} else if (IMG_CLR_RNGS.RNG2.LOW >= sampleClrNbr && sampleClrNbr > IMG_CLR_RNGS.RNG2.HIGH) {
					clrPShape = myPShapes.get(CLR_TRANS_NBRS.RNG2);
				} else if (IMG_CLR_RNGS.RNG3.LOW >= sampleClrNbr && sampleClrNbr > IMG_CLR_RNGS.RNG3.HIGH) {
					clrPShape = myPShapes.get(CLR_TRANS_NBRS.RNG3);
				} else if (IMG_CLR_RNGS.RNG4.LOW >= sampleClrNbr && sampleClrNbr > IMG_CLR_RNGS.RNG4.HIGH) {
					clrPShape = myPShapes.get(CLR_TRANS_NBRS.RNG4);
				} else if (IMG_CLR_RNGS.RNG5.LOW >= sampleClrNbr && sampleClrNbr > IMG_CLR_RNGS.RNG5.HIGH) {
					clrPShape = myPShapes.get(CLR_TRANS_NBRS.RNG5);
				} else if (IMG_CLR_RNGS.RNG6.LOW >= sampleClrNbr && sampleClrNbr > IMG_CLR_RNGS.RNG6.HIGH) {
					clrPShape = myPShapes.get(CLR_TRANS_NBRS.RNG6);
				} else {
					// We won't create a cube if it doesn't fall within the above ranges.
				}

				if (clrPShape != null) {
					rtnCubes.add(new Cube(this, clrPShape, x, y));
				}
			}
		}

		return rtnCubes;
	}

	/*
	 * Samples the color of the current pixel as well as the pixels in its
	 * vicinity and returns their average value.
	 * 
	 * @param inpPxlClrs Array of pixel colors to sample.
	 * @return Integer of the average color value for these pixels.
	 */
	private int samplePxlClrs(int[] inpPxlClrNbrs) {
		int totClrSampleNbr = 0;

		for (int i = 0; i < inpPxlClrNbrs.length; i++) {
			totClrSampleNbr += inpPxlClrNbrs[i];
		}

		return totClrSampleNbr / inpPxlClrNbrs.length;
	}
}
