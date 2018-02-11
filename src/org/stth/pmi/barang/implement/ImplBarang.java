package org.stth.pmi.barang.implement;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stth.pmi.barang.entitas.Barang;
import org.stth.pmi.barang.entitas.Jenis;
import org.stth.pmi.barang.interfaces.InterBarang;

@Repository
public class ImplBarang implements InterBarang {
	@Autowired
	ConfigSession cf;

	@Override
	public void createOrUpdate(Barang b) {
		// TODO Auto-generated method stub
		if (b.getId() == null) {
			cf.create(b);
		} else {
			cf.update(b);
		}

	}

	@Override
	public void delete(Barang b) {
		// TODO Auto-generated method stub
		cf.delete(b);
	}

	@Override
	public void autoId(Barang b) {
		// TODO Auto-generated method stub
		try {
			Criteria cr = cf.getSf().openSession().createCriteria(Barang.class);
			cr.setProjection(Projections.max("idBarang"));
			Object o = cr.uniqueResult();
			String auto = ""
					+ (Integer.parseInt(o.toString().substring(2)) + 1);
			String nol = "";
			if (auto.length() == 1) {
				nol = "00";
			} else if (auto.length() == 2) {
				nol = "0";
			} else if (auto.length() == 3) {
				nol = "";
			}
			b.setId("B." + nol + auto);
		} catch (NumberFormatException | HibernateException e) {

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Barang> getAll() {
		// TODO Auto-generated method stub
		return cf.getSf().openSession().createCriteria(Barang.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Barang> search(String a) {
		// TODO Auto-generated method stub
		Criteria cr = cf.getSf().openSession().createCriteria(Barang.class);
		cr.add(Restrictions.or(
				Restrictions.like("namaBarang", a, MatchMode.ANYWHERE),
				Restrictions.like("idBarang", a, MatchMode.ANYWHERE)));
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Barang> getByJenisAndDate(Jenis jenis, Date date) {
		// TODO Auto-generated method stub

		Criteria cr = cf.getSf().openSession().createCriteria(Barang.class);

		cr.add(Restrictions.or(Restrictions.eq("jenis", jenis)));
		System.out.println(date);

		return cr.list();
	}

}
