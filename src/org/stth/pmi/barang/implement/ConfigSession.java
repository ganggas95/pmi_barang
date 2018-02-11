package org.stth.pmi.barang.implement;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class ConfigSession {
	@Autowired
	SessionFactory sf;

	public SessionFactory getSf() {
		return sf;
	}

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	public void create(Object o){
		Session session = this.sf.openSession();
		Transaction trx = session.beginTransaction();
		try{
			session.persist(o);
		}catch(HibernateException e){
			e.printStackTrace();
			System.out.println("Gagal Create..");
		}
		trx.commit();
		session.close();
	}
	
	public void update(Object o){
		Session session = this.sf.openSession();
		Transaction trx = session.beginTransaction();
		try{
			session.merge(o);
		}catch(HibernateException e){
			e.printStackTrace();
			System.out.println("Gagal Create..");
		}
		trx.commit();
		session.close();
	}
	
	public void delete(Object o){
		Session session = this.sf.openSession();
		Transaction trx = session.beginTransaction();
		try{
			session.delete(o);
		}catch(HibernateException e){
			e.printStackTrace();
			System.out.println("Gagal Create..");
		}
		trx.commit();
		session.close();
	}
}
