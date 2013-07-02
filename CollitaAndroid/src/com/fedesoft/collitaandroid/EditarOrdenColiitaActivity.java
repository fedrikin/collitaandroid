package com.fedesoft.collitaandroid;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.fedesoft.collitaandroid.model.Camion;
import com.fedesoft.collitaandroid.model.Comprador;
import com.fedesoft.collitaandroid.model.Cuadrilla;
import com.fedesoft.collitaandroid.model.OrdenCollita;
import com.fedesoft.collitaandroid.model.Terme;
import com.fedesoft.collitaandroid.model.Variedad;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class EditarOrdenColiitaActivity extends Activity implements OnClickListener {
	private Button fechaOrdenButton;
	private AutoCompleteTextView cuadrillaOrdenAutoComplete;
	private AutoCompleteTextView camionOrdenAutoComplete;	
	private AutoCompleteTextView variedadOrdenAutoComplete;
	private AutoCompleteTextView termeOrdenAutoComplete;
	private AutoCompleteTextView compradorOrdenAutoComplete;
	private EditText propietarioEditText;
	private EditText cajonesOrdenEditText;
	private Button seleccionCuadrillaButton;
	private Button seleccionCamionButton;
	private Button seleccionCompradorButton;
	private Button seleccionTermeButton;
	private Button seleccionVariedadButton;
	private Button editaButton;
	private OrdenCollita ordecollita;
	private CollitaDAOIfc collitaDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_orden_coliita);
		fechaOrdenButton=(Button) findViewById(R.id.fechaOrdenbutton);
		cuadrillaOrdenAutoComplete=(AutoCompleteTextView) findViewById(R.id.cuadrillaOrdenAutoCompleteEditText);
		camionOrdenAutoComplete=(AutoCompleteTextView) findViewById(R.id.camionOrdenAutoCompleteEditText);
		variedadOrdenAutoComplete=(AutoCompleteTextView) findViewById(R.id.variedadOrdenAutoCompleteEditText);
		termeOrdenAutoComplete=(AutoCompleteTextView) findViewById(R.id.termeOrdenAutoCompleteEditText);
		compradorOrdenAutoComplete=(AutoCompleteTextView) findViewById(R.id.compradorAutoCompleteEditText);
		propietarioEditText=(EditText) findViewById(R.id.propietarioordeneditText);
		cajonesOrdenEditText=(EditText) findViewById(R.id.cajonesEditText);
		seleccionCuadrillaButton = (Button) findViewById(R.id.elijecuadrillaButton);		
		seleccionCuadrillaButton.setOnClickListener(this);
		seleccionCamionButton = (Button) findViewById(R.id.elijecamionbutton);
		seleccionCamionButton.setOnClickListener(this);
		seleccionCompradorButton = (Button) findViewById(R.id.elijecomprdorButton);
		seleccionCompradorButton.setOnClickListener(this);
		seleccionTermeButton =(Button) findViewById(R.id.elijetermeButton);
		seleccionTermeButton.setOnClickListener(this);
		seleccionVariedadButton =(Button) findViewById(R.id.elijevariedadButton);		
		seleccionVariedadButton.setOnClickListener(this);
		
		editaButton=(Button) findViewById(R.id.editarrordenbutton);
		editaButton.setOnClickListener(this);
		
		
       // Arreplega els datos desde mainactivity
		
		Integer ordenCollitaId=getIntent().getIntExtra("ordencollita_id",0);
		System.out.println("id:"+ordenCollitaId);
		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		ordecollita=collitaDAO.getOrdenCollitadById(ordenCollitaId);
		
		propietarioEditText.setText(ordecollita.getPropietario());
		cajonesOrdenEditText.setText(""+ordecollita.getCajonesPrevistos());
		
		Date fechacollita=(ordecollita.getFechaCollita());
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");
		String fechastring=sdf.format(fechacollita);		
		fechaOrdenButton.setText(fechastring);
		
		
		
		cuadrillaOrdenAutoComplete.setText(ordecollita.getCuadrilla().toString());
		camionOrdenAutoComplete.setText(ordecollita.getCamion().toString());
		compradorOrdenAutoComplete.setText(ordecollita.getComprador().toString());
		termeOrdenAutoComplete.setText(ordecollita.getTerme().toString());
		variedadOrdenAutoComplete.setText(ordecollita.getVariedad().toString());
					
	  //.......................................
		
		// Cargar datos...............
		cargarCuadrillas();
		cuadrillaOrdenAutoComplete.setThreshold(1);
		cargarCamiones();
		camionOrdenAutoComplete.setThreshold(1);
		cargarCompradores();
		compradorOrdenAutoComplete.setThreshold(1);
		cargarTermes();
		termeOrdenAutoComplete.setThreshold(1);
		cargarVariedades();
		variedadOrdenAutoComplete.setThreshold(1);
		
	}
     // METODOS......................
	private void cargarCuadrillas() {
		List<Cuadrilla> cuadrillas = collitaDAO.recuperarCuadrillas(true);
		ArrayAdapter<Cuadrilla> adapter=new ArrayAdapter<Cuadrilla>(this,android.R.layout.simple_dropdown_item_1line,cuadrillas.toArray(new Cuadrilla[]{}));
		cuadrillaOrdenAutoComplete.setAdapter(adapter);
	}
	private void cargarCamiones() {
		List<Camion> camiones = collitaDAO.recuperarCamiones(true);
		ArrayAdapter<Camion> adapter=new ArrayAdapter<Camion>(this,android.R.layout.simple_dropdown_item_1line,camiones.toArray(new Camion[]{}));
		camionOrdenAutoComplete.setAdapter(adapter);
	}
	
	private void cargarCompradores() {
		List<Comprador> compradores = collitaDAO.recuperarCompradores(true);
		ArrayAdapter<Comprador> adapter=new ArrayAdapter<Comprador>(this,android.R.layout.simple_dropdown_item_1line,compradores.toArray(new Comprador[]{}));
		compradorOrdenAutoComplete.setAdapter(adapter);
	}
	private void cargarTermes() {
		List<Terme> termes = collitaDAO.recuperarTermes();
		ArrayAdapter<Terme> adapter=new ArrayAdapter<Terme>(this,android.R.layout.simple_dropdown_item_1line,termes.toArray(new Terme[]{}));
		termeOrdenAutoComplete.setAdapter(adapter);
	}
	private void cargarVariedades() {
		List<Variedad> variedades = collitaDAO.recuperarVariedades();
		ArrayAdapter<Variedad> adapter=new ArrayAdapter<Variedad>(this,android.R.layout.simple_dropdown_item_1line,variedades.toArray(new Variedad[]{}));
		variedadOrdenAutoComplete.setAdapter(adapter);
	}
		
		
	
	
	private void editarOrdeCollita() {
		ordecollita.setPropietario(propietarioEditText.getText().toString());
		ordecollita.setCajonesPrevistos(Integer.parseInt(cajonesOrdenEditText.getText().toString()));
		
		String cuadrilla = cuadrillaOrdenAutoComplete.getText().toString();
		String camion = camionOrdenAutoComplete.getText().toString();		
		String variedad = variedadOrdenAutoComplete.getText().toString();
		String terme = termeOrdenAutoComplete.getText().toString();
		String comprador = compradorOrdenAutoComplete.getText().toString();
		ordecollita.setCuadrilla(collitaDAO.buscarCuadrillaPorNombre(cuadrilla));
		ordecollita.setCamion(collitaDAO.buscarCamionPorNombre(camion));
		ordecollita.setComprador(collitaDAO.buscarCompradorPorNombre(comprador));
		ordecollita.setTerme(collitaDAO.buscarTermePorNombre(terme));
		ordecollita.setVariedad(collitaDAO.buscarVariedadPorNombre(variedad));
		
		collitaDAO.actualizarOrdenCollita(ordecollita);
		setResult(1);
		finish();
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_orden_coliita, menu);
		return true;
	}

	// onClick s.......................
		@Override
		public void onClick(View v) {
			if (v == editaButton){
				this.editarOrdeCollita();
			}
			if (v == seleccionCuadrillaButton){
				System.out.println("cuadrillas");
				cuadrillaOrdenAutoComplete.showDropDown();
				
			}
			if (v == seleccionCamionButton){
				System.out.println("camiones");
				camionOrdenAutoComplete.showDropDown();
			}
			if (v == seleccionCompradorButton){
				System.out.println("compradores");
				compradorOrdenAutoComplete.showDropDown();
			}
			if (v == seleccionTermeButton){
				System.out.println("termes");
				termeOrdenAutoComplete.showDropDown();
			}
			if (v == seleccionVariedadButton){
				System.out.println("variedades");
				variedadOrdenAutoComplete.showDropDown();
			}
		}
}
