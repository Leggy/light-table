package lighttable;

import java.net.URL;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

/**
 * Launcher class for Light Table Visualiser.
 * 
 * @author Leggy
 *
 */
public class LightTableLauncher extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL location = getClass().getResource("/LightTable.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(location);

        Parent root = loader.load(location.openStream());
        LightTableController controller = loader.getController();
		Scene scene = new Scene(root, 1200, 950);
		stage.setTitle("Light Table Visualiser");
		stage.setScene(scene);
		stage.setOnCloseRequest(e -> controller.stopGame());
		stage.setMinWidth(1200);
		stage.setMinHeight(950);
		stage.show();
	}
}
