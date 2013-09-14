package com.fedesoft.collitaandroid;

import java.util.List;

import com.fedesoft.collitaandroid.model.Comprador;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class CompradoresActivity extends Activity {

	private Button agregarCompradoresButton;
	private LinearLayout listaCompradoresLayout;
	private CollitaDAOIfc collitaDAO;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compradores);
        agregarCompradoresButton=(Button)findViewById(R.id.agregarcompradoresbutton);		
		listaCompradoresLayout=(LinearLayout) findViewById(R.id.listacompradoreslinearlayout);
		
		agregarCompradoresButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				Intent intent=new Intent(getApplicationContext(),NuevoCompradorActivity.class);
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
		listaCompradoresLayout.removeAllViews();	
		collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		List<Comprador> compradores= collitaDAO.recuperarCompradores(null);
		for(final Comprador c:compradores){
			Button b=new Button(getApplicationContext());
			b.setText(c.getNombre());
			listaCompradoresLayout.addView(b);
			b.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(getApplicationContext(),EditarCompradorActivity.class);
					intent.putExtra("comprador_id", c.getId());
					startActivityForResult(intent, 3);				
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compradores, menu);
		return true;
	}
	@Override
	public boolean onMenuItemSelected(int featuredId, MenuItem item) {
		if (item.getItemId() == R.id.camionesitem) {			
			Intent intent = new Intent(getApplicationContext(),
					CamionesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		if (item.getItemId() == R.id.cuadrillasitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					CuadrillasActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		
		if (item.getItemId() == R.id.variedadesitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					VariedadesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		if (item.getItemId() == R.id.termesitem) {
			
			Intent intent = new Intent(getApplicationContext(),
					TermesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
		}
		if (item.getItemId() == R.id.informesitem){
			Intent intent = new Intent(getApplicationContext(),
					InformesActivity.class);
			startActivity(intent);
			setResult(1);
			finish();
			
		}
		return true;
	}
}
