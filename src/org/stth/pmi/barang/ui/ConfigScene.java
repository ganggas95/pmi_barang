package org.stth.pmi.barang.ui;

import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.StageStyle;

 
/**
 *
 * @author herudi-pc
 */
public class ConfigScene {
 
    public ConfigScene() {
    	
    }
     
    //dialog jfx
    public static void createDialog(Alert.AlertType alertType, String s){
        Alert alert = new Alert(alertType,s);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Info");
        alert.showAndWait();
    }
     
    //select table auto, saat save data
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static void selectedTable(Integer click, Integer select, TableView tv){
        if (click==1) {
            tv.getSelectionModel().select(select);
            tv.scrollTo(select);
        }else{
            tv.getSelectionModel().selectLast();
            int table = tv.getSelectionModel().getSelectedIndex();
            tv.scrollTo(table);
        }
    }
     
    //pengaturan select saat di klik
    @SuppressWarnings("rawtypes")
	public static void onSelectTable(Integer click, Integer select, TableView tv){
        click = 1;
        select = tv.getSelectionModel().getSelectedIndex();
    }

     
    
}
