
package it.polito.tdp.borders;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="comboBox"
    private ComboBox<Country> comboBox;

    @FXML
    private Button btnGrafo;


    @FXML
    void doConfini(ActionEvent event) {
    	try
    	{
    		txtResult.setText("Visita in ampiezza:\n");
    		List <Country> ampiezza=this.model.visitaAmpiezza(comboBox.getValue());
    		for(Country c: ampiezza)
    		{
    			txtResult.appendText(c.toString()+" ");
    		}
    		txtResult.appendText("\n-------------------\n");
    		txtResult.appendText("Visita in profondità:\n");
    		List <Country> profondita=this.model.visitaProfondita(comboBox.getValue());
    		for(Country c: profondita)
    		{
    			txtResult.appendText(c.toString()+" ");
    		}
    	}
    	catch(NullPointerException npe)
    	{
    		txtResult.setText("Inserisci un anno accettabile");
    	}
    	catch (IllegalArgumentException iae)
    	{
    		txtResult.setText("Seleziona uno stato accettabile");
    	}
    }
    
    @FXML
    void doCalcolaConfini(ActionEvent event) 
    {
    	
    	try
    	{
    		int anno=Integer.parseInt(txtAnno.getText());
    		if(anno<1816 || anno>2016)
    			txtResult.setText("Metti un anno valido!");
    		else
    		{
    			this.comboBox.getItems().addAll(this.model.loadAllCountriesPerYear(anno));
    			float t1=System.nanoTime();
    			txtResult.setText(model.creaGrafo(anno));
    			float t2=System.nanoTime();
    			float processo=(float) ((t2-t1)*1e-9);
    			txtResult.appendText("\nTEMPO IMPIEGATO: "+String.valueOf(processo)+" secondi");
    		}
    	}
    	catch(NumberFormatException nfe)
    	{
    		txtResult.setText("Inserisci un numero valido!");
    		return;
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGrafo != null : "fx:id=\"btnGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.comboBox.setValue(new Country(null, 0, null));
    }
}
