package org.stth.pmi.barang.ui.controller;

import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.stth.pmi.barang.entitas.Barang;
import org.stth.pmi.barang.entitas.Jenis;
import org.stth.pmi.barang.entitas.LaporanBarang;
import org.stth.pmi.barang.entitas.Pegawai;
import org.stth.pmi.barang.entitas.Status;
import org.stth.pmi.barang.implement.ConfigSpring;
import org.stth.pmi.barang.interfaces.InterBarang;
import org.stth.pmi.barang.interfaces.InterLaporanBarang;
import org.stth.pmi.barang.interfaces.InterPegawai;
import org.stth.pmi.barang.ui.ConfigScene;

import com.ibm.icu.text.SimpleDateFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class ProsesController implements Initializable {

	@FXML
	private TableView<LaporanBarang> tableProses;

	@FXML
	private TableColumn<LaporanBarang, String> idBarangCol;

	@FXML
	private TableColumn<LaporanBarang, String> namaBarangCol;

	@FXML
	private TableColumn<LaporanBarang, String> jenisBarangCol;

	@FXML
	private TableColumn<LaporanBarang, Date> tanggalCol;

	@FXML
	private TableColumn<LaporanBarang, String> jumlahKeluarCol;

	@FXML
	private TableColumn<LaporanBarang, String> jumlahKembaliCol;

	@FXML
	private TableColumn<LaporanBarang, String> tujuanCol;

	@FXML
	private TableColumn<LaporanBarang, String> petugasCol;

	@FXML
	private TableColumn<LaporanBarang, String> statusCol;

	@FXML
	private TextField cariDataBarang;

	@FXML
	private ComboBox<Barang> comboNamaBarang;

	@FXML
	private TextField textKodeBarang;

	@FXML
	private ComboBox<Jenis> comboJenis;

	@FXML
	private TextField textJumlahBarang;

	@FXML
	private ComboBox<Pegawai> comboPetugas;
	@FXML
	private Button btnTambah;

	@FXML
	private Button btnBatal;

	@FXML
	private TextField textNamaBarangKembali;

	@FXML
	private TextField textKodeBarangKembali;

	@FXML
	private TextField textJumlahSisiaBarang;

	@FXML
	private TextField textJumlahBarangKeluar;

	@FXML
	private TextField textJumlahBarangKembali;

	@FXML
	private ComboBox<Status> comboStatusBarang;

	@FXML
	private Button btnSimpan;

	@FXML
	private Button btnBatalKembali;

	@FXML
	private TextField textTujuan;

	@FXML
	private Button btnRefresh;

	@FXML
	private TextField textSisaBarang;

	private ObservableList<Barang> listBarang;
	private ObservableList<Pegawai> listPegawai;
	private InterBarang crudBarang;
	private InterPegawai crudPegawai;
	private InterLaporanBarang crudLaporanBarang;
	private ObservableList<LaporanBarang> listLaporanBarang;

	private boolean isSelected = false;

	@FXML
	void onClickBtnBatal(ActionEvent event) throws ParseException {
		clear();
		showData();
		showBarang();
		showPetugas();
	}

	@FXML
	void onClickBtnRefresh(ActionEvent event) throws ParseException {
		clear();
		showData();
		showBarang();
		showPetugas();
	}

	@FXML
	void onClickBtnBatalKembali(ActionEvent event) {
		clear();
		showBarang();
		try {
			showData();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showPetugas();
	}

	@FXML
	void onClickSimpan(ActionEvent event) throws ParseException {
		prosesPengembalianBarang();

	}

	@FXML
	void onClickBtnTambah(ActionEvent event) {
		try {
			prosesPengambilanBarang();

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void onKeyPressedCariData(ActionEvent event) {
		listLaporanBarang = FXCollections.observableArrayList(crudLaporanBarang
				.search(cariDataBarang.getText()));
		tableProses.setItems(listLaporanBarang);
	}

	@FXML
	void onClickComboBarang(ActionEvent event) {

		searchWithNama(comboNamaBarang.getSelectionModel().getSelectedItem()
				.getNamaBarang());

	}

	private void searchWithNama(String namaBarang) {
		listBarang = FXCollections.observableArrayList(crudBarang
				.search(namaBarang));
		for (Barang barang : listBarang) {
			textKodeBarang.setText(barang.getIdBarang());
			comboJenis.setValue(barang.getJenis());
			textSisaBarang.setText(String.valueOf(barang.getJumlah()));

		}
	}

	private void clear() {
		textJumlahBarang.clear();
		textJumlahBarangKeluar.clear();
		textJumlahBarangKembali.clear();
		textJumlahSisiaBarang.clear();
		textKodeBarang.clear();
		textKodeBarangKembali.clear();
		textNamaBarangKembali.clear();
		textSisaBarang.clear();
		textTujuan.clear();
		isSelected = false;
	}

	private void prosesPengembalianBarang() throws ParseException {
		LaporanBarang lb = tableProses.getSelectionModel().getSelectedItem();
		if (!isSelected == true) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Pilih Barang yang akan di proses!!!");
			tableProses.requestFocus();
		} else {
			if (lb.getStatus() == Status.Kembali) {
				ConfigScene.createDialog(AlertType.WARNING,
						"Barang yang sudah dikembalikan tidak bisa diubah!!!");
				tableProses.requestFocus();
			} else {
				if (lb.getBarang().getJenis() == Jenis.THP) {
					if (textJumlahBarangKembali.getText().isEmpty()) {
						ConfigScene.createDialog(AlertType.WARNING,
								"Jumlah Barang Kembali kosong!!");
						textJumlahBarangKembali.requestFocus();
					} else if (comboStatusBarang.getValue() == Status.Belum_Kembali
							&& comboStatusBarang.getValue() == null) {
						ConfigScene.createDialog(AlertType.WARNING,
								"Tidak ada perubahan dalam proses ini!!");
						comboStatusBarang.requestFocus();
					} else {
						int jumlahBarangKembali = Integer
								.parseInt(textJumlahBarangKembali.getText());
						int sisaBarang = lb.getBarang().getJumlah();
						int jumlahBarangKeluar = lb.getJmlKeluar();
						if (jumlahBarangKeluar < jumlahBarangKembali) {
							ConfigScene
									.createDialog(
											AlertType.WARNING,
											"Jumlah Barang Kembali melebihi jumlah barang keluar! \n Jumlah Barang keluar adalah "
													+ jumlahBarangKeluar);
						} else {
							sisaBarang = sisaBarang + jumlahBarangKembali;
							Barang barang = lb.getBarang();
							barang.setJumlah(sisaBarang);
							crudBarang.createOrUpdate(barang);
							lb.setJmlKembali(jumlahBarangKembali);
							lb.setStatus(Status.Kembali);
							crudLaporanBarang.createOrUpdate(lb);
							clear();
							showData();
							showBarang();
						}
					}
				} else if (lb.getBarang().getJenis() == Jenis.HP) {
					Barang barang = lb.getBarang();
					barang.setJumlah(lb.getBarang().getJumlah()
							- lb.getJmlKeluar());
					crudBarang.createOrUpdate(barang);
					lb.setStatus(Status.Kembali);
					crudLaporanBarang.createOrUpdate(lb);
					clear();
					showData();
					showBarang();
				}
			}
		}
	}

	private void prosesPengambilanBarang() throws ParseException {
		LaporanBarang lb = new LaporanBarang();

		if (textJumlahBarang.getText().isEmpty()) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Jumlah Barang yang diambil kosong.");
			textJumlahBarang.requestFocus();
		} else if (comboPetugas.getValue() == null) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Siapa yang bertanggung jawab atas keluarnya barang");
			comboPetugas.requestFocus();
		} else if (textTujuan.getText().isEmpty()) {
			ConfigScene.createDialog(AlertType.WARNING,
					"Tujuan kosong.. Silahkan Isi Tujuan!!");
			textTujuan.requestFocus();
		} else {
			int jumlahBarang = Integer.parseInt(textSisaBarang.getText());
			int jumlahPengambilan = Integer
					.parseInt(textJumlahBarang.getText());
			if(jumlahBarang<1){
				ConfigScene.createDialog(AlertType.ERROR,
						"Stock Barang Habis!!!!");
				textJumlahSisiaBarang.requestFocus();
			}else{
				if (jumlahPengambilan <= jumlahBarang) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					lb.setBarang(comboNamaBarang.getValue());
					lb.setStatus(Status.Belum_Kembali);
					lb.setTanggal(sdf.parse(sdf.format(new Date())));
					lb.setJmlKeluar(Integer.parseInt(textJumlahBarang.getText()));
					lb.setPegawai(comboPetugas.getValue());
					lb.setTujuan(textTujuan.getText());
					Barang barang = lb.getBarang();
					barang.setJumlah(jumlahBarang - jumlahPengambilan);
					crudBarang.createOrUpdate(barang);
					crudLaporanBarang.createOrUpdate(lb);
					clear();
					showData();
					showBarang();
				} else {
					ConfigScene.createDialog(AlertType.ERROR,
							"Jumlah Pengambilan Melebihi jumlah barang");
					textJumlahBarang.requestFocus();
				}
			}

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		isSelected = false;
		ApplicationContext ctx = ConfigSpring.getInstance()
				.getApplicationContext();
		crudBarang = (InterBarang) ctx.getBean(InterBarang.class);
		crudPegawai = (InterPegawai) ctx.getBean(InterPegawai.class);
		crudLaporanBarang = (InterLaporanBarang) ctx
				.getBean(InterLaporanBarang.class);
		namaBarangCol.setCellValueFactory(new PropertyValueFactory<>(
				"namaBarang"));
		jenisBarangCol.setCellValueFactory(new PropertyValueFactory<>(
				"jenisBarang"));
		petugasCol
				.setCellValueFactory(new PropertyValueFactory<>("namaPetugas"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
		jumlahKeluarCol.setCellValueFactory(new PropertyValueFactory<>(
				"jmlKeluar"));
		jumlahKembaliCol.setCellValueFactory(new PropertyValueFactory<>(
				"jmlKembali"));
		tujuanCol.setCellValueFactory(new PropertyValueFactory<>("tujuan"));
		ObservableList<Jenis> jenisBarang = FXCollections
				.observableArrayList(Jenis.values());
		ObservableList<Status> statusBarang = FXCollections
				.observableArrayList(Status.values());
		comboJenis.setItems(jenisBarang);
		comboStatusBarang.setItems(statusBarang);
		try {
			showData();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showBarang();
		showPetugas();
		getDataFromTable();
	}

	private void getDataFromTable() {
		// TODO Auto-generated method stub
		tableProses.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if (event.getClickCount() > 1) {
					if (tableProses.getSelectionModel().getSelectedIndex() >= 0) {
						LaporanBarang lb = tableProses.getSelectionModel()
								.getSelectedItem();
						textNamaBarangKembali.setText(lb.getNamaBarang());
						textKodeBarangKembali.setText(lb.getBarang().getId());
						textJumlahSisiaBarang.setText(String.valueOf(lb
								.getBarang().getJumlah()));
						textJumlahBarangKeluar.setText(String.valueOf(lb
								.getJmlKeluar()));
						comboStatusBarang.setValue(lb.getStatus());
						isSelected = true;
					}
				}
			}
		});
	}

	private void showData() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		if (listLaporanBarang == null) {
			listLaporanBarang = FXCollections
					.observableArrayList(crudLaporanBarang.getByDate(sdf
							.parse(sdf.format(new Date()))));
		} else {
			listLaporanBarang.clear();
			listLaporanBarang.addAll(crudLaporanBarang.getByDate(sdf.parse(sdf
					.format(new Date()))));
		}
		tableProses.setItems(listLaporanBarang);
	}

	private void showBarang() {
		if (listBarang == null) {
			listBarang = FXCollections.observableArrayList(crudBarang.getAll());
		} else {
			listBarang.clear();
			listBarang.addAll(crudBarang.getAll());
		}
		comboNamaBarang.setItems(listBarang);
		comboNamaBarang.setConverter(new StringConverter<Barang>() {

			@Override
			public String toString(Barang arg0) {
				// TODO Auto-generated method stub
				return arg0.getNamaBarang();
			}

			@Override
			public Barang fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	private void showPetugas() {
		if (listPegawai == null) {
			listPegawai = FXCollections.observableArrayList(crudPegawai
					.getAll());
		} else {
			listPegawai.clear();
			listPegawai.addAll(crudPegawai.getAll());
		}
		comboPetugas.setItems(listPegawai);
		comboPetugas.setConverter(new StringConverter<Pegawai>() {

			@Override
			public String toString(Pegawai arg0) {
				// TODO Auto-generated method stub
				return arg0.getNama();
			}

			@Override
			public Pegawai fromString(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

}
