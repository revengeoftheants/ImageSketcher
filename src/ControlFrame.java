import processing.core.*;
import controlP5.*;

/*
 * @author Kevin Dean
 * Based on Andreas Schlegel, 2012
 * http://www.sojamo.de/libraries/controlP5/examples/extra/ControlP5frame/ControlP5frame.pde
 */
public class ControlFrame extends PApplet {

	/*
	 * Constants.
	 */
	final int BACKGRND_CLR_NBR = 0;
	final int CNTRL_X_COORD_STRT_NBR = 10;
	final int CNTRL_Y_COORD_STRT_OFFSET_NBR = 12;
	final int CNTRL_WDTH_NBR = 250;
	final int CNTRL_HGHT_NBR = 9;


	/*
	 * Global variables
	 */
	PApplet parPApp = null;
	ControlP5 cp5;
	int wdthNbr, hghtNbr;
	RadioButton thisRadioButton;


	/*
	 * Constructor
	 */
	public ControlFrame(PApplet inpParPApp, int inpWdthNbr, int inpHghtNbr) {
		parPApp = inpParPApp;
		wdthNbr = inpWdthNbr;
		hghtNbr = inpHghtNbr;
	}


	/*
	 * Retrieves the control.
	 */
	public ControlP5 rtrvCntrl() {
		return cp5;
	}


	/*
	 * (non-Javadoc)
	 * @see processing.core.PApplet#setup()
	 * 
	 * The ControlFrame class extends PApplet, so we are creating a new Processing
	 * applet inside a new frame, and it contains a ControlP5 object.
	 */
	public void setup() {
		size(wdthNbr, hghtNbr);
		frameRate(25);
		cp5 = new ControlP5(this);

		int y = 0;
		cp5.addSlider("eyeX", 0, 670, Main.eyeXNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "eyeXNbr");
		cp5.addSlider("eyeY", 0, 699, Main.eyeYNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "eyeYNbr");
		cp5.addSlider("eyeZ", -300, 300, Main.eyeZNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "eyeZNbr");
		cp5.addSlider("centerX", 0, 500, Main.centerXNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "centerXNbr");
		cp5.addSlider("centerY", 0, 500, Main.centerYNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "centerYNbr");
		cp5.addSlider("centerZ", 0, 500, Main.centerZNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "centerZNbr");
		/*
		cp5.addSlider("upX", -1, 1, Main.upXNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "upXNbr");
		cp5.addSlider("upY", -1, 1, Main.upYNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "upYNbr");
		cp5.addSlider("upZ", -1, 1, Main.upZNbr, CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR, CNTRL_WDTH_NBR, CNTRL_HGHT_NBR).plugTo(parPApp, "upZNbr");
		*/
		thisRadioButton = cp5.addRadioButton("btn", CNTRL_X_COORD_STRT_NBR, y += CNTRL_Y_COORD_STRT_OFFSET_NBR)
				.setSize(10, 10)
				.setColorActive(color(255))
				.addItem("upX", 1)
				.addItem("upY", 2)
				.addItem("upZ", 3);
		/*
	     for(Toggle t:thisRadioButton.getItems()) {
	         t.captionLabel().setColorBackground(color(255,80));
	         t.captionLabel().style().moveMargin(-7,0,0,-3);
	         t.captionLabel().style().movePadding(7,0,0,3);
	         t.captionLabel().style().backgroundWidth = 45;
	         t.captionLabel().style().backgroundHeight = 13;
	       }
		 */
		cp5.setAutoInitialization(true);
	}


	public void draw() {
		background(BACKGRND_CLR_NBR);
	}

	public void keyPressed() {
		switch(key) {
		case('0'): thisRadioButton.deactivateAll(); break;
		case('1'): thisRadioButton.activate(0); break;
		case('2'): thisRadioButton.activate(1); break;
		case('3'): thisRadioButton.activate(3); break;
		}

	}

	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isFrom(thisRadioButton)) {
			print("got an event from " + theEvent.getName() + "\t");
		}
	}
}
