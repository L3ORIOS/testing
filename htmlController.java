package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.Serializable;
import java.net.URL;
//import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

//import javafx.beans.property.SimpleStringProperty;
//import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

public class htmlController implements Initializable {
	@FXML
	private TableView<Persona> Lista;
	@FXML
	private TextField Txf_Apellido;
	@FXML
	private TextField Txf_Nombre;
	@FXML
	private Button Btn_Agregar;
	@FXML
	private Button btn_Eliminar;
	@FXML
	private Button Btn_EliminarEscribiendo;
	@FXML
	private Button Btn_Cargar;
	@FXML
	private Button Btn_Guardar;
	
	public  ObservableList<Persona> ListaPersona;

	// Event Listener on Button[#Btn_Agregar].onAction
	@FXML
	public void Agregar(ActionEvent event) {
		// TODO Autogenerated
		String Nombre = Txf_Nombre.getText();
		String Apellido = Txf_Apellido.getText();
		
		Persona p = new Persona(Nombre,Apellido);
		
		ListaPersona.add(p);
		Lista.setItems(ListaPersona);
		Lista.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}
	// Event Listener on Button[#btn_Eliminar].onAction
	@FXML
	public void Eliminar(ActionEvent event) {
		// TODO Autogenerated
		Persona aux = Lista.getSelectionModel().getSelectedItem();
		int eliminar = ListaPersona.indexOf(aux);
		ListaPersona.remove(eliminar);
		Lista.setItems(ListaPersona);
		
		
		System.out.println(aux.getNombre()+" "+aux.getApellido());
	}
	// Event Listener on Button[#Btn_EliminarEscribiendo].onAction
	@FXML
	public void EliminarEscribiendo(ActionEvent event) {
		// TODO Autogenerated
		if(Txf_Apellido.getText()==""|Txf_Apellido.getText()==" ") {
			JOptionPane.showMessageDialog(null,"Necesita el apellido tambien.","Mensaje del sistema", JOptionPane.INFORMATION_MESSAGE);
		}else {
			
			int aux = -1;
			
			for(int i = 0; i < ListaPersona.size(); i++) {
				
				if(ListaPersona.get(i).NombreProperty().get().equals(Txf_Nombre.getText()) && ListaPersona.get(i).ApellidoProperty().get().equals(Txf_Apellido.getText())) {
					aux = i;
				}
			}
			
			if(aux!=-1) {
				ListaPersona.remove(aux);
				Lista.setItems(ListaPersona);
			}
			if(aux==-1) {
				JOptionPane.showMessageDialog(null,"No existe "+Txf_Nombre.getText()+" en el sistema","Mensaje del sistema", JOptionPane.INFORMATION_MESSAGE);
			}
			
		}
		
	}
	// Event Listener on Button[#Btn_Cargar].onAction
	
	@FXML
	public void Cargar(ActionEvent event) throws ClassNotFoundException {
		// TODO Autogenerated
		
			  
		  	try{
		  		FileInputStream fila = new FileInputStream("clientes.txt");
		  		ObjectInputStream ob = new ObjectInputStream(fila);
		  		
		  		ObservableList<Persona> Aux = FXCollections.observableArrayList(); 
		  	
		  		for(int i = 0; i < fila.available() ; i++) {
		  			Object aux = ob.readObject();
		  			Persona aux2 = (Persona) aux;
		  			Aux.add(aux2);
		  		}
		  		
		  		Lista.setItems(Aux);
	
		  		ob.close();
		  
		  		}catch(IOException e){
		  			e.printStackTrace();
		  		}
	
	   	
	}
	// Event Listener on Button[#Btn_Guardar].onAction
	@FXML
	public void Guardar(ActionEvent event) {
		// TODO Autogenerated
		if(!ListaPersona.isEmpty()){
		  	try{
		  		FileOutputStream Archivo = new FileOutputStream("clientes.txt");
		  		ObjectOutputStream ob = new ObjectOutputStream(Archivo);
		  		
		  		for(int i = 0; i < ListaPersona.size(); i++) {
		  			ob.writeObject(ListaPersona.get(i));
		  		}
		  		//ob.writeObject(Lista.getItems());
		  		 System.out.println("File Saved Successfully.");
		  		ob.close();
		  		
		  		}catch(IOException e){
		  			e.printStackTrace();
		  			 System.err.println("Save: File not found.");
		  		}
	  		}
	  		else{
	  			JOptionPane.showMessageDialog(null, "No hay datos para guardar","Mensaje del sitema!",JOptionPane.INFORMATION_MESSAGE);
	  		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ListaPersona = FXCollections.observableArrayList();
		TableColumn<Persona, String> FirstName = new TableColumn<>("Nombre");
		TableColumn<Persona, String> LastName = new TableColumn<>("Apellido");
		FirstName.setCellValueFactory(cellData -> cellData.getValue().NombreProperty());
		LastName.setCellValueFactory(cellData -> cellData.getValue().ApellidoProperty());
		Lista.getColumns().addAll(FirstName,LastName);
		Lista.setItems(ListaPersona);
	}
}
