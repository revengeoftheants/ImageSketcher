import processing.core.*;

public class Cube {

	// Variables
	PApplet parPApp = null;
	int xCoordNbr, yCoordNbr;
	PShape thisCube;
	Boolean drawnInd = false;

	// Constructor
	/*
	 * Constructor
	 * 
	 * @param inpParPApp The parent PApplet object.
	 * @param inpShape The PShape to use for this cube.
	 * @param inpXCoordNbr The x coordinate of where this cube should be drawn.
	 * @param inpYCoordNbr The y coordinate of where this cube should be drawn.
	 */
	Cube(PApplet inpParPApp, PShape inpShape, int inpXCoordNbr, int inpYCoordNbr) {
		parPApp = inpParPApp;
		thisCube = inpShape;
		xCoordNbr = inpXCoordNbr;
		yCoordNbr = inpYCoordNbr;
	}

	/*
	 * Draws this cube.
	 */
	void draw() {
		parPApp.pushMatrix();
		parPApp.translate(xCoordNbr, yCoordNbr);
		parPApp.shape(thisCube);
		parPApp.popMatrix();
	}
}
