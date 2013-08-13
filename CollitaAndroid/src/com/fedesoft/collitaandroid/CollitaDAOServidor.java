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

public class CollitaDAOServidor implements CollitaDAOIfc {

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
		// TODO Auto-generated method stub

	}

	@Override
	public List<Cuadrilla> recuperarCuadrillas(Boolean activas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cuadrilla> buscarCuadrillasPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
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
	public List<Camion> recuperarCamiones(Boolean activos) {
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
	public List<Comprador> recuperarCompradores(Boolean activos) {
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

	@Override
	public List<OrdenCollita> recuperarOrdenesCollita(Date desde, Date hasta) {
		// TODO Auto-generated method stub
		return null;
	}

}
