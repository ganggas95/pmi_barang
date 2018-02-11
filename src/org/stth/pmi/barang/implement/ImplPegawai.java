package org.stth.pmi.barang.implement;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.stth.pmi.barang.entitas.Pegawai;
import org.stth.pmi.barang.interfaces.InterPegawai;
@Repository
public class ImplPegawai implements InterPegawai{
	@Autowired
	ConfigSession cf;
	
	@Override
	public void createOrUpdate(Pegawai p) {
		// TODO Auto-generated method stub
		if(p.getIdPegawai()==null){
			cf.create(p);
		}else{
			cf.update(p);
		}
	}

	@Override
	public void delete(Pegawai p) {
		// TODO Auto-generated method stub
		cf.delete(p);
	}

	@Override
	public void autoId(Pegawai p) {
		// TODO Auto-generated method stub
		try{
			Criteria cr = cf.getSf().openSession().createCriteria(Pegawai.class);
			cr.setProjection(Projections.max("idPegawai"));
			Object o = cr.uniqueResult();
			String auto = ""+(Integer.parseInt(o.toString().substring(2))+1);
			String nol = "";
			if(auto.length()==1){
				nol = "00";
			}else if(auto.length()==2){
				nol = "0";
			}else if(auto.length()==3){
				nol = "";
			}
			p.setIdPegawai("P."+nol+auto);
		}catch(NumberFormatException | HibernateException e){
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pegawai> getAll() {
		// TODO Auto-generated method stub
		return cf.getSf().openSession().createCriteria(Pegawai.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pegawai> search(String a) {
		// TODO Auto-generated method stub
		Criteria cr = cf.getSf().openSession().createCriteria(Pegawai.class);
		cr.add(Restrictions.or(Restrictions.like("nama",a ,MatchMode.ANYWHERE),
				Restrictions.like("nip",a ,MatchMode.ANYWHERE)));
		return cr.list();
	}

}
