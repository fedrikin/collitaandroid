package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.model.Comprador;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarCompradorActivity extends Activity {
    private EditText nombreCompradorEditText;
    private EditText telefonoCompradorEditText;
    private Button   editaCompradorButton;
    private Comprador comprador;   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_comprador);
		nombreCompradorEditText= (EditText) findViewById(R.id.nombrecompradoreditText);
		telefonoCompradorEditText= (EditText) findViewById(R.id.telefonocompradoreditText);
		editaCompradorButton= (Button) findViewById(R.id.editarompradorbutton);
		editaCompradorButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					editarcomprador();		
			}
			
		});
		Integer compradorId=getIntent().getIntExtra("comprador_id",0);
		System.out.println("id"+compradorId);
		CollitaDAOIfc collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
	    comprador=collitaDAO.getCompradorById(compradorId);
		nombreCompradorEditText.setText(comprador.getNombre());
		telefonoCompradorEditText.setText(comprador.getTelefono());
		
	}
    private void editarcomprador(){
    	String nombrecomprador=nombreCompradorEditText.getText().toString();
    	if (nombrecomprador.equals("")){
    		Toast toast=Toast.makeText(getApplicationContext(), "Debe introducir un nombre", Toast.LENGTH_LONG);
			toast.show();
			return;
    	}
    	comprador.setNombre(nombreCompradorEditText.getText().toString());
    	comprador.setTelefono(telefonoCompradorEditText.getText().toString());
    	CollitaDAOSqlite.getInstance(getApplicationContext()).actualizarComprador(comprador);
    	setResult(1);
    	finish();
    	    	
    }
}