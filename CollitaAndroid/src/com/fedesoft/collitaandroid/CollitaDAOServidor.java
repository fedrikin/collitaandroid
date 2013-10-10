package com.fedesoft.collitaandroid;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class CollitaDAOServidor implements CollitaDAOIfc {

	private HttpClient httpClient = new DefaultHttpClient();
	private Gson gson=new Gson();
	//private String baseurl = "http://localhost:8080/CollitaDAOServidor/CollitaServlet";;

	private String baseurl = "http://192.168.1.103:8080/CollitaDAOServidor/CollitaServlet";;
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
		HttpResponse response;
		String responseString = null;
		String json=gson.toJson(cuadrilla);
		try {	
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("op", "guardarcuadrilla"));
	        params.add(new BasicNameValuePair("json",json));
	        String paramString = URLEncodedUtils.format(params, "utf-8");
	        System.out.println(baseurl + "?"+paramString);
	        HttpGet httpGet = new HttpGet(baseurl + "?"+paramString);
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				if (responseString.equals("error - cuadrilla ya existe")){
					throw new CuadrillaYaExisteException();
				}
				
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

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
		HttpResponse response;
		String responseString = null;
		String json=gson.toJson(camion);
		try {	
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("op", "guardarcamion"));
	        params.add(new BasicNameValuePair("json",json));
	        String paramString = URLEncodedUtils.format(params, "utf-8");
	        System.out.println(baseurl + "?"+paramString);
	        HttpGet httpGet = new HttpGet(baseurl + "?"+paramString);
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				if (responseString.equals("error - camion ya existe")){
					throw new CamionYaExisteException();
				}
				
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		HttpResponse response;
		String responseString = null;
		String json=gson.toJson(comprador);
		try {	
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("op", "guardarcomprador"));
	        params.add(new BasicNameValuePair("json",json));
	        String paramString = URLEncodedUtils.format(params, "utf-8");
	        System.out.println(baseurl + "?"+paramString);
	        HttpGet httpGet = new HttpGet(baseurl + "?"+paramString);
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				if (responseString.equals("error - comprador ya existe")){
					throw new CompradorYaExisteException();
				}
				
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

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
		HttpResponse response;
		String responseString = null;
		try {	
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("op", "recuperaterme"));
	        params.add(new BasicNameValuePair("id",""+id));
	        String paramString = URLEncodedUtils.format(params, "utf-8");
	        System.out.println(baseurl + "?"+paramString);
	        HttpGet httpGet = new HttpGet(baseurl + "?"+paramString);
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();	
				System.out.println("response:"+responseString);
				return gson.fromJson(responseString, Terme.class);
				
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void actualizaTerme(Terme terme) {
		HttpResponse response;
		String responseString = null;
		String json=gson.toJson(terme);
		try {	
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("op", "actualizarterme"));
	        params.add(new BasicNameValuePair("json",json));
	        String paramString = URLEncodedUtils.format(params, "utf-8");
	        System.out.println(baseurl + "?"+paramString);
	        HttpGet httpGet = new HttpGet(baseurl + "?"+paramString);
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();				
				
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void guardarTerme(Terme terme) throws TermeYaExisteException {
		HttpResponse response;
		String responseString = null;
		String json=gson.toJson(terme);
		try {	
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("op", "guardarterme"));
	        params.add(new BasicNameValuePair("json",json));
	        String paramString = URLEncodedUtils.format(params, "utf-8");
	        System.out.println(baseurl + "?"+paramString);
	        HttpGet httpGet = new HttpGet(baseurl + "?"+paramString);
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				if (responseString.equals("error - terme ya existe")){
					throw new TermeYaExisteException();
				}
				
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public List<Terme> recuperarTermes() {
		HttpResponse response;
		String responseString = null;
		List<Terme> termes=null;
		try {
			response = httpClient.execute(new HttpGet(baseurl
					+ "?op=recuperartermes"));
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
				Type type = new TypeToken<ArrayList<Terme>>(){}.getType();
				termes=gson.fromJson(responseString,type);
			} else {
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return termes;
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

	@Override
	public void borraOrdenCollita(OrdenCollita ordencollita) {
		// TODO Auto-generated method stub

	}

}
