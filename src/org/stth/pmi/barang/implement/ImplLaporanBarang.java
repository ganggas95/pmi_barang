package org.stth.pmi.barang.implement;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stth.pmi.barang.entitas.LaporanBarang;
import org.stth.pmi.barang.interfaces.InterLaporanBarang;

import com.ibm.icu.text.SimpleDateFormat;

@Repository
public class ImplLaporanBarang implements InterLaporanBarang {
	@Autowired
	ConfigSession cf;

	@Override
	public void createOrUpdate(LaporanBarang lb) {
		// TODO Auto-generated method stub
		if (lb.getIdLaporan() == null) {
			cf.create(lb);
		} else {
			cf.update(lb);
		}
	}

	@Override
	public void delete(LaporanBarang lb) {
		// TODO Auto-generated method stub
		cf.delete(lb);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaporanBarang> getAll() {
		// TODO Auto-generated method stub
		return cf.getSf().openSession().createCriteria(LaporanBarang.class)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaporanBarang> search(String a) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Criteria cr = cf.getSf().openSession()
				.createCriteria(LaporanBarang.class);
		try {
			cr.add(Restrictions.and(
					Restrictions.like("namaBarang", a, MatchMode.ANYWHERE),
					Restrictions.eq("tanggal", sdf.parse(sdf.format(new Date())))));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LaporanBarang> getByDate(Date date) {
		Criteria cr = cf.getSf().openSession()
				.createCriteria(LaporanBarang.class);
		cr.add(Restrictions.eq("tanggal", date));
		return cr.list();
	}


}
