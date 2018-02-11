package org.stth.pmi.barang.interfaces;

import java.util.Date;
import java.util.List;

import org.stth.pmi.barang.entitas.Barang;
import org.stth.pmi.barang.entitas.Jenis;

public interface InterBarang {
	void createOrUpdate(Barang b);

	void delete(Barang b);

	void autoId(Barang b);

	List<Barang> getByJenisAndDate(Jenis jenis, Date date);

	List<Barang> getAll();

	List<Barang> search(String a);
}
