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

@Entity
@Table(name = "tb_barang")
public class Barang {
	@Id
	@Column(name = "idBarang")
	private String idBarang;

	@Column(name = "nama_barang")
	private String namaBarang;

	@Column(name = "tanggal_masuk")
	private Date tanggalMasuk;

	@Column(name = "tanggal_exp")
	private Date tanggalExp;

	@Enumerated(EnumType.STRING)
	private Jenis jenis;

	@Column(name = "jumlah")
	private int jumlah;

	private int jumlahStatusK;
	private int jumlahStatusBK;
	private int jumlahProses;
	private int jumlahProsesHp;
	private int jumlahProsesTHp;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "barang")
	private List<LaporanBarang> listLaporanBarangs = new ArrayList<LaporanBarang>();

	@Column(name = "jumlahAwal")
	private int jumlahAwal;

	public String getId() {
		return idBarang;
	}

	public void setId(String id) {
		this.idBarang = id;
	}

	public String getNamaBarang() {
		return namaBarang;
	}

	public void setNamaBarang(String namaBarang) {
		this.namaBarang = namaBarang;
	}

	public Jenis getJenis() {
		return jenis;
	}

	public void setJenis(Jenis jenis) {
		this.jenis = jenis;
	}

	public int getJumlah() {
		return jumlah;
	}

	public void setJumlah(int jumlah) {
		this.jumlah = jumlah;
	}

	public String getIdBarang() {
		return idBarang;
	}

	public void setIdBarang(String idBarang) {
		this.idBarang = idBarang;
	}

	public List<LaporanBarang> getListLaporanBarangs() {
		return listLaporanBarangs;
	}

	public void setListLaporanBarangs(List<LaporanBarang> listLaporanBarangs) {
		this.listLaporanBarangs = listLaporanBarangs;
	}

	public Date getTanggalMasuk() {
		return tanggalMasuk;
	}

	public void setTanggalMasuk(Date tanggalMasuk) {
		this.tanggalMasuk = tanggalMasuk;
	}

	public Date getTanggalExp() {
		return tanggalExp;
	}

	public void setTanggalExp(Date tanggalExp) {
		this.tanggalExp = tanggalExp;
	}

	public int getJumlahAwal() {
		return jumlahAwal;
	}

	public void setJumlahAwal(int jumlahAwal) {
		this.jumlahAwal = jumlahAwal;
	}

	public int getJumlahProses() {
		int temp = 0;
		for(LaporanBarang lb : getListLaporanBarangs()){
			if(lb.getBarang().jenis==Jenis.HP){
				temp = getJumlahProsesHp();
			}else if(lb.getBarang().jenis==Jenis.THP){
				temp = getJumlahProsesTHp();
			}
		}
		jumlahProses = temp;
		return jumlahProses;
	}

	public void setJumlahProses(int jumlahProses) {
		this.jumlahProses = jumlahProses;
	}

	public int getJumlahStatusK() {
		List<String> temp1 = new ArrayList<>();
		List<String> temp2 = new ArrayList<>();
		for (LaporanBarang lb : getListLaporanBarangs()) {
			if(lb.getStatus()==Status.Belum_Kembali){
				temp1.add(String.valueOf(lb.getStatus()==Status.Belum_Kembali));
			}else{
				temp2.add(String.valueOf(lb.getStatus()==Status.Kembali));
			}
		}
		jumlahStatusK = temp2.size();
		return jumlahStatusK;
	}

	public void setJumlahStatusK(int jumlahStatusK) {
		this.jumlahStatusK = jumlahStatusK;
	}

	public int getJumlahStatusBK() {
		List<String> temp1 = new ArrayList<>();
		List<String> temp2 = new ArrayList<>();
		for (LaporanBarang lb : getListLaporanBarangs()) {
			if(lb.getStatus()==Status.Belum_Kembali){
				temp1.add(String.valueOf(lb.getStatus()==Status.Belum_Kembali));
			}else{
				temp2.add(String.valueOf(lb.getStatus()==Status.Kembali));
			}
		}
		jumlahStatusBK = temp1.size();
		return jumlahStatusBK;
	}

	public void setJumlahStatusBK(int jumlahStatusBK) {
		this.jumlahStatusBK = jumlahStatusBK;
	}

	public int getJumlahProsesTHp() {
		List<String> temp = new ArrayList<>();
		for (LaporanBarang lb : getListLaporanBarangs()) {
			temp.add(String.valueOf(lb.getJenisBarang() == Jenis.THP));
		}
		jumlahProsesTHp = temp.size();
		return jumlahProsesTHp;
	}

	public void setJumlahProsesTHp(int jumlahProsesTHp) {
		this.jumlahProsesTHp = jumlahProsesTHp;
	}

	public int getJumlahProsesHp() {
		List<String> temp = new ArrayList<>();
		for (LaporanBarang lb : getListLaporanBarangs()) {
			temp.add(String.valueOf(lb.getJenisBarang() == Jenis.HP));
		}
		jumlahProsesHp = temp.size();
		return jumlahProsesHp;
	}

	public void setJumlahProsesHp(int jumlahProsesHp) {
		this.jumlahProsesHp = jumlahProsesHp;
	}
}
