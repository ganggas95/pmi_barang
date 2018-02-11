package org.stth.pmi.barang.ui.controller;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.stth.pmi.barang.entitas.Barang;
import org.stth.pmi.barang.entitas.Jenis;
import org.stth.pmi.barang.implement.ConfigSpring;
import org.stth.pmi.barang.interfaces.InterBarang;
import org.stth.pmi.barang.ui.ConfigScene;

import com.ibm.icu.text.SimpleDateFormat;

import de.jensd.fx.fontawesome.AwesomeDude;
import de.jensd.fx.fontawesome.AwesomeIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class BarangController implements Initializable {

	@FXML
	private TableView<Barang> tableDataBarang;

	@FXML
	private TableColumn<Barang, String> idBarang;

	@FXML
	private TableColumn<Barang, String> namaBarangCol;

	@FXML
	private TableColumn<Barang, String> jenisBarangCol;

	@FXML
	private TableColumn<Barang, Date> tglMasukCol;

	@FXML
	private TableColumn<Barang, Date> tglExpCol;

	@FXML
	private TableColumn<Barang, String> jumlahBarangCol;

	@FXML
	private Label lblColId;

	@FXML
	private Label lblColNamaBarang;

	@FXML
	private Label lblColJenis;

	@FXML
	private Label lblColTglMasuk;

	@FXML
	private Label lblColTglExp;

	@FXML
	private Label lblColJumlah;

	@FXML
	private Label lblColAction;

	@FXML
	private TextField textCariBarang;

	@FXML
	private TextField textKode;

	@FXML
	private TextField textNamaBarang;

	@FXML
	private DatePicker tanggalMasukCal;

	@FXML
	private DatePicker tanggalExpCal;

	@FXML
	private ComboBox<Jenis> comboJenisBarang;

	@FXML
	private TextField textJumlahBarang;

	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnHapus;


	@FXML
	private Button btnSimpan;

	@FXML
	private Label lblBtnSimpan;

	@FXML
	private Label lblBtnHapus;

	@FXML
	private Label lblBtnRefresh;

	private ObservableList<Barang> listBarang;

	private boolean onSelect;

	private InterBarang crudBarang;
	private int jmlAwal;


	@FXML
	void onClickBtnRefresh(ActionEvent event) {
		clear();
		showBarang();
		autoId();

	}

	@FXML
	void onClickBtnHapus(ActionEvent event) {

		if (!onSelect) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Silahkan Pilih Barang yang di hapus!!!");
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
					"Yakin Ingin Hapus??");
			alert.initStyle(StageStyle.UTILITY);
			Optional<ButtonType> optionalBoolean = alert.showAndWait();
			if (optionalBoolean.get() == ButtonType.OK) {
				Barang barang = new Barang();
				barang.setId(textKode.getText());
				crudBarang.delete(barang);

				clear();
				showBarang();
				autoId();
			} else {

				clear();
				showBarang();
				autoId();
			}
		}
	}

	private void clear() {
		onSelect = false;
		textKode.clear();
		textCariBarang.clear();
		textJumlahBarang.clear();
		textNamaBarang.clear();
	}

	private void autoId() {
		try {
			Barang c = new Barang();
			crudBarang.autoId(c);
			textKode.setText(c.getId());
		} catch (Exception e) {
			textKode.setText("B.001");
		}
	}

	@FXML
	void onClickBtnSimpan(ActionEvent event) throws ParseException {
		LocalDate dateMasuk = tanggalMasukCal.getValue();
		LocalDate dateExp = tanggalExpCal.getValue();
		if (textNamaBarang.getText().isEmpty()) {
			ConfigScene.createDialog(Alert.AlertType.WARNING,
					"Nama Barang Tidak Boleh Kosong");
			textNamaBarang.requestFocus();
		} else if (textKode.getText().isEmpty()) {
			ConfigScene.createDialog(Alert.AlertType.WARNING,
					"Kode Barang Tidak Boleh Kosong");
			textKode.requestFocus();
		} else if (textKode.getText().isEmpty()) {
			ConfigScene.createDialog(Alert.AlertType.WARNING,
					"Kode Barang Tidak Boleh Kosong");
			textKode.requestFocus();
		} else if (dateMasuk == null) {
			ConfigScene.createDialog(Alert.AlertType.WARNING,
					"Tanggal Masuk Tidak Boleh Kosong");
			tanggalMasukCal.requestFocus();
		} else if (dateExp == null) {
			ConfigScene.createDialog(Alert.AlertType.WARNING,
					"Tanggal Expired Tidak Boleh Kosong");
			tanggalExpCal.requestFocus();
		} else {
			Barang c = new Barang();
			c.setId(textKode.getText());
			c.setNamaBarang(textNamaBarang.getText());
			c.setJenis(comboJenisBarang.getValue());
			c.setTanggalMasuk(java.sql.Date.valueOf(dateMasuk));
			c.setTanggalExp(java.sql.Date.valueOf(dateExp));
			c.setJumlah(Integer.parseInt(textJumlahBarang.getText()));
			c.setJumlahAwal(getJmlAwal()+Integer.parseInt(textJumlahBarang.getText()));
			crudBarang.createOrUpdate(c);

			clear();
			showBarang();
			autoId();
			textNamaBarang.requestFocus();
		}
	}

	@FXML
	void onKeyPressedCari(ActionEvent event) {
		listBarang = FXCollections.observableArrayList(crudBarang
				.search(textCariBarang.getText()));
		tableDataBarang.setItems(listBarang);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		onSelect = false;
		ApplicationContext cxt = ConfigSpring.getInstance()
				.getApplicationContext();
		crudBarang = (InterBarang) cxt.getBean(InterBarang.class);
		idBarang.setCellValueFactory(new PropertyValueFactory("idBarang"));
		namaBarangCol
				.setCellValueFactory(new PropertyValueFactory("namaBarang"));
		jenisBarangCol.setCellValueFactory(new PropertyValueFactory("jenis"));
		jumlahBarangCol.setCellValueFactory(new PropertyValueFactory("jumlah"));
		tglExpCol.setCellValueFactory(new PropertyValueFactory("tanggalExp"));
		tglMasukCol
				.setCellValueFactory(new PropertyValueFactory("tanggalMasuk"));
		ObservableList<Jenis> jenis = FXCollections.observableArrayList(Jenis
				.values());
		comboJenisBarang.setItems(jenis);
		AwesomeDude.setIcon(lblBtnHapus, AwesomeIcon.TIMES_CIRCLE_ALT, "15px");
		AwesomeDude.setIcon(lblBtnRefresh, AwesomeIcon.REFRESH, "15px");
		AwesomeDude.setIcon(lblBtnSimpan, AwesomeIcon.SAVE, "15px");
		showBarang();
		autoId();
		getDataBarang();
	}

	private void getDataBarang() {
		try {
			tableDataBarang.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					DateTimeFormatter formatter = DateTimeFormatter
							.ofPattern("yyyy-MM-dd");
					if (event.getClickCount() > 1) {
						if (tableDataBarang.getSelectionModel()
								.getSelectedIndex() >= 0) {
							Barang barang = tableDataBarang.getSelectionModel()
									.getSelectedItem();
							textKode.setText(barang.getId());
							textNamaBarang.setText(barang.getNamaBarang());
							textJumlahBarang.setText(String.valueOf(barang
									.getJumlah()));
							comboJenisBarang.setValue(barang.getJenis());
							setJmlAwal(barang.getJumlahAwal());
							String textDtMasuk = sdf.format(barang
									.getTanggalMasuk());
							LocalDate dateMasuk = LocalDate.parse(textDtMasuk,
									formatter);
							String textDtExp = sdf.format(barang
									.getTanggalExp());
							LocalDate dateexp = LocalDate.parse(textDtExp,
									formatter);
							tanggalMasukCal.setValue(dateMasuk);
							tanggalExpCal.setValue(dateexp);
							onSelect = true;
						}
					}
				}
			});

		} catch (Exception e) {

		}

	}

	private void showBarang() {
		if (listBarang == null) {
			listBarang = FXCollections.observableArrayList(crudBarang.getAll());
		} else {
			listBarang.clear();
			listBarang.addAll(crudBarang.getAll());
		}
		tableDataBarang.setItems(listBarang);
	}

	public int getJmlAwal() {
		return jmlAwal;
	}

	public void setJmlAwal(int jmlAwal) {
		this.jmlAwal = jmlAwal;
	}


}