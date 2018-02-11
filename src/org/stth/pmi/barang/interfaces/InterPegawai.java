package org.stth.pmi.barang.interfaces;

import java.util.List;

import org.stth.pmi.barang.entitas.Pegawai;

public interface InterPegawai {
	void createOrUpdate(Pegawai p);
	void delete(Pegawai p);
	void autoId(Pegawai p);
	List<Pegawai> getAll();
	List<Pegawai> search(String a);
}
