package org.stth.pmi.barang.ui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.stth.pmi.barang.ui.ConfigScene;

import com.ibm.icu.text.SimpleDateFormat;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MasterController implements Initializable {

	ConfigScene cs;

	@FXML
	private Tab tabProses;

	@FXML
	private AnchorPane paneLoadProses;

	@FXML
	private Tab tabMaster;

	@FXML
	private AnchorPane paneLoadMaster;

	@FXML
	private Tab tabLaporan;

	@FXML
	private AnchorPane paneLoadLaporan;

	@FXML
	private Tab tabPegawai;

	@FXML
	private AnchorPane paneLoadPegawai;

	@FXML
	private Button btnLogout;

	@FXML
	private Button btnWaktu;

	@FXML
	private DatePicker calenderDate;
	
	@FXML
	private Label lblLogout;
	
	@FXML
	private Label lblTime;
	
	@FXML
	void onClickLogout(ActionEvent event) {
		
	}

	@FXML
	void onClickWaktu(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText("Waktu Sekarang");
		Label label = new Label();
		label.setStyle("-fx-font-size: 15px;-fx-font-family: 'Segoe UI';-fx-text-fill: black;");
		bindTimeLabel(label);
		
		alert.setGraphic(label);
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Parent pegawaiParent = FXMLLoader.load(getClass().getResource(
					"/org/stth/pmi/barang/ui/fxml/Pegawai.fxml"));
			Parent barangParent = FXMLLoader.load(getClass().getResource(
					"/org/stth/pmi/barang/ui/fxml/Barang.fxml"));
			Parent laporanParent = FXMLLoader.load(getClass().getResource(
					"/org/stth/pmi/barang/ui/fxml/Laporan.fxml"));
			Parent prosesParent = FXMLLoader.load(getClass().getResource(
					"/org/stth/pmi/barang/ui/fxml/Proses.fxml"));
			paneLoadPegawai.getChildren().add(pegawaiParent);
			paneLoadMaster.getChildren().add(barangParent);
			paneLoadLaporan.getChildren().add(laporanParent);
			paneLoadProses.getChildren().add(prosesParent);
			calenderDate.setEditable(false);
			calenderDate.setValue(LocalDate.now());
			bindTimeLabel(lblTime);
			AwesomeDude.setIcon(lblTime, AwesomeIcon.CLOCK_ALT, "15px");
			AwesomeDude.setIcon(lblLogout, AwesomeIcon.LOCK,"15px");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void bindTimeLabel(Label label) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),
				new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// TODO Auto-generated method stub
						Calendar time = Calendar.getInstance();
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
								"HH:mm:ss");
						label.setText(simpleDateFormat.format(time.getTime()));
					}

				}), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

}
