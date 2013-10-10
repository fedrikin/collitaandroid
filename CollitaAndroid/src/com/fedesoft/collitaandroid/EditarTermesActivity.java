package com.fedesoft.collitaandroid;

import com.fedesoft.collitaandroid.exceptions.TermeYaExisteException;
import com.fedesoft.collitaandroid.model.Terme;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditarTermesActivity extends Activity {
    private EditText nombreTermeEditText;
    private EditText preciokgEditText;
    private Button   editaTermeButton;
    private Terme terme;
	private CollitaDAOIfc collitaDAO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editar_termes);
		nombreTermeEditText= (EditText) findViewById(R.id.nombretermeeditText);
		preciokgEditText= (EditText) findViewById(R.id.preciotermeeditText);
		editaTermeButton= (Button) findViewById(R.id.editatermebutton);
		editaTermeButton.setOnClickListener(new OnClickListener() {						
			@Override
			public void onClick(View v) {
				editaTerme();	
			}
			
		});
		Integer termeId=getIntent().getIntExtra("terme_id",0);
//		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		collitaDAO=new CollitaDAOServidor();
		RecuperarTermeTask recuperarTermeTask=new RecuperarTermeTask();
		recuperarTermeTask.execute(termeId);
	}
	
	private void muestraDatos(){
		nombreTermeEditText.setText(terme.getNombre());
		preciokgEditText.setText(""+terme.getPrecioKilo());
	}
	
	private void editaTerme() {
		String nomterme=nombreTermeEditText.getText().toString();
	    if (nomterme.equals("")){
	    	Toast toast=Toast.makeText(getApplicationContext(), "Introduce NOMBRE TERMINO", Toast.LENGTH_LONG);
			toast.show();
			return;
	    }
	    terme.setNombre(nombreTermeEditText.getText().toString());
	    String precioterme=preciokgEditText.getText().toString();
	    if (precioterme.equals("")){
	    	Toast toast=Toast.makeText(getApplicationContext(), "Introduce un PRECIO", Toast.LENGTH_LONG);
			toast.show();
			return;
	    }
	    terme.setPrecioKilo(Double.parseDouble(preciokgEditText.getText().toString()));
	    ActualizarTermeTask actualizarTermeTask=new ActualizarTermeTask();
	    actualizarTermeTask.execute(terme);	    
	    setResult(1);
	    finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edtar_termes, menu);
		return true;
	}
	
	
	private class ActualizarTermeTask extends AsyncTask<Terme, Void, Void> {

		@Override
		protected Void doInBackground(Terme... params) {			
				collitaDAO.actualizaTerme(params[0]);
				return null;
		}

	}
	private class RecuperarTermeTask extends AsyncTask<Integer, Void, Terme>{

		@Override
		protected Terme doInBackground(Integer... params) {
			return collitaDAO.getTermeById(params[0]);
		}
		
		@Override
		protected void onPostExecute(Terme result) {
			super.onPostExecute(result);
			EditarTermesActivity.this.terme=result;
			muestraDatos();
		}
		
		
	}

}
