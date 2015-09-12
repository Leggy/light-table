package lighttable;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import javafx.beans.value.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LightTableController implements Initializable {

	@FXML
	private AnchorPane canvasPane;

	@FXML
	private Slider hsvSaturation, hsvValue;

	private ExecutorService executor;

	private Renderer renderer;

	private final int pixelSize;

	Table table;

	private boolean running = false;

	public LightTableController() {
		this.pixelSize = 50;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		table = new Table(20, 15);// tableSize*2, tableSize);
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

			hsvSaturation.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
				renderer.setHsvSaturation(newVal.doubleValue());
			});
			
			hsvValue.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldVal, Number newVal) -> {
				renderer.setHsvValue(newVal.doubleValue());
			});

			executor.execute(new TableThread(50, table));
			renderer = new Renderer(graphicsContext, table, pixelSize);
			renderer.start();
		}
		running = true;

	}

	@FXML
	public void hsv(ActionEvent event) throws Exception {
		renderer.setRenderMode(Renderer.Mode.HSV);
	}

	@FXML
	public void rgb(ActionEvent event) throws Exception {
		renderer.setRenderMode(Renderer.Mode.RGB);
	}

	@FXML
	public void distance(ActionEvent event) throws Exception {
		renderer.setRenderMode(Renderer.Mode.DISTANCE);
	}

	public void stopGame() {
		if (executor != null) {
			executor.shutdown();
		}
	}

}
