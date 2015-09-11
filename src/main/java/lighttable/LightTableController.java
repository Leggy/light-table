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

public class LightTableController implements Initializable {

	@FXML
	private AnchorPane canvasPane;

	@FXML
	private Slider slider;

	private ExecutorService executor;

	private int worldSize = 2000;

	private int tableSize = 500;

	private int pixelSize = 50;

	Table table;

	private boolean running = false;

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

			// slider.valueProperty().addListener(new ChangeListener<Number>() {
			// public void changed(ObservableValue<? extends Number> ov,
			// Number oldVal, Number newVal) {
			// //Table.setZ(newVal.doubleValue() * Math.E);
			// }
			// });

			executor.execute(new TableThread(50, table));
			new Renderer(graphicsContext, table, pixelSize).start();
		}
		running = true;

	}

	public void stopGame() {
		if (executor != null) {
			executor.shutdown();
		}
	}

}
