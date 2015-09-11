package lighttable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import lighttable.Renderer.RenderMode;

public class LightTableController implements Initializable {

	@FXML
	private AnchorPane canvasPane;

	@FXML
	private Slider hsvSaturation, hsvValue;

	private ExecutorService executor;

	private Renderer renderer;

	private int worldSize = 2000;

	private int tableSize = 500;

	private int pixelSize = 5;

	Table table;

	private boolean running = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table = new Table(200, 150);// tableSize*2, tableSize);

	}

	@FXML
	public void start(ActionEvent event) throws Exception {
		if (!running) {
			Canvas canvas = new Canvas();

			canvasPane.getChildren().add(canvas);
			canvas.widthProperty().bind(canvasPane.widthProperty());
			canvas.heightProperty().bind(canvasPane.heightProperty());

			GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

			executor = Executors.newCachedThreadPool();

			hsvSaturation.valueProperty().addListener(
					new ChangeListener<Number>() {
						public void changed(
								ObservableValue<? extends Number> ov,
								Number oldVal, Number newVal) {
							renderer.setHsvSaturation(newVal.doubleValue());
						}
					});
			
			hsvValue.valueProperty().addListener(
					new ChangeListener<Number>() {
						public void changed(
								ObservableValue<? extends Number> ov,
								Number oldVal, Number newVal) {
							renderer.setHsvValue(newVal.doubleValue());
						}
					});

			executor.execute(new TableThread(50, table));
			renderer = new Renderer(graphicsContext, table, pixelSize);
			renderer.start();
		}
		running = true;

	}

	@FXML
	public void hsv(ActionEvent event) throws Exception {
		renderer.setRenderMode(RenderMode.HSV);
	}

	@FXML
	public void rgb(ActionEvent event) throws Exception {
		renderer.setRenderMode(RenderMode.RGB);
	}

	@FXML
	public void distance(ActionEvent event) throws Exception {
		renderer.setRenderMode(RenderMode.DISTANCE);
	}

	public void stopGame() {
		if (executor != null) {
			executor.shutdown();
		}
	}

}
