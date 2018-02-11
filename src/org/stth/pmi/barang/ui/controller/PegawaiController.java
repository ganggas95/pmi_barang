package org.stth.pmi.barang.ui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.stth.pmi.barang.entitas.Agama;
import org.stth.pmi.barang.entitas.JenisKelamin;
import org.stth.pmi.barang.entitas.Pegawai;
import org.stth.pmi.barang.implement.ConfigSpring;
import org.stth.pmi.barang.interfaces.InterPegawai;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class PegawaiController implements Initializable {
	@FXML
	private TableView<Pegawai> tablePegawai;

	@FXML
	private TableColumn<Pegawai, String> idPegawaiCol;

	@FXML
	private TableColumn<Pegawai, String> nipCol;

	@FXML
	private TableColumn<Pegawai, String> namaCol;

	@FXML
	private TableColumn<Pegawai, String> jenisKelaminCol;

	@FXML
	private TableColumn<Pegawai, String> agamaCol;

	@FXML
	private TableColumn<Pegawai, String> tmpLahirCol;

	@FXML
	private TableColumn<Pegawai, Date> tglLahirCol;

	@FXML
	private TableColumn<Pegawai, String> alamatCol;

	@FXML
	private TextField textCari;

	@FXML
	private TextField txtIdPegawai;

	@FXML
	private TextField txtNim;

	@FXML
	private TextField txtNama;

	@FXML
	private ComboBox<JenisKelamin> comboJenisKelamin;

	@FXML
	private ComboBox<Agama> comboAgama;

	@FXML
	private TextField txtTempatLahir;

	@FXML
	private DatePicker tglLahirCal;

	@FXML
	private TextArea textAlamat;

	@FXML
	private Button btnSimpan;

	@FXML
	private Button btnHapus;

	@FXML
	private Button btnRefresh;
	
	@FXML
	private Label lblBtnSimpan;
	
	@FXML
	private Label lblBtnHapus;
	
	@FXML
	private Label lblBtnRefresh;

	private ObservableList<Pegawai> listPegawai;
	private boolean onSelect;
	private InterPegawai crudPegawai;

	@FXML
	void onClickBtnHapus(ActionEvent event) {
		if(!onSelect){
			ConfigScene.createDialog(AlertType.WARNING, "Pilih data pegawai yang ingin di hapus");
			tablePegawai.requestFocus();
		}else{
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
					"Yakin Ingin Hapus??");
			alert.initStyle(StageStyle.UTILITY);
			Optional<ButtonType> optional = alert.showAndWait();
			if(optional.get()==ButtonType.OK){
				Pegawai pegawai = new Pegawai();
				pegawai.setIdPegawai(txtIdPegawai.getText());
				crudPegawai.delete(pegawai);
				clear();
				showPegawai();
				autoId();
			}
		}
	}

	@FXML
	void onClickRefresh(ActionEvent event) {
		clear();
		showPegawai();
		autoId();
	}

	private void clear() {
		onSelect = false;
		textAlamat.clear();
		txtIdPegawai.clear();
		txtNama.clear();
		txtNim.clear();
		txtTempatLahir.clear();
	}

	private void autoId() {
		try {
			Pegawai pegawai = new Pegawai();
			crudPegawai.autoId(pegawai);
			txtIdPegawai.setText(pegawai.getIdPegawai());
		} catch (Exception e) {
			txtIdPegawai.setText("P.001");
		}
	}

	@FXML
	void onClickSimpan(ActionEvent event) {
		LocalDate localDate = tglLahirCal.getValue();
		if (txtNama.getText().isEmpty()) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Nama Pegawai tidak boleh kosong!!");
			txtNama.requestFocus();
		} else if (txtIdPegawai.getText().isEmpty()) {
			ConfigScene.createDialog(AlertType.WARNING,
					"ID Pegawai tidak boleh kosong!!");
			txtIdPegawai.requestFocus();
		} else if (txtNim.getText().isEmpty()) {
			ConfigScene.createDialog(AlertType.WARNING,
					"NIP Pegawai tidak boleh kosong!!");
			txtNim.requestFocus();
		} else if (localDate == null) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Tanggal Pegawai tidak boleh kosong!!");
			tglLahirCal.requestFocus();
		} else if (txtTempatLahir.getText().isEmpty()) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Tanggal Pegawai tidak boleh kosong!!");
			txtTempatLahir.requestFocus();
		} else if (textAlamat.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.CONFIRMATION,
					"Yakin Tidak masukkan alamat?");
			alert.initStyle(StageStyle.UTILITY);
			Optional<ButtonType> optional = alert.showAndWait();
			if (optional.get() == ButtonType.OK) {
				textAlamat.setText("-");
				textAlamat.requestFocus();
			} else {
				textAlamat.setText("-");
				textAlamat.requestFocus();
			}

		} else {

			savePegawaiData(txtIdPegawai.getText(), txtNim.getText(),
					txtNama.getText(), comboJenisKelamin.getValue(),
					comboAgama.getValue(), txtTempatLahir.getText(),
					textAlamat.getText(), java.sql.Date.valueOf(localDate));
			clear();
			showPegawai();
			autoId();
		}
	}

	@FXML
	void onKeyPressedCariPegawai(ActionEvent event) {
		listPegawai = FXCollections.observableArrayList(crudPegawai
				.search(textCari.getText()));
		tablePegawai.setItems(listPegawai);
	}

	private void savePegawaiData(String id, String nip, String nama,
			JenisKelamin jk, Agama agama, String tempatLahir, String alamat,
			Date tanggalLahir) {
		Pegawai pegawai = new Pegawai();
		pegawai.setIdPegawai(id);
		pegawai.setNip(nip);
		pegawai.setNama(nama);
		pegawai.setJk(jk);
		pegawai.setAgama(agama);
		pegawai.setTempatLahir(tempatLahir);
		pegawai.setTanggalLahir(tanggalLahir);
		pegawai.setAlamat(alamat);
		crudPegawai.createOrUpdate(pegawai);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		onSelect = false;
		ApplicationContext apContext = ConfigSpring.getInstance()
				.getApplicationContext();
		crudPegawai = (InterPegawai) apContext.getBean(InterPegawai.class);

		idPegawaiCol.setCellValueFactory(new PropertyValueFactory("idPegawai"));
		nipCol.setCellValueFactory(new PropertyValueFactory("nip"));
		namaCol.setCellValueFactory(new PropertyValueFactory("nama"));
		jenisKelaminCol.setCellValueFactory(new PropertyValueFactory("jk"));
		agamaCol.setCellValueFactory(new PropertyValueFactory("agama"));
		tmpLahirCol
				.setCellValueFactory(new PropertyValueFactory("tempatLahir"));
		tglLahirCol
				.setCellValueFactory(new PropertyValueFactory("tanggalLahir"));
		alamatCol.setCellValueFactory(new PropertyValueFactory("alamat"));

		ObservableList<Agama> agamaList = FXCollections
				.observableArrayList(Agama.values());
		comboAgama.setItems(agamaList);
		ObservableList<JenisKelamin> jenisKelaminList = FXCollections
				.observableArrayList(JenisKelamin.values());
		comboJenisKelamin.setItems(jenisKelaminList);

		tglLahirCal.setPromptText("dd/MM/yyyy");
		AwesomeDude.setIcon(lblBtnHapus, AwesomeIcon.TIMES_CIRCLE_ALT, "15px");
		AwesomeDude.setIcon(lblBtnRefresh, AwesomeIcon.REFRESH, "15px");
		AwesomeDude.setIcon(lblBtnSimpan, AwesomeIcon.SAVE, "15px");
		showPegawai();
		autoId();
		getDataPegawai();
	}

	private void getDataPegawai() {
		try {
			tablePegawai.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					DateTimeFormatter formatter = DateTimeFormatter
							.ofPattern("yyyy-MM-dd");
					if (arg0.getClickCount() > 1) {
						if (tablePegawai.getSelectionModel().getSelectedIndex() >= 0) {
							Pegawai pegawai = tablePegawai.getSelectionModel()
									.getSelectedItem();
							txtIdPegawai.setText(pegawai.getIdPegawai());
							txtNim.setText(pegawai.getNip());
							txtNama.setText(pegawai.getNama());
							comboJenisKelamin.setValue(pegawai.getJk());
							comboAgama.setValue(pegawai.getAgama());
							String txtTgl = sdf.format(pegawai
									.getTanggalLahir());
							LocalDate tmpTglLahir = LocalDate.parse(txtTgl,
									formatter);
							tglLahirCal.setValue(tmpTglLahir);
							txtTempatLahir.setText(pegawai.getTempatLahir());
							textAlamat.setText(pegawai.getAlamat());
							onSelect = true;
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showPegawai() {
		// TODO Auto-generated method stub
		if (listPegawai == null) {
			listPegawai = FXCollections.observableArrayList(crudPegawai
					.getAll());
		} else {
			listPegawai.clear();
			listPegawai.addAll(crudPegawai.getAll());
		}
		tablePegawai.setItems(listPegawai);
	}

}
