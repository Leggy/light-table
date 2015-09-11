package lighttable;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Renderer renders the {@link Table}.
 * 
 * @author Leggy
 *
 */
public class Renderer extends AnimationTimer {

	private GraphicsContext graphicsContext;
	private int pixelSize;

	Table table;

	/**
	 * The rendered size (in pixels) of a Tile.
	 */

	public Renderer(GraphicsContext graphicsContext, Table table, int pixelSize) {
		super();
		this.table = table;
		this.graphicsContext = graphicsContext;
		this.pixelSize = pixelSize;
	}

	@Override
	public void handle(long arg0) {
		renderWorld();
	}

	/**
	 * Renders the world to the canvas.
	 */
	private void renderWorld() {
		//graphicsContext.setFill(new Color(0.8, 0, 0.8, 1));
		for (int y = 0; y < table.yMax(); y++) {
			//graphicsContext.setFill(getColour(((double)y)/table.yMax()));
			for (int x = 0; x < table.xMax(); x++) {
				graphicsContext.setFill(getColour(table.get(x, y)));
				

				graphicsContext.fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize);
			}
		}
	}

	private Color getColour(double noise) {
		//System.out.println(noise);
		//double Bx = noise * Math.PI * Math.PI * 2 / 3;

//		double r = 1;//(Math.sin(Bx) + 1) / 2;
//		double g = 1;//(Math.sin(Bx + (2 * Math.PI) / 3) + 1) / 2;
//		double b = (Math.sin(Bx - (2 * Math.PI) / 3) + 1) / 2;
//		return new Color(r, g, b, 1);
		return Color.hsb(noise * 360, 1, 1);
	}
	
	private Color getColourHaxMode(double noise) {
		double hue = noise * 360;
		if(hue < 30){
			hue = 0;
		}else if(hue < 90){
			hue = 60;
		} else if(hue < 150){
			hue = 120;
		}else if (hue < 210){
			hue = 180;
		}else if(hue < 270){
			hue = 240;
		} else if(hue < 330){
			hue = 300;
		} else {
			hue = 360;
		}
		
		
		//double Bx = noise * Math.PI * Math.PI * 2 / 3;

//		double r = 1;//(Math.sin(Bx) + 1) / 2;
//		double g = 1;//(Math.sin(Bx + (2 * Math.PI) / 3) + 1) / 2;
//		double b = (Math.sin(Bx - (2 * Math.PI) / 3) + 1) / 2;
//		return new Color(r, g, b, 1);
		return Color.hsb(hue, 1, 1);
	}
	
	private Color getColourLandWater(double noise) {
		double hue = noise * 360;
		if(hue < 45){
			hue = 240;
		} else {
			hue = 120;
		}
		
		
		//double Bx = noise * Math.PI * Math.PI * 2 / 3;

//		double r = 1;//(Math.sin(Bx) + 1) / 2;
//		double g = 1;//(Math.sin(Bx + (2 * Math.PI) / 3) + 1) / 2;
//		double b = (Math.sin(Bx - (2 * Math.PI) / 3) + 1) / 2;
//		return new Color(r, g, b, 1);
		return Color.hsb(hue, 1, 1);
	}
}
