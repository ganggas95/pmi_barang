package org.stth.pmi.barang.entitas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_laporanbarang")
public class LaporanBarang {
	@Id @GeneratedValue
	@Column(name = "id_laporan")
	private Integer idLaporan;

	@ManyToOne
	@JoinColumn(name = "idBarang")
	private Barang barang;

	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn(name = "id_pegawai")
	private Pegawai pegawai;

	@Column(name = "jmlKeluar")
	private int jmlKeluar;

	@Column(name = "jmlKembali")
	private int jmlKembali;

	@Column(name = "tanggal")
	private Date tanggal;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "tujuan")
	private String tujuan;

	private String namaBarang;

	private Jenis jenisBarang;

	private int jumlahBarang;
	private int jumlahAwal;

	private String namaPetugas;

	public Integer getIdLaporan() {
		return idLaporan;
	}

	public void setIdLaporan(Integer idLaporan) {
		this.idLaporan = idLaporan;
	}

	public int getJmlKeluar() {
		return jmlKeluar;
	}

	public void setJmlKeluar(int jmlKeluar) {
		this.jmlKeluar = jmlKeluar;
	}

	public int getJmlKembali() {
		return jmlKembali;
	}

	public void setJmlKembali(int jmlKembali) {
		this.jmlKembali = jmlKembali;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public String getTujuan() {
		return tujuan;
	}

	public void setTujuan(String tujuan) {
		this.tujuan = tujuan;
	}

	public Barang getBarang() {
		return barang;
	}

	public void setBarang(Barang barang) {
		this.barang = barang;
	}

	public Pegawai getPegawai() {
		return pegawai;
	}

	public void setPegawai(Pegawai pegawai) {
		this.pegawai = pegawai;
	}

	public String getNamaBarang() {
		namaBarang = barang.getNamaBarang();
		return namaBarang;
	}

	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}

	public Jenis getJenisBarang() {
		jenisBarang = barang.getJenis();
		return jenisBarang;
	}

	public void setJenisBarang(Jenis jenisBarang) {
		this.jenisBarang = jenisBarang;
	}

	public int getJumlahBarang() {
		jumlahBarang = barang.getJumlah();
		return jumlahBarang;
	}

	public void setJumlahBarang(int jumlahBarang) {
		this.jumlahBarang = jumlahBarang;
	}
	public int getJumlahAwal() {
		jumlahAwal = barang.getJumlahAwal();
		return jumlahAwal;
	}

	public void setJumlahAwal(int jumlahAwal) {
		this.jumlahAwal = jumlahAwal;
	}
	public String getNamaPetugas() {
		namaPetugas = pegawai.getNama();
		return namaPetugas;
	}

	public void setNamaPetugas(String namaPetugas) {
		this.namaPetugas = namaPetugas;
	}
}
