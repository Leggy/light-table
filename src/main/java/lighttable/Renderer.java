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

	public enum RenderMode {
		HSV, RGB, DISTANCE
	}
	
	private double hsvSaturation;
	private double hsvValue;

	private GraphicsContext graphicsContext;
	private int pixelSize;
	private RenderMode renderMode;

	Table table;

	/**
	 * The rendered size (in pixels) of a Tile.
	 */

	public Renderer(GraphicsContext graphicsContext, Table table, int pixelSize) {
		super();
		this.table = table;
		this.graphicsContext = graphicsContext;
		this.pixelSize = pixelSize;
		this.renderMode = RenderMode.HSV;
		this.hsvSaturation = 1.0;
		this.hsvValue = 1.0;
	}
	
	public void setRenderMode(RenderMode renderMode){
		this.renderMode = renderMode;
	}
	
	public void setHsvSaturation(double hsvSaturation){
		this.hsvSaturation = hsvSaturation;
	}
	
	public void setHsvValue(double hsvValue){
		this.hsvValue = hsvValue;
	}

	@Override
	public void handle(long arg0) {
		renderTable();
	}

	/**
	 * Renders the world to the canvas.
	 */
	private void renderTable() {
		// graphicsContext.setFill(new Color(0.8, 0, 0.8, 1));
		for (int y = 0; y < table.yMax(); y++) {
			// graphicsContext.setFill(getColour(((double)y)/table.yMax()));
			for (int x = 0; x < table.xMax(); x++) {
				graphicsContext.setFill(getColour(x, y));

				graphicsContext.fillRect(x * pixelSize, y * pixelSize,
						pixelSize, pixelSize);
			}
		}
	}

	private Color getColour(int x, int y) {
		switch (renderMode) {
		case HSV:
			return hsvColor(x, y);
		case RGB:
			return rgbColor(x, y);
		case DISTANCE:
			return distanceColor(x, y);
		default:
			return new Color(0, 0, 0, 1);
		}
	}

	private Color hsvColor(int x, int y) {
		double noise = table.get(x, y);
		return Color.hsb(noise * 360, hsvSaturation, hsvValue);
	}

	private Color rgbColor(int x, int y) {
		double noise = table.get(x, y);
		double Bx = noise * Math.PI * Math.PI * 2 / 3;

		double r = (Math.sin(Bx) + 1) / 2;
		double g = (Math.sin(Bx + (2 * Math.PI) / 3) + 1) / 2;
		double b = (Math.sin(Bx - (2 * Math.PI) / 3) + 1) / 2;
		return new Color(r, g, b, 1);
	}
	
	private Color distanceColor(int x, int y){
		double noise = ( Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) * pixelSize) + ((double)System.currentTimeMillis())/10;
		return Color.hsb(noise % 360, 1, 1);
	}

	private Color getColourHaxMode(double noise) {
		double hue = noise * 360;
		if (hue < 30) {
			hue = 0;
		} else if (hue < 90) {
			hue = 60;
		} else if (hue < 150) {
			hue = 120;
		} else if (hue < 210) {
			hue = 180;
		} else if (hue < 270) {
			hue = 240;
		} else if (hue < 330) {
			hue = 300;
		} else {
			hue = 360;
		}
		return Color.hsb(hue, 1, 1);
	}

}
