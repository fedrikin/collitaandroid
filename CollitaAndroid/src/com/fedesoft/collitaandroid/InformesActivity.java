package com.fedesoft.collitaandroid;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fedesoft.collitaandroid.model.OrdenCollita;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;

public class InformesActivity extends Activity implements OnClickListener {
	private static final String[] opciones = { "cuadrillas", "camiones",
			"comprador" };
	private static final String[] unidades = { "Kilos", "arrobas", "cajones" };
	private Button desdeButton;
	private Button hastaButton;
	private Button opcionesButton;
	private Button unidadesButton;
	private int opcionSeleccionada = 0;
	private int unidadSeleccionada = 0;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private Date desde;
	private Date hasta;
	private CollitaDAOIfc collitaDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informes);
		desdeButton = (Button) findViewById(R.id.informesdesdebutton);
		hastaButton = (Button) findViewById(R.id.informeshastabutton);
		opcionesButton = (Button) findViewById(R.id.opcionesbutton);
		unidadesButton = (Button) findViewById(R.id.unidadesbutton);
		desdeButton.setOnClickListener(this);
		hastaButton.setOnClickListener(this);
		opcionesButton.setOnClickListener(this);
		unidadesButton.setOnClickListener(this);
		Date hoy = new Date();
		desde = hoy;
		hasta = hoy;
		desdeButton.setText(sdf.format(hoy));
		hastaButton.setText(sdf.format(hoy));
		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		muestraDatos();
	}

	private void muestraDatos() {
		opcionesButton.setText(opciones[opcionSeleccionada]);
		unidadesButton.setText(unidades[unidadSeleccionada]);
		List<OrdenCollita> ordenes = collitaDAO.recuperarOrdenesCollita(desde,hasta);
		System.out.println("desde:"+desde);
		System.out.println("hasta:"+hasta);
		System.out.println("ordenes:"+ordenes.size());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.informes, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == opcionesButton) {
			opcionSeleccionada = (opcionSeleccionada + 1) % 3;
			muestraDatos();
		}
		if (v == unidadesButton) {
			unidadSeleccionada = (unidadSeleccionada + 1) % 3;
			muestraDatos();
		}
		if (v == desdeButton) {
			DatePickerFragment fragment=new DatePickerFragment(desdeButton,true);
			fragment.show(getFragmentManager(), "Seleccione fecha");
			
		}
		if (v == hastaButton) {
			DatePickerFragment fragment=new DatePickerFragment(hastaButton,false);
			fragment.show(getFragmentManager(), "Seleccione fecha");
		}

	}

	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		private Button button;
		private Calendar calendar;
		private boolean desde;
		private SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		
		public DatePickerFragment(Button button,boolean desde) {
			
			this.button = button;
			this.desde=desde;
		}

		@SuppressLint("NewApi")
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);			
			return datePickerDialog;
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			calendar.set(Calendar.YEAR,year);
			calendar.set(Calendar.MONTH, month);
			calendar.set(Calendar.DAY_OF_MONTH,day);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.HOUR,0);
			calendar.set(Calendar.SECOND,0);
			button.setText(sdf.format(calendar.getTime()));
			if(desde){
				InformesActivity.this.desde=calendar.getTime();	
			}else{
				InformesActivity.this.hasta=calendar.getTime();
			}
			
			muestraDatos();
		}
		
	}
}
