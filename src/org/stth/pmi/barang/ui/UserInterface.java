package org.stth.pmi.barang.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserInterface extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/org/stth/pmi/barang/ui/fxml/UserInterface.fxml"));
		AnchorPane rootLoadAnchorPane = (AnchorPane) loader.load();
		Scene scene = new Scene(rootLoadAnchorPane);
		primaryStage.setScene(scene);
		primaryStage.setAlwaysOnTop(true);
		primaryStage.setFullScreenExitHint("Tekan ESC pada keyboard untuk mengembalikan ke tampilan semula");
		primaryStage.setFullScreen(true);
		primaryStage.setTitle("Aplikasi Inventaris Barang");
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
