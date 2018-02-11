package org.stth.pmi.barang.ui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.stth.pmi.barang.entitas.Barang;
import org.stth.pmi.barang.entitas.Jenis;
import org.stth.pmi.barang.implement.ConfigSpring;
import org.stth.pmi.barang.interfaces.InterBarang;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class LaporanController implements Initializable {

	@FXML
	private DatePicker calStat;

	@FXML
	private Button btnTableRefersh;

	@FXML
	private TableView<Barang> tableLaporanTHp;

	@FXML
	private TableColumn<Barang, String> namaBarangTHpCol;

	@FXML
	private TableColumn<Barang, String> jenisBarangTHpCol;

	@FXML
	private TableColumn<Barang, String> jumlahAwalTHpCol;

	@FXML
	private TableColumn<Barang, String> jumlahAkhirTHpCol;

	@FXML
	private TableColumn<Barang, String> kembaliTHpCol;

	@FXML
	private TableColumn<Barang, String> belumKembaliTHpCol;

	@FXML
	private TableColumn<Barang, String> jumlahProsTHpCol;

	@FXML
	private TableView<Barang> tableLaporanHp;

	@FXML
	private TableColumn<Barang, String> namaBarangHpCol;

	@FXML
	private TableColumn<Barang, String> jenisBarangHpCol;

	@FXML
	private TableColumn<Barang, String> jumlahAwalHpCol;

	@FXML
	private TableColumn<Barang, String> jumlahAkhirHpCol;

	@FXML
	private TableColumn<Barang, String> kembaliHpCol;

	@FXML
	private TableColumn<Barang, String> belumKembaliHpCol;

	@FXML
	private TableColumn<Barang, String> jumlahProsHpCol;
	
	@FXML
	private Button btnStatisticRefersh;

	@FXML
	private CategoryAxis xAxisHp;

	@FXML
	private CategoryAxis xAxisTHp;

	@FXML
	private BarChart<String, Integer> statisticBarangHp;

	@FXML
	private BarChart<String, Integer> statisticBarangTHp;

	private InterBarang crudBarang;

	@FXML
	void onClickBtnTableRefersh(ActionEvent event) {
		detailLaporan();
	}

	@FXML
	void onActioncalStat(ActionEvent event) {
		setDataStatisticHp(calStat.getValue());
		setDataStatisticTHp(calStat.getValue());
	}

	@FXML
	void onClickBtnStSRefersh(ActionEvent event) {
		if (calStat.getValue() != null) {
			setDataStatisticHp(calStat.getValue());
			setDataStatisticTHp(calStat.getValue());
		} else {
			setDataStatisticHp(LocalDate.now());
			setDataStatisticTHp(LocalDate.now());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ApplicationContext ctx = ConfigSpring.getInstance()
				.getApplicationContext();
		crudBarang = (InterBarang) ctx.getBean(InterBarang.class);
		namaBarangTHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"namaBarang"));
		jenisBarangTHpCol.setCellValueFactory(new PropertyValueFactory<>("jenis"));
		jumlahAwalTHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahAwal"));
		jumlahAkhirTHpCol
				.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
		kembaliTHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahStatusK"));
		belumKembaliTHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahStatusBK"));
		jumlahProsTHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahProses"));
		
		namaBarangHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"namaBarang"));
		jenisBarangHpCol.setCellValueFactory(new PropertyValueFactory<>("jenis"));
		jumlahAwalHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahAwal"));
		jumlahAkhirHpCol
				.setCellValueFactory(new PropertyValueFactory<>("jumlah"));
		kembaliHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahStatusK"));
		belumKembaliHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahStatusBK"));
		jumlahProsHpCol.setCellValueFactory(new PropertyValueFactory<>(
				"jumlahProses"));
		statisticBarangHp.setAnimated(true);
		statisticBarangTHp.setAnimated(true);

		setDataStatisticHp(LocalDate.now());
		setDataStatisticTHp(LocalDate.now());
		detailLaporan();
		detailLaporanTHp();
	}

	@SuppressWarnings("unused")
	private void detailLaporan() {
		ObservableList<Barang> listBarang = null;
		if (listBarang == null) {
			listBarang = FXCollections.observableArrayList(crudBarang.getByJenisAndDate(Jenis.HP, new Date()));
		} else {
			listBarang.clear();
			listBarang.addAll(crudBarang.getByJenisAndDate(Jenis.HP, new Date()));
		}
		tableLaporanHp.setItems(listBarang);
	}
	@SuppressWarnings("unused")
	private void detailLaporanTHp() {
		ObservableList<Barang> listBarang = null;
		if (listBarang == null) {
			listBarang = FXCollections.observableArrayList(crudBarang.getByJenisAndDate(Jenis.THP, new Date()));
		} else {
			listBarang.clear();
			listBarang.addAll(crudBarang.getByJenisAndDate(Jenis.THP, new Date()));
		}
		tableLaporanTHp.setItems(listBarang);
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private void setDataStatisticHp(LocalDate date) {

		ObservableList<Barang> listBarang = null;
		if (listBarang == null) {
			listBarang = FXCollections.observableArrayList(crudBarang
					.getByJenisAndDate(Jenis.HP, java.sql.Date.valueOf(date)));
		} else {
			listBarang.clear();
			listBarang.addAll(crudBarang.getByJenisAndDate(Jenis.HP,
					java.sql.Date.valueOf(date)));
		}
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Belum Kembali");
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Kembali");
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Proses");
		for (Barang barang : listBarang) {
			series1.getData().add(
					new XYChart.Data<>("BK", barang.getJumlahStatusBK()));
			series2.getData().add(
					new XYChart.Data<>("K", barang.getJumlahStatusK()));
			series3.getData().add(
					new XYChart.Data<>("Proses", barang.getJumlahProses()));
		}
		statisticBarangHp.getData().clear();
		statisticBarangHp.getData().addAll(series1, series2, series3);

	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private void setDataStatisticTHp(LocalDate date) {
		ObservableList<Barang> listBarang = null;
		if (listBarang == null) {
			listBarang = FXCollections.observableArrayList(crudBarang
					.getByJenisAndDate(Jenis.THP, java.sql.Date.valueOf(date)));
		} else {
			listBarang.clear();
			listBarang.addAll(crudBarang.getByJenisAndDate(Jenis.THP,
					java.sql.Date.valueOf(date)));
		}
		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Belum Kembali");
		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Kembali");
		XYChart.Series series3 = new XYChart.Series();
		series3.setName("Proses");
		int i = 0;
		for (Barang barang : listBarang) {
			series1.getData().add(
					new XYChart.Data<>("BK", barang.getJumlahStatusBK()));
			series2.getData().add(
					new XYChart.Data<>("K", barang.getJumlahStatusK()));
			series3.getData().add(
					new XYChart.Data<>("Proses", barang.getJumlahProses()));
			i += barang.getJumlahProses();
			System.out.println(i);
		}
		statisticBarangTHp.getData().clear();
		statisticBarangTHp.getData().addAll(series1, series2, series3);
	}
}
