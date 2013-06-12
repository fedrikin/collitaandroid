package com.fedesoft.collitaandroid;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import android.widget.Button;
import android.widget.EditText;

public class EditarOrdenColiitaActivity extends Activity {
	private Button fechaOrdenButton;
	private EditText cuadrillaOrdenEditText;
	private EditText camionOrdenEditText;
	private EditText cajonesOrdenEditText;
	private EditText variedadOrdenEditText;
	private EditText termeOrdenEditText;
	private EditText compradorOrdenEditText;
	private EditText propietarioEditText;
	private Button editaButton;
	private OrdenCollita ordecollita;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_orden_coliita);
		fechaOrdenButton=(Button) findViewById(R.id.fechaOrdenbutton);
		cuadrillaOrdenEditText=(EditText) findViewById(R.id.cuadrillaordeneditText);
		camionOrdenEditText=(EditText) findViewById(R.id.camionordeneditText);
		variedadOrdenEditText=(EditText) findViewById(R.id.variedadordeneditText);
		termeOrdenEditText=(EditText) findViewById(R.id.termeordeneditText);
		compradorOrdenEditText=(EditText) findViewById(R.id.compradorordeneditText);
		propietarioEditText=(EditText) findViewById(R.id.propietarioordeneditText);
		cajonesOrdenEditText=(EditText) findViewById(R.id.cajonesEditText);
		editaButton=(Button) findViewById(R.id.editarrordenbutton);
		editaButton.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
					editarOrdeCollita();			
			}

			
		});

		Integer ordenCollitaId=getIntent().getIntExtra("ordencollita_id",0);
		System.out.println("id:"+ordenCollitaId);
		CollitaDAO collitaDAO=CollitaDAO.getInstance();
		ordecollita=collitaDAO.getOrdenCollitadById(ordenCollitaId);
		
		propietarioEditText.setText(ordecollita.getPropietario());
		cajonesOrdenEditText.setText(""+ordecollita.getCajonesPrevistos());
		
		Date fechacollita=(ordecollita.getFechaCollita());
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy");
		String fechastring=sdf.format(fechacollita);		
		fechaOrdenButton.setText(fechastring);
		
		Cuadrilla ordecuadrilla=ordecollita.getCuadrilla();
		Camion ordecamion=ordecollita.getCamion();
		Comprador ordecomprador=ordecollita.getComprador();
		Terme ordeterme=ordecollita.getTerme();
		Variedad ordevariedad=ordecollita.getVariedad();
		
		cuadrillaOrdenEditText.setText(ordecuadrilla.getNombre());
		camionOrdenEditText.setText(ordecamion.getNombre());
		compradorOrdenEditText.setText(ordecomprador.getNombre());
		termeOrdenEditText.setText(ordeterme.getNombre());
		variedadOrdenEditText.setText(ordevariedad.getNombre());
		
		
		
		
		
	}
	
	private void editarOrdeCollita() {
		ordecollita.setPropietario(propietarioEditText.getText().toString());
		ordecollita.setCajonesPrevistos(Integer.parseInt(cajonesOrdenEditText.getText().toString()));
		
		CollitaDAO.getInstance().actualizarOrdenCollita(ordecollita);
		setResult(1);
		finish();
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.editar_orden_coliita, menu);
		return true;
	}

	
}
