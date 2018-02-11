package org.stth.pmi.barang.interfaces;

import java.util.Date;
import java.util.List;

import org.stth.pmi.barang.entitas.LaporanBarang;

public interface InterLaporanBarang {
	void createOrUpdate(LaporanBarang lb);
	void delete(LaporanBarang lb);
	List<LaporanBarang> getByDate(Date date);
	List<LaporanBarang> getAll();
	List<LaporanBarang> search(String a);
}
