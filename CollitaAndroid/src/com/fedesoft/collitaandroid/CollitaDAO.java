package com.fedesoft.collitaandroid;
//hola
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

public class CollitaDAO implements CollitaDAOIfc {
	private static CollitaDAOIfc instance;
	private List<Cuadrilla> cuadrillas;
	private List<Camion> camiones;
	private List<Variedad> variedades;
	private List<Terme> termes;
	private List<OrdenCollita> ordenesCollitas;
	private List<Comprador> compradores;
	private Integer contador=1;
	
	
	public static CollitaDAOIfc getInstance(){
		if (instance == null){
			instance=new CollitaDAO();
		}
		return instance;
	}
	
	private CollitaDAO(){
		
		// LLISTAT CUADRILLES
		
		cuadrillas=new ArrayList<Cuadrilla>();
		Cuadrilla c1=new Cuadrilla();
		c1.setId(1);
		c1.setNombre("juan");
		c1.setNumeroCollidors(4);
		c1.setTelefono("96111111");
		try {
			guardarCuadrilla(c1);
		} catch (CuadrillaYaExisteException e1) {
			
			e1.printStackTrace();
		}
		Cuadrilla c2=new Cuadrilla();
		c2.setId(2);
		c2.setNombre("pepito");
		c2.setNumeroCollidors(7);
		c2.setTelefono("96111113");
		try {
			guardarCuadrilla(c2);
		} catch (CuadrillaYaExisteException e1) {
			
			e1.printStackTrace();
		}		Cuadrilla c3=new Cuadrilla();
		c3.setId(3);
		c3.setNombre("andres");
		c3.setNumeroCollidors(8);
		c3.setTelefono("96111112");
		try {
			guardarCuadrilla(c3);
		} catch (CuadrillaYaExisteException e1) {
			
			e1.printStackTrace();
		}		
		// LLISTAT CAMIONS	
		
		camiones=new ArrayList<Camion>();
		Camion ca1=new Camion();
		ca1.setId(1);
		ca1.setNombre("RenaultRoig");
		ca1.setConductor("Vicent");
		ca1.setTelefono("2019");
		ca1.setCajonesMaximo(420);
		try {
			guardarCamion(ca1);
		} catch (CamionYaExisteException e1) {
	
			e1.printStackTrace();
		}		
		Camion ca2=new Camion();
		ca2.setId(2);
		ca2.setNombre("NisanVerd");
		ca2.setConductor("Joaquin");
		ca2.setTelefono("2018");
		ca2.setCajonesMaximo(420);
		try {
			guardarCamion(ca2);
		} catch (CamionYaExisteException e1) {
			
			e1.printStackTrace();
		}				Camion ca3=new Camion();
		ca3.setId(3);
		ca3.setNombre("ManRoig");
		ca3.setConductor("Torda");
		ca3.setTelefono("609898989");
		ca3.setCajonesMaximo(420);
		try {
			guardarCamion(ca3);
		} catch (CamionYaExisteException e1) {
			
			e1.printStackTrace();
		}				
		// LLISTAT VARIEDADES
		
		variedades=new ArrayList<Variedad>();
		Variedad v1=new Variedad();
		v1.setId(1);
		v1.setNombre("Navelina");
		v1.setPrecioKiloCollita(0.060);
		try {
			guardarVariedad(v1);
		} catch (VariedadYaExisteException e1) {
		
			e1.printStackTrace();
		}
		Variedad v2=new Variedad();
		v2.setId(2);
		v2.setNombre("Clementina");
		v2.setPrecioKiloCollita(0.1080);
		try {
			guardarVariedad(v2);
		} catch (VariedadYaExisteException e1) {
			
			e1.printStackTrace();
		}		Variedad v3=new Variedad();
		v3.setId(3);
		v3.setNombre("Ortanike");
		v3.setPrecioKiloCollita(0.0880);
		try {
			guardarVariedad(v3);
		} catch (VariedadYaExisteException e1) {
			
			e1.printStackTrace();
		}		
		// LLUSTAT TERMES
		
		termes=new ArrayList<Terme>();
		Terme t1=new Terme();
		t1.setId(1);
		t1.setNombre("Villanova");
		t1.setPrecioKilo(0.14);
		
		try {
			guardarTerme(t1);
		} catch (TermeYaExisteException e1) {
			
			e1.printStackTrace();
		}
		Terme t2=new Terme();
		t2.setId(2);
		t2.setNombre("Pedralba");
		t2.setPrecioKilo(0.17);
		try {
			guardarTerme(t2);
		} catch (TermeYaExisteException e1) {
		
			e1.printStackTrace();
		}		Terme t3=new Terme();
		t3.setId(3);
		t3.setNombre("Tavernes");
		t3.setPrecioKilo(0.08);
		try {
			guardarTerme(t3);
		} catch (TermeYaExisteException e1) {
			
			e1.printStackTrace();
		}		
		//LLISTAT COMPRADOERS
		compradores=new ArrayList<Comprador>();
		Comprador co1=new Comprador();
		co1.setId(1);
		co1.setNombre("Jose Moreno");
		co1.setTelefono("66334455");
		try {
			guardarComprador(co1);
		} catch (CompradorYaExisteException e2) {
			
			e2.printStackTrace();
		}		
		Comprador co2=new Comprador();
		co2.setId(2);
		co2.setNombre("Jose Ibañez");		
		co2.setTelefono("66778899");		
		try {
			guardarComprador(co2);
		} catch (CompradorYaExisteException e2) {
			
			e2.printStackTrace();
		}		
		Comprador co3=new Comprador();
		co3.setId(3);
		co3.setNombre("Juanvi Serigo");
		co3.setTelefono("66778899");		
		try {
			guardarComprador(co3);
		} catch (CompradorYaExisteException e1) {

			e1.printStackTrace();
		}
				
		//LLISTAT ORDRESCULLITA
		
		// Examples de treballar amb Date.
		GregorianCalendar gc=new GregorianCalendar();
		gc.set(GregorianCalendar.DAY_OF_MONTH, 25);
		gc.set(GregorianCalendar.MONTH, GregorianCalendar.JULY);
		gc.set(GregorianCalendar.YEAR, 2011);
		Date fecha=gc.getTime();
		SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
		String fechaString= sdf.format(fecha);
		try {
			Date fecha2=sdf.parse(fechaString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fecha=new Date();
		ordenesCollitas=new ArrayList<OrdenCollita>();
		OrdenCollita oc1=new OrdenCollita();
		oc1.setId(1);
		oc1.setFechaCollita(fecha);
		oc1.setCuadrilla(c3);
		oc1.setCamion(ca1);
		oc1.setComprador(co1);
		oc1.setTerme(t2);
		oc1.setVariedad(v3);
		oc1.setCajonesPrevistos(420);
		oc1.setPropietario("FincaCaturla");
		ordenesCollitas.add(oc1);
		OrdenCollita oc2=new OrdenCollita();
		oc2.setId(2);
		oc2.setFechaCollita(fecha);
		oc2.setCuadrilla(c3);
		oc2.setCamion(ca1);
		oc2.setComprador(co1);
		oc2.setTerme(t2);
		oc2.setVariedad(v3);
		oc2.setCajonesPrevistos(420);
		oc2.setPropietario("FincaSAEAS");
		ordenesCollitas.add(oc2);
		OrdenCollita oc3=new OrdenCollita();
		oc3.setId(3);
		
		
		oc3.setFechaCollita(fecha);
		oc3.setCuadrilla(c3);
		oc3.setCamion(ca1);
		oc3.setComprador(co1);
		oc3.setTerme(t2);
		oc3.setVariedad(v3);
		oc3.setCajonesPrevistos(420);
		oc3.setPropietario("SATPicamont");
		ordenesCollitas.add(oc3);
	}
	
	//..........................................................................................recuperarcuadrilles
	@Override
	public Cuadrilla getCuadrillaById(Integer id){ 
		for(Cuadrilla c:cuadrillas){
			if (c.getId() == id){
				return c;
			}
		}
		return null;
		
	}
	
	@Override
	public void actualizarCuadrilla(Cuadrilla cuadrilla){
		for (Cuadrilla cuadrillaAux : cuadrillas) {
			if (cuadrillaAux.getId()==cuadrilla.getId()){
				cuadrillaAux.setNombre(cuadrilla.getNombre());
				cuadrillaAux.setNumeroCollidors(cuadrilla.getNumeroCollidors());
				cuadrillaAux.setTelefono(cuadrilla.getTelefono());
				return;
			}
		}
		
	}
	
	@Override
	public void guardarCuadrilla(Cuadrilla cuadrilla) throws CuadrillaYaExisteException{
		for(Cuadrilla c: cuadrillas){
			if (c.getNombre().equals(cuadrilla.getNombre())){
				throw new CuadrillaYaExisteException();
			}
		}
		cuadrilla.setId(contador);
		cuadrillas.add(cuadrilla);
		contador++;
	}
	
	@Override
	public List<Cuadrilla> recuperarCuadrillas(Boolean activas){		
		return cuadrillas;
	}
	
	@Override
	public Cuadrilla buscarCuadrillaPorNombre(String nombre){		
		for(Cuadrilla c: cuadrillas){
			if(c.getNombre().equals(nombre)){
				return c;
			}			
		}
		return null;
	}
	
	//...............................................................................................recuperarcamion
	
	
	
	@Override
	public Camion getCamionById(Integer id){
		for(Camion ca:camiones){
			if (ca.getId() == id){
				return ca;
			}		
		}
		return null;
	}
	@Override
	public void actualizarCamion(Camion camion){
		for (Camion camionAux:camiones){
			if (camionAux.getId()==camion.getId()){
				camionAux.setNombre(camion.getNombre());
				camionAux.setConductor(camion.getConductor());
				camionAux.setTelefono(camion.getTelefono());
				camionAux.setCajonesMaximo(camion.getCajonesMaximo());
				return;
			}
		}
	}
	@Override
	public void guardarCamion(Camion camion) throws CamionYaExisteException{
		for (Camion ca:camiones){
			if (ca.getNombre().equals(camion.getNombre())){
				throw new CamionYaExisteException();
			}
		}
		camion.setId(contador);		
		camiones.add(camion);	
		contador++;
		
	}
	
    @Override
	public List<Camion> recuperarCamiones(Boolean activo){
    	
		return camiones;    	
    }
   
    @Override
	public Camion buscarCamionPorNombre(String nombre){		
		for(Camion ca: camiones){
			if(ca.getNombre().equals(nombre)){
				return ca;
			}			
		}
		return null;
	}
    
    //.................................................................................................recuperarcomprador
    
    @Override
	public Comprador getCompradorById(Integer id){
    	 for(Comprador co:compradores){
    		 if (co.getId() == id){
    			 return co;
    		 }
    	 }
    	 return null;
    }
    @Override
	public void actualizarComprador(Comprador comprador){
    	for (Comprador compradorAux:compradores){
    		if (compradorAux.getId()== comprador.getId()){
    			compradorAux.setNombre(comprador.getNombre());
    			compradorAux.setTelefono(comprador.getTelefono());
    			return;    			
    		}
    	}
    }
    @Override
	public void guardarComprador(Comprador comprador)throws CompradorYaExisteException{
		  for(Comprador co:compradores){
			   if (co.getNombre().equals(comprador.getNombre())){
				   throw new CompradorYaExisteException();
			   }
		  }
		  comprador.setId(contador);
		  compradores.add(comprador);
		  contador++;
	}
    public List<Comprador> recuperarCompradores(boolean activos){
    	
		return compradores;
    }
    
    @Override
	public Comprador buscarCompradorPorNombre(String nombre){    	
    	for(Comprador co: compradores){
    		if(co.getNombre().equals(nombre)){    	
    			return co;
    		}
    	}
    	return null;
    }
    //.................................................................................................recuperarterme
    
    @Override
	public Terme getTermeById(Integer id){
    	for(Terme t:termes){
    		if(t.getId() == id){
    			return t;
    		}
    	}
    	return null;
    }
    @Override
	public void actualizaTerme(Terme terme){
    	for (Terme termeAux:termes){
    		if (termeAux.getId() == terme.getId()){
    			termeAux.setNombre(terme.getNombre());
    			termeAux.setPrecioKilo(terme.getPrecioKilo());
    			return;
    		}
    	}
    }
    @Override
	public void guardarTerme(Terme terme)throws TermeYaExisteException{
    	 for(Terme t:termes){
    		 if (t.getNombre().equals(terme.getNombre())){
    			 throw new TermeYaExisteException();
    		 }
    	 }
    	 terme.setId(contador);
		 termes.add(terme);
		 contador++;
	}
    @Override
	public  List<Terme> recuperarTermes(){
    	
		return termes;
    	
    }

    @Override
	public Terme buscarTermePorNombre(String nombre){    	
    	for(Terme t:termes){
    		if(t.getNombre().equals(nombre)){
    			return t;
    		}
    	}
    	return null;
    			
    }
    //..................................................................................................recuperarVarietat

	@Override
	public Variedad getVariedadById(Integer id){
		for(Variedad v:variedades){
			if (v.getId() == id){
				return v;
			}		
		}
		return null;
	}
	@Override
	public void actualizaVariedad(Variedad variedad){
		for (Variedad variedadAux:variedades){
			if (variedadAux.getId()==variedad.getId()){
				variedadAux.setNombre(variedad.getNombre());
				variedadAux.setPrecioKiloCollita(variedad.getPrecioKiloCollita()); 
				return;				
			}
		}
	}
    @Override
	public void guardarVariedad(Variedad variedad) throws VariedadYaExisteException{
    	for (Variedad v:variedades){
			if (v.getNombre().equals(variedad.getNombre())){
				throw new VariedadYaExisteException();
			}
		}
    	variedad.setId(contador);
		variedades.add(variedad);
		contador++;
		
	}
    @Override
	public List<Variedad> recuperarVariedades(){
    	 return variedades ;
    }
   
    @Override
	public Variedad buscarVariedadPorNombre(String nombre){    
    	for(Variedad v: variedades){
			if(v.getNombre().contains(nombre)){
				return v;
			}			
		}
		return null;
    	
    }
    //...................................................................................................recuperarOrdedecollita
    @Override
	public OrdenCollita getOrdenCollitadById(Integer id){
		for(OrdenCollita oc:ordenesCollitas){
			if (oc.getId() == id){
				return oc;
			}		
		}
		
		return null;
	}
    @Override
	public void actualizarOrdenCollita(OrdenCollita ordencollita){
    	for (OrdenCollita ordencollitaAux:ordenesCollitas){
    		if ( ordencollitaAux.getId()==ordencollita.getId()){
    			 ordencollitaAux.setFechaCollita(ordencollita.getFechaCollita());
    			 ordencollitaAux.setCuadrilla(ordencollita.getCuadrilla());
    			 ordencollitaAux.setCamion(ordencollita.getCamion());
    			 ordencollitaAux.setComprador(ordencollita.getComprador());
    			 ordencollitaAux.setTerme(ordencollita.getTerme());
    			 ordencollitaAux.setVariedad(ordencollita.getVariedad());
    			 ordencollitaAux.setCajonesPrevistos(ordencollita.getCajonesPrevistos());
    			 ordencollitaAux.setPropietario(ordencollita.getPropietario());
    			 return;
    		}
    	}
    }
    @Override
	public void guardarOrdenCollita(OrdenCollita ordencollita){
    	ordencollita.setId(contador);
		ordenesCollitas.add(ordencollita);
		contador++;
	}
    @Override
	public List<OrdenCollita> recuperarOrdenesCollita(){
    	
		return ordenesCollitas;
    	
    }

	@Override
	public List<OrdenCollita> recuperarOrdenesCollita(Date fecha) {
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");				
		List<OrdenCollita> ordenesCollitaFecha=new ArrayList<OrdenCollita>();
		for(OrdenCollita ordenCollita:ordenesCollitas){					
			if (fmt.format(ordenCollita.getFechaCollita()).equals(fmt.format(fecha))){
				ordenesCollitaFecha.add(ordenCollita);
			}			
		}
		return ordenesCollitaFecha;
	}

	@Override
	public List<Cuadrilla> buscarCuadrillasPorNombre(String nombre) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Comprador> recuperarCompradores(Boolean activos) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdenCollita> recuperarOrdenesCollita(Date desde, Date hasta) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
