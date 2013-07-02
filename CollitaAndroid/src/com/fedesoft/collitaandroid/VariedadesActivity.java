package com.fedesoft.collitaandroid;

import java.util.List;

import com.fedesoft.collitaandroid.model.Variedad;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class VariedadesActivity extends Activity {
    private Button agregarVariedadButton;
    private LinearLayout listaVariedadeslinearlayout;
    private CollitaDAOIfc collitaDAO;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_variedades);
		agregarVariedadButton= (Button) findViewById(R.id.agregarvariedadbutton);
		listaVariedadeslinearlayout=(LinearLayout) findViewById(R.id.listavariedadeslinearlayout);		
		agregarVariedadButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				Intent intent=new Intent(getApplicationContext(),NuevaVarierdadActivity.class);
				startActivityForResult(intent, 3);				
			}
		});
		refrescarlista();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {			
		if (resultCode==1){
			refrescarlista();			
		}					
	}
	private void refrescarlista(){
		listaVariedadeslinearlayout.removeAllViews();
		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		List<Variedad> variedades= collitaDAO.recuperarVariedades();
		for(final Variedad variedad:variedades){
			Button b=new Button(getApplicationContext());
			b.setText(variedad.getNombre()+"-"+variedad.getId());
			listaVariedadeslinearlayout.addView(b);
			b.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(getApplicationContext(),EditarVariedadActivity.class);
					intent.putExtra("variedad_id", variedad.getId());
					startActivityForResult(intent, 3);				
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.variedades, menu);
		return true;
	}

}
