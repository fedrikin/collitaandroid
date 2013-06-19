package com.fedesoft.collitaandroid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.fedesoft.collitaandroid.exceptions.CamionYaExisteException;
import com.fedesoft.collitaandroid.exceptions.CompradorYaExisteException;
import com.fedesoft.collitaandroid.exceptions.CuadrillaYaExisteException;
import com.fedesoft.collitaandroid.exceptions.TermeYaExisteException;
import com.fedesoft.collitaandroid.exceptions.VariedadYaExisteException;
import com.fedesoft.collitaandroid.model.Camion;
import com.fedesoft.collitaandroid.model.Comprador;
import com.fedesoft.collitaandroid.model.Cuadrilla;
import com.fedesoft.collitaandroid.model.OrdenCollita;
import com.fedesoft.collitaandroid.model.Terme;
import com.fedesoft.collitaandroid.model.Variedad;

public class CollitaDAOSqlite extends SQLiteOpenHelper implements CollitaDAOIfc {
	private static CollitaDAOSqlite instance = null;
	private String sqlCreate;

	public static CollitaDAOSqlite getInstance(Context context) {
		if (instance == null) {
			instance = new CollitaDAOSqlite(context, "collitaandroid", null, 1);
		}
		return instance;
	}

	private CollitaDAOSqlite(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		sqlCreate = leerFichero();
		
	}

	private String leerFichero() {
		InputStream fis;
		try {
			fis = getClass().getResourceAsStream("create.sql");
			StringBuffer fileContent = new StringBuffer("");
			byte[] buffer = new byte[1024];
			while (fis.read(buffer) != -1) {
				fileContent.append(new String(buffer));
			} 
			
			fis.close();
			return fileContent.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		executeSqlScript(db, sqlCreate);		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	@Override
	public Cuadrilla getCuadrillaById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarCuadrilla(Cuadrilla cuadrilla) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarCuadrilla(Cuadrilla cuadrilla)
			throws CuadrillaYaExisteException {
		SQLiteDatabase db = getWritableDatabase();
		Object[] parametros = new Object[]{cuadrilla.getNombre(),cuadrilla.getNumeroCollidors(),cuadrilla.getTelefono()};
		db.execSQL("insert into cuadrilla(nombre,numerocollidors,telefono) values (?,?,?)",parametros);		
	}

	@Override
	public List<Cuadrilla> recuperarCuadrillas() {
		List<Cuadrilla> resultado=new ArrayList<Cuadrilla>();
		SQLiteDatabase db = getReadableDatabase();		
		Cursor cursor=db.rawQuery("select * from cuadrilla", new String[]{});		
		while(cursor.moveToNext()){
			Cuadrilla cuadrilla=new Cuadrilla();
			cuadrilla.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			cuadrilla.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
			cuadrilla.setNumeroCollidors(cursor.getInt(cursor.getColumnIndex("NUMEROCOLLIDORS")));
			cuadrilla.setTelefono(cursor.getString(cursor.getColumnIndex("TELEFONO")));
			resultado.add(cuadrilla);					
		}
		return resultado;
	}

	@Override
	public Cuadrilla buscarCuadrillaPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Camion getCamionById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarCamion(Camion camion) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarCamion(Camion camion) throws CamionYaExisteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Camion> recuperarCamiones() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Camion buscarCamionPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comprador getCompradorById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarComprador(Comprador comprador) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarComprador(Comprador comprador)
			throws CompradorYaExisteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Comprador> recuperarCompradores() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comprador buscarCompradorPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Terme getTermeById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizaTerme(Terme terme) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarTerme(Terme terme) throws TermeYaExisteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Terme> recuperarTermes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Terme buscarTermePorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variedad getVariedadById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizaVariedad(Variedad variedad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarVariedad(Variedad variedad)
			throws VariedadYaExisteException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Variedad> recuperarVariedades() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Variedad buscarVariedadPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrdenCollita getOrdenCollitadById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizarOrdenCollita(OrdenCollita ordencollita) {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarOrdenCollita(OrdenCollita ordencollita) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<OrdenCollita> recuperarOrdenesCollita() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdenCollita> recuperarOrdenesCollita(Date fecha) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	// Metodo per a poder executar multiples consultes sql en un string
	 public static int executeSqlScript(SQLiteDatabase db, String sql)
	             {	        
	        String[] lines = sql.split(";");
	        int count;
	        count = executeSqlStatements(db, lines);	        
	        System.out.println("Executed " + count + " statements from SQL script ");
	        return count;
	    }

	  

	    public static int executeSqlStatements(SQLiteDatabase db, String[] statements) {
	        int count = 0;
	        for (String line : statements) {
	            line = line.trim();
	            if (line.length() > 0) {
	                db.execSQL(line);
	                count++;
	            }
	        }
	        return count;
	    }


	

}
