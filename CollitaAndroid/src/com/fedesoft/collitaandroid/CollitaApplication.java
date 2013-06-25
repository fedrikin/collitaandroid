package com.fedesoft.collitaandroid;

import android.content.Context;

public class CollitaApplication {
	private static CollitaApplication instance=null;
	private CollitaDAOIfc collitaDAO;
	
	static public CollitaApplication getInstance(Context context){
		if (instance == null){
			instance=new CollitaApplication(context);
		}
		return instance;
	}
	
	private CollitaApplication(Context context){
		//collitaDAO=CollitaApplication.getInstance(getApplicationContext()).getCollitaDAO();
		collitaDAO=CollitaDAOSqlite.getInstance(context);		
	}
	
	

	public CollitaDAOIfc getCollitaDAO() {
		return collitaDAO;
	}

	
	

}
