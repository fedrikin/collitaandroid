package com.fedesoft.collitaandroid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.fedesoft.collitaandroid.model.Camion;
import com.fedesoft.collitaandroid.model.Comprador;
import com.fedesoft.collitaandroid.model.Cuadrilla;
import com.fedesoft.collitaandroid.model.OrdenCollita;
import com.fedesoft.collitaandroid.model.Terme;
import com.fedesoft.collitaandroid.model.Variedad;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class NuevaOrdenCollitaActivity extends Activity implements OnClickListener {

	private AutoCompleteTextView cuadrillaOrdenAutoComplete;
	private AutoCompleteTextView camionOrdenAutoComplete;	
	private AutoCompleteTextView variedadOrdenAutoComplete;
	private AutoCompleteTextView termeOrdenAutoComplete;
	private AutoCompleteTextView compradorOrdenAutoComplete;
	private EditText cajonesOrdenEditText;
	private EditText propietarioEditText;
	private Button seleccionCuadrillaButton;
	private Button seleccionCamionButton;
	private Button seleccionCompradorButton;
	private Button seleccionTermeButton;
	private Button seleccionVariedadButton;
	private Button guardaButton;
	private Button fechaOrdenButton;
	private Button diaMasButton;
	private Button diaMenosButton;
	private CollitaDAOIfc collitaDAO;
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd/MM/yyyy");
	Date fecha = new Date();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva_orden_collita);

		cuadrillaOrdenAutoComplete = (AutoCompleteTextView) findViewById(R.id.cuadrillaOrdenAutoCompleteEditText);
		camionOrdenAutoComplete = (AutoCompleteTextView) findViewById(R.id.camionOrdenAutoCompleteText);
		variedadOrdenAutoComplete = (AutoCompleteTextView) findViewById(R.id.variedadOrdenAutoCompleteText);
		termeOrdenAutoComplete = (AutoCompleteTextView) findViewById(R.id.termeOrdenAutoCompleteText);
		compradorOrdenAutoComplete = (AutoCompleteTextView) findViewById(R.id.compradorOrdenAutoCompleteText);
		propietarioEditText = (EditText) findViewById(R.id.propietarioordeneditText);
		cajonesOrdenEditText = (EditText) findViewById(R.id.cajonesEditText);
		diaMasButton= (Button) findViewById(R.id.diamasbutton);
		diaMenosButton=(Button)findViewById(R.id.diamenosbutton);
		fechaOrdenButton = (Button) findViewById(R.id.fechaOrdenbutton);	
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
		guardaButton = (Button) findViewById(R.id.editarrordenbutton);
		guardaButton.setOnClickListener(this);
		String fechamain= getIntent().getStringExtra("ordencollita_fecha");
		fechaOrdenButton.setText(fechamain);
		fechaOrdenButton.setOnClickListener(this);
		diaMasButton.setOnClickListener(this);
		diaMenosButton.setOnClickListener(this);
		
		
		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
			
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
    
	protected void guardarOrden() {
		String fechacollita = fechaOrdenButton.getText().toString();
		try {
			fecha = simpleDateFormat.parse(fechacollita);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String cajonePrevistos = cajonesOrdenEditText.getText().toString();
		if (cajonePrevistos.equals("")) {
			Toast toast = Toast.makeText(getApplicationContext(),
					"tindran que collira algo, NO??? POSA CAIXONS!!",
					Toast.LENGTH_LONG);
			toast.show();
			cajonePrevistos = "0";
			return;
		}
		String propietario = propietarioEditText.getText().toString();		
		String cuadrilla = cuadrillaOrdenAutoComplete.getText().toString();
		String camion = camionOrdenAutoComplete.getText().toString();		
		String variedad = variedadOrdenAutoComplete.getText().toString();
		String terme = termeOrdenAutoComplete.getText().toString();
		String comprador = compradorOrdenAutoComplete.getText().toString();				
		OrdenCollita ordenCollita = new OrdenCollita();
		ordenCollita.setCajonesPrevistos(Integer.parseInt(cajonePrevistos));
		ordenCollita.setPropietario(propietario);
		ordenCollita.setFechaCollita(fecha);
		ordenCollita.setCuadrilla(collitaDAO.buscarCuadrillaPorNombre(cuadrilla));
        ordenCollita.setCamion(collitaDAO.buscarCamionPorNombre(camion));
        ordenCollita.setComprador(collitaDAO.buscarCompradorPorNombre(comprador));
        ordenCollita.setTerme(collitaDAO.buscarTermePorNombre(terme));
        ordenCollita.setVariedad(collitaDAO.buscarVariedadPorNombre(variedad));
		collitaDAO.guardarOrdenCollita(ordenCollita);
		setResult(1);
		finish();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nueva_orden_collita, menu);
		return true;
	}

	@SuppressLint("ValidFragment")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private static class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		private Button button;

		public DatePickerFragment(Button button) {
			this.button = button;
		}

		@SuppressLint("NewApi")
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			// Create a new instance of DatePickerDialog and return it

			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			button.setText("" + day + "/" + (month + 1) + "/" + year);
		}
	}
// onClick s.......................
	@Override
	public void onClick(View v) {
		if (v == guardaButton){
			this.guardarOrden();
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
		if (v == fechaOrdenButton){			
			DialogFragment newFragment = new DatePickerFragment(
					fechaOrdenButton);
			newFragment.show(getFragmentManager(), "datePicker");
		}
		if (v==diaMasButton){
			try {
				Date fechaAux = simpleDateFormat.parse(fechaOrdenButton.getText().toString());
				GregorianCalendar calendar=new GregorianCalendar();
				calendar.setTime(fechaAux);
				calendar.add(GregorianCalendar.DATE, 1);
				fechaOrdenButton.setText(simpleDateFormat.format(calendar.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
				
		}
		if(v==diaMenosButton){
			try {
				Date fechaAux = simpleDateFormat.parse(fechaOrdenButton.getText().toString());
				GregorianCalendar calendar=new GregorianCalendar();
				calendar.setTime(fechaAux);
				calendar.add(GregorianCalendar.DATE, -1);
				fechaOrdenButton.setText(simpleDateFormat.format(calendar.getTime()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

}
