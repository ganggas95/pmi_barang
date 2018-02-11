package org.stth.pmi.barang.implement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
 
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
/**
 *
 * @author herudi-pc
 */

public class ConfigSpring {
 
    private ApplicationContext applicationContext;
    private static ConfigSpring provider;
 
    private ConfigSpring() throws ExceptionInInitializerError {
        try {
            this.applicationContext = new ClassPathXmlApplicationContext("appConfig.xml");
        } catch (BeansException ex) {
            System.err.print("error " + ex);
        }
    }
 
    public synchronized static ConfigSpring getInstance() throws ExceptionInInitializerError {
        ConfigSpring tempProvider;
        if (provider == null) {
            provider = new ConfigSpring();
            tempProvider = provider;
        }else if(provider.getApplicationContext()==null){
            provider=new ConfigSpring();
            tempProvider=provider;
        }else{
            tempProvider=provider;
        }
        return tempProvider;
    }
 
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }
 
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
