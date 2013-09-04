package com.fedesoft.collitaandroid;

import java.util.Date;
import java.util.List;

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

public interface CollitaDAOIfc {

	public  Cuadrilla getCuadrillaById(Integer id);
		
	public abstract void actualizarCuadrilla(Cuadrilla cuadrilla);

	public abstract void guardarCuadrilla(Cuadrilla cuadrilla)
			throws CuadrillaYaExisteException;

	public abstract List<Cuadrilla> recuperarCuadrillas(Boolean activas);

	public abstract List<Cuadrilla> buscarCuadrillasPorNombre(String nombre);
	
	public abstract Cuadrilla buscarCuadrillaPorNombre(String nombre);

	public abstract Camion getCamionById(Integer id);

	public abstract void actualizarCamion(Camion camion);

	public abstract void guardarCamion(Camion camion)
			throws CamionYaExisteException;

	public abstract List<Camion> recuperarCamiones(Boolean activos);

	public abstract Camion buscarCamionPorNombre(String nombre);

	public abstract Comprador getCompradorById(Integer id);

	public abstract void actualizarComprador(Comprador comprador);

	public abstract void guardarComprador(Comprador comprador)
			throws CompradorYaExisteException;

	public abstract List<Comprador> recuperarCompradores(Boolean activos);

	public  Comprador buscarCompradorPorNombre(String nombre);

	public  Terme getTermeById(Integer id);

	public abstract void actualizaTerme(Terme terme);

	public abstract void guardarTerme(Terme terme)
			throws TermeYaExisteException;

	public abstract List<Terme> recuperarTermes();

	public abstract Terme buscarTermePorNombre(String nombre);


	public abstract Variedad getVariedadById(Integer id);

	public abstract void actualizaVariedad(Variedad variedad);

	public abstract void guardarVariedad(Variedad variedad)
			throws VariedadYaExisteException;

	public abstract List<Variedad> recuperarVariedades();

	public abstract Variedad buscarVariedadPorNombre(String nombre);

	public abstract OrdenCollita getOrdenCollitadById(Integer id);

	public abstract void actualizarOrdenCollita(OrdenCollita ordencollita);

	public abstract void guardarOrdenCollita(OrdenCollita ordencollita);

	public abstract List<OrdenCollita> recuperarOrdenesCollita();

	public abstract List<OrdenCollita> recuperarOrdenesCollita(Date fecha);

	public List<OrdenCollita> recuperarOrdenesCollita(Date desde, Date hasta);

	void borraOrdenCollita(OrdenCollita ordencollita);

}