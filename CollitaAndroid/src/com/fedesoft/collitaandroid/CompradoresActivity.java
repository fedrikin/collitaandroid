package com.fedesoft.collitaandroid;

import java.util.List;

import com.fedesoft.collitaandroid.model.Comprador;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class CompradoresActivity extends Activity {

	private Button agregarCompradoresButton;
	private LinearLayout listaCompradoresLayout;
	
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
		CollitaDAOIfc collitaDAO=CollitaDAOSqlite.getInstance(getApplicationContext());
		List<Comprador> compradores= collitaDAO.recuperarCompradores();
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

}
