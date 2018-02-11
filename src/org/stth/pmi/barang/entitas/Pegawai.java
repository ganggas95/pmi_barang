package org.stth.pmi.barang.entitas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table (name="tb_pegawai")
public class Pegawai {
	@Id
	@Column(name="id_Pegawai")
	private String idPegawai;

	@Column(name="nip", unique=true)
	private String nip;

	@Column(name="nama")
	private String nama;

	@Enumerated(EnumType.STRING)
	private JenisKelamin jk;

	@Enumerated(EnumType.STRING)
	private Agama agama;

	@Temporal (TemporalType.DATE)
	@Column(name="tanggalLahir")
	private Date tanggalLahir;

	@Column(name="tempatLahir")
	private String tempatLahir;

	@Column(name="alamat", length=255)
	private String alamat;


	@OneToMany(cascade=CascadeType.ALL, mappedBy="pegawai")
	private List<LaporanBarang> riwayatPetugas = new ArrayList<LaporanBarang>();

	public String getIdPegawai() {
		return idPegawai;
	}
	public void setIdPegawai(String idPegawai) {
		this.idPegawai = idPegawai;
	}
	public String getNip() {
		return nip;
	}
	public void setNip(String nip) {
		this.nip = nip;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public JenisKelamin getJk() {
		return jk;
	}
	public void setJk(JenisKelamin jk) {
		this.jk = jk;
	}
	public Agama getAgama() {
		return agama;
	}
	public void setAgama(Agama agama) {
		this.agama = agama;
	}
	public Date getTanggalLahir() {
		return tanggalLahir;
	}
	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}
	public String getTempatLahir() {
		return tempatLahir;
	}
	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}
	public List<LaporanBarang> getRiwayatPetugas() {
		return riwayatPetugas;
	}
	public void setRiwayatPetugas(List<LaporanBarang> riwayatPetugas) {
		this.riwayatPetugas = riwayatPetugas;
	}
}
