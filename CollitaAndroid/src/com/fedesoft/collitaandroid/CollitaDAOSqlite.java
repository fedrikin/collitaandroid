package com.fedesoft.collitaandroid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
			instance = new CollitaDAOSqlite(context, "collitaandroid", null, 2);
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
		if (oldVersion == 1 && newVersion == 2) {
			db.execSQL("Alter table cuadrilla add column activa boolean null");
		}

	}

	// CUADRILLAS
	// METODOS...........................................................................................

	@Override
	public Cuadrilla getCuadrillaById(Integer id) {
		Cuadrilla resultado = new Cuadrilla();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from cuadrilla where id=?",
				new String[] { "" + id });
		cursor.moveToNext();
		resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
		resultado.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
		resultado.setNumeroCollidors(cursor.getInt(cursor
				.getColumnIndex("NUMEROCOLLIDORS")));
		resultado.setTelefono(cursor.getString(cursor
				.getColumnIndex("TELEFONO")));
		resultado.setActiva(cursor.getInt(cursor.getColumnIndex("activa")) > 0);
		db.close();
		return resultado;
	}

	@Override
	public void actualizarCuadrilla(Cuadrilla cuadrilla) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues parametros = new ContentValues();
		parametros.put("ID", cuadrilla.getId());
		parametros.put("NOMBRE", cuadrilla.getNombre());
		parametros.put("NUMEROCOLLIDORS", cuadrilla.getNumeroCollidors());
		parametros.put("TELEFONO", cuadrilla.getTelefono());
		parametros.put("activa", cuadrilla.isActiva());
		db.update("cuadrilla", parametros, "ID=" + cuadrilla.getId(), null);
		db.close();
	}

	@Override
	public void guardarCuadrilla(Cuadrilla cuadrilla)
			throws CuadrillaYaExisteException {
		SQLiteDatabase db = getWritableDatabase();
		Object[] parametros = new Object[] { cuadrilla.getNombre(),
				cuadrilla.getNumeroCollidors(), cuadrilla.getTelefono(), true };
		db.execSQL(
				"insert into cuadrilla(nombre,numerocollidors,telefono,activa) values (?,?,?,?)",
				parametros);
	}

	@Override
	public List<Cuadrilla> recuperarCuadrillas(Boolean activas) {
		List<Cuadrilla> resultado = new ArrayList<Cuadrilla>();
		SQLiteDatabase db = getReadableDatabase();
		String sql = "select * from cuadrilla";
		if (activas != null) {
			if (activas) {
				sql += " where activa=1";
			} else {
				sql += " where activa=0";
			}
		}

		Cursor cursor = db.rawQuery(sql, new String[] {});
		while (cursor.moveToNext()) {
			Cuadrilla cuadrilla = new Cuadrilla();
			cuadrilla.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			cuadrilla.setNombre(cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			cuadrilla.setNumeroCollidors(cursor.getInt(cursor
					.getColumnIndex("NUMEROCOLLIDORS")));
			cuadrilla.setTelefono(cursor.getString(cursor
					.getColumnIndex("TELEFONO")));
			resultado.add(cuadrilla);
		}
		return resultado;
	}

	@Override
	public List<Cuadrilla> buscarCuadrillasPorNombre(String nombre) {
		List<Cuadrilla> resultado = new ArrayList<Cuadrilla>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(
				"select * from cuadrilla where nombre like ?",
				new String[] { "%" + nombre + "%" });
		while (cursor.moveToNext()) {
			Cuadrilla cuadrilla = new Cuadrilla();
			cuadrilla.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			cuadrilla.setNombre(cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			cuadrilla.setNumeroCollidors(cursor.getInt(cursor
					.getColumnIndex("NUMEROCOLLIDORS")));
			cuadrilla.setTelefono(cursor.getString(cursor
					.getColumnIndex("TELEFONO")));
			resultado.add(cuadrilla);
		}
		return resultado;
	}

	// CAMIONES
	// METODOS................................................................................................

	@Override
	public Camion getCamionById(Integer id) {
		Camion resultado = new Camion();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from camion", new String[] {});
		while (cursor.moveToNext()) {
			Integer idnuevo = (cursor.getInt(cursor.getColumnIndex("ID")));
			if (idnuevo.equals(id)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setConductor(cursor.getString(cursor
						.getColumnIndex("CONDUCTOR")));
				resultado.setCajonesMaximo(cursor.getInt(cursor
						.getColumnIndex("CAJONESMAXIMO")));
				resultado.setTelefono(cursor.getString(cursor
						.getColumnIndex("TELEFONO")));
			}
		}
		return resultado;
	}

	@Override
	public void actualizarCamion(Camion camion) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues parametros = new ContentValues();
		parametros.put("ID", camion.getId());
		parametros.put("NOMBRE", camion.getNombre());
		parametros.put("CONDUCTOR", camion.getConductor());
		parametros.put("TELEFONO", camion.getTelefono());
		parametros.put("CAJONESMAXIMO", camion.getCajonesMaximo());
		db.update("camion", parametros, "ID=" + camion.getId(), null);
		db.close();
	}

	@Override
	public void guardarCamion(Camion camion) throws CamionYaExisteException {
		SQLiteDatabase db = getWritableDatabase();
		Object[] parametros = new Object[] { camion.getNombre(),
				camion.getConductor(), camion.getTelefono(),
				camion.getCajonesMaximo() };
		db.execSQL(
				"insert into camion(nombre,conductor,telefono,cajonesMaximo) values (?,?,?,?)",
				parametros);
	}

	@Override
	public List<Camion> recuperarCamiones() {
		List<Camion> resultado = new ArrayList<Camion>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from camion", new String[] {});
		while (cursor.moveToNext()) {
			Camion camion = new Camion();
			camion.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			camion.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
			camion.setConductor(cursor.getString(cursor
					.getColumnIndex("CONDUCTOR")));
			camion.setTelefono(cursor.getString(cursor
					.getColumnIndex("TELEFONO")));
			camion.setCajonesMaximo(cursor.getInt(cursor
					.getColumnIndex("CAJONESMAXIMO")));
			resultado.add(camion);
		}
		return resultado;
	}

	@Override
	public Camion buscarCamionPorNombre(String nombre) {
		Camion resultado = new Camion();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from camion", new String[] {});
		while (cursor.moveToNext()) {
			String nombreaux = (cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			if (nombreaux.equals(nombre)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setCajonesMaximo(cursor.getInt(cursor
						.getColumnIndex("CAJONESMAXIMO")));
				resultado.setConductor(cursor.getString(cursor
						.getColumnIndex("CONDUCTOR")));
				resultado.setTelefono(cursor.getString(cursor
						.getColumnIndex("TELEFONO")));
			}
		}
		return resultado;
	}

	// COMPRADORES
	// METODOS..............................................................................

	@Override
	public Comprador getCompradorById(Integer id) {
		Comprador resultado = new Comprador();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from comprador", new String[] {});
		while (cursor.moveToNext()) {
			Integer idnuevo = (cursor.getInt(cursor.getColumnIndex("ID")));
			if (idnuevo.equals(id)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setTelefono(cursor.getString(cursor
						.getColumnIndex("TELEFONO")));
			}
		}
		return resultado;
	}

	@Override
	public void actualizarComprador(Comprador comprador) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues parametros = new ContentValues();
		parametros.put("ID", comprador.getId());
		parametros.put("NOMBRE", comprador.getNombre());
		parametros.put("TELEFONO", comprador.getTelefono());
		db.update("comprador", parametros, "ID=" + comprador.getId(), null);
		db.close();
	}

	@Override
	public void guardarComprador(Comprador comprador)
			throws CompradorYaExisteException {
		SQLiteDatabase db = getWritableDatabase();
		Object[] parametros = new Object[] { comprador.getNombre(),
				comprador.getTelefono() };
		db.execSQL("insert into comprador(nombre,telefono) values(?,?)",
				parametros);
	}

	@Override
	public List<Comprador> recuperarCompradores() {
		List<Comprador> resultado = new ArrayList<Comprador>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from comprador", new String[] {});
		while (cursor.moveToNext()) {
			Comprador comprador = new Comprador();
			comprador.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			comprador.setNombre(cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			comprador.setTelefono(cursor.getString(cursor
					.getColumnIndex("TELEFONO")));
			resultado.add(comprador);
		}
		return resultado;
	}

	@Override
	public Comprador buscarCompradorPorNombre(String nombre) {
		Comprador resultado = new Comprador();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from comprador", new String[] {});
		while (cursor.moveToNext()) {
			String nombreaux = (cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			if (nombreaux.equals(nombre)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setTelefono(cursor.getString(cursor
						.getColumnIndex("TELEFONO")));
			}
		}
		return resultado;
	}

	// TERMES
	// METODOS.....................................................................................

	@Override
	public Terme getTermeById(Integer id) {
		Terme resultado = new Terme();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from terme", new String[] {});
		while (cursor.moveToNext()) {
			Integer idnuevo = (cursor.getInt(cursor.getColumnIndex("ID")));
			if (idnuevo.equals(id)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setPrecioKilo(cursor.getDouble(cursor
						.getColumnIndex("PRECIOKILO")));
			}
		}
		return resultado;
	}

	@Override
	public void actualizaTerme(Terme terme) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues parametros = new ContentValues();
		parametros.put("ID", terme.getId());
		parametros.put("NOMBRE", terme.getNombre());
		parametros.put("PRECIOKILO", terme.getPrecioKilo());
		db.update("terme", parametros, "ID=" + terme.getId(), null);
		db.close();
	}

	@Override
	public void guardarTerme(Terme terme) throws TermeYaExisteException {
		SQLiteDatabase db = getWritableDatabase();
		Object[] parametros = new Object[] { terme.getNombre(),
				terme.getPrecioKilo() };
		db.execSQL("insert into terme(nombre,preciokilo) values(?,?)",
				parametros);
	}

	@Override
	public List<Terme> recuperarTermes() {
		List<Terme> resultado = new ArrayList<Terme>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from terme", new String[] {});
		while (cursor.moveToNext()) {
			Terme terme = new Terme();
			terme.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			terme.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
			terme.setPrecioKilo(cursor.getDouble(cursor
					.getColumnIndex("PRECIOKILO")));
			resultado.add(terme);
		}
		return resultado;
	}

	@Override
	public Terme buscarTermePorNombre(String nombre) {
		Terme resultado = new Terme();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from terme", new String[] {});
		while (cursor.moveToNext()) {
			String nombreaux = (cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			if (nombreaux.equals(nombre)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setPrecioKilo(cursor.getDouble(cursor
						.getColumnIndex("PRECIOKILO")));
			}
		}
		return resultado;
	}

	// VARIEDADES METODOS.....................................................

	@Override
	public Variedad getVariedadById(Integer id) {
		Variedad resultado = new Variedad();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from variedad", new String[] {});
		while (cursor.moveToNext()) {
			Integer idnuevo = (cursor.getInt(cursor.getColumnIndex("ID")));
			if (idnuevo.equals(id)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setPrecioKilo(cursor.getDouble(cursor
						.getColumnIndex("PRECIOKILO")));
			}
		}
		return resultado;
	}

	@Override
	public void actualizaVariedad(Variedad variedad) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues parametros = new ContentValues();
		parametros.put("ID", variedad.getId());
		parametros.put("NOMBRE", variedad.getNombre());
		parametros.put("PRECIOKILO", variedad.getPrecioKilo());
		db.update("variedad", parametros, "ID=" + variedad.getId(), null);
		db.close();
	}

	@Override
	public void guardarVariedad(Variedad variedad)
			throws VariedadYaExisteException {
		SQLiteDatabase db = getWritableDatabase();
		Object[] parametros = new Object[] { variedad.getNombre(),
				variedad.getPrecioKilo() };
		db.execSQL("insert into variedad(nombre,preciokilo) values(?,?)",
				parametros);
	}

	@Override
	public List<Variedad> recuperarVariedades() {
		List<Variedad> resultado = new ArrayList<Variedad>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from variedad", new String[] {});
		while (cursor.moveToNext()) {
			Variedad variedad = new Variedad();
			variedad.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			variedad.setNombre(cursor.getString(cursor.getColumnIndex("NOMBRE")));
			variedad.setPrecioKilo(cursor.getDouble(cursor
					.getColumnIndex("PRECIOKILO")));
			resultado.add(variedad);
		}
		return resultado;
	}

	@Override
	public Variedad buscarVariedadPorNombre(String nombre) {
		Variedad resultado = new Variedad();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery("select *from variedad", new String[] {});
		while (cursor.moveToNext()) {
			String nombreaux = (cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			if (nombreaux.equals(nombre)) {
				resultado.setId(cursor.getInt(cursor.getColumnIndex("ID")));
				resultado.setNombre(cursor.getString(cursor
						.getColumnIndex("NOMBRE")));
				resultado.setPrecioKilo(cursor.getDouble(cursor
						.getColumnIndex("PRECIOKILO")));
			}
		}
		return resultado;
	}

	// .ORDENCOLLITA
	// METODOS........................................................................
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
		String sql = "insert into ordencollita(CAJONESPREVISTOS,FECHACOLLITA,CAMION_ID,COMPRADOR_ID,CUADRILLA_ID,TERME_ID,VARIEDAD_ID,propietario) values (?,?,?,?,?,?,?,?)";
		Object[] params = { ordencollita.getCajonesPrevistos(),
				ordencollita.getFechaCollita(),
				ordencollita.getCamion().getId(),
				ordencollita.getComprador().getId(),
				ordencollita.getCuadrilla().getId(),
				ordencollita.getTerme().getId(),
				ordencollita.getVariedad().getId(),
				ordencollita.getPropietario() };
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql, params);

	}

	@Override
	public List<OrdenCollita> recuperarOrdenesCollita() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdenCollita> recuperarOrdenesCollita(Date fecha) {
		List<OrdenCollita> resultado = new ArrayList<OrdenCollita>();

		return resultado;
	}

	/*
	 * ...........................................................................
	 * ..............................................................
	 */

	// Metodo per a poder executar multiples consultes sql en un string (NOSE
	// QUE ES AÇO)
	public static int executeSqlScript(SQLiteDatabase db, String sql) {
		String[] lines = sql.split(";");
		int count;
		count = executeSqlStatements(db, lines);
		System.out
				.println("Executed " + count + " statements from SQL script ");
		return count;
	}

	public static int executeSqlStatements(SQLiteDatabase db,
			String[] statements) {
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

	@Override
	public Cuadrilla buscarCuadrillaPorNombre(String nombre) {
		String sql = "Select * from cuadrilla where nombre = ?";
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, new String[] { nombre });
		if (cursor.moveToFirst()) {
			Cuadrilla cuadrilla = new Cuadrilla();
			cuadrilla.setId(cursor.getInt(cursor.getColumnIndex("ID")));
			cuadrilla.setNombre(cursor.getString(cursor
					.getColumnIndex("NOMBRE")));
			cuadrilla.setNumeroCollidors(cursor.getInt(cursor
					.getColumnIndex("NUMEROCOLLIDORS")));
			cuadrilla.setTelefono(cursor.getString(cursor
					.getColumnIndex("TELEFONO")));
			db.close();
			return cuadrilla;
		}
		db.close();
		return null;
	}

}
