package com.races.portal.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.races.portal.constants.Constants;
import com.races.portal.dto.Clasificacion;
import com.races.portal.dto.Equipo;
import com.races.portal.dto.GranPremio;
import com.races.portal.dto.Inscripcion;
import com.races.portal.dto.Piloto;
import com.races.portal.dto.Resultado;
import com.races.portal.dto.Sancion;
import com.races.portal.dto.Sesion;
import com.races.portal.dto.SesionGP;
import com.races.portal.dto.TipoSesion;
import com.races.portal.dto.Vuelta;

import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

@Component
public class Converter {

	@Autowired
	ObjectMapper mapper;

	/**
	 * Conversor de JSONObject a Gp
	 * 
	 * @param jsonObject
	 * @return
	 */
	public GranPremio json2Gp(JSONObject json) {
		GranPremio gp = new GranPremio();
		JSONObject jsonGp;
		String fecha = null;
		if (json.isNull(Constants.PARAM_GP)) {
			return gp;
		} else {
			jsonGp = json.getJSONObject(Constants.PARAM_GP);
		}
		gp.setId(jsonGp.isNull(Constants.PARAM_ID) ? 0 : jsonGp.getLong(Constants.PARAM_ID));
		gp.setUbicacion(jsonGp.isNull(Constants.PARAM_UBICACION) ? "N/A" : jsonGp.getString(Constants.PARAM_UBICACION));
		JSONArray array = json.isNull(Constants.PARAM_SESIONES) ? new JSONArray()
				: json.getJSONArray(Constants.PARAM_SESIONES);
		List<SesionGP> listaSesiones = new ArrayList<>();
		for (int i = 0; i < array.length(); i++) {
			listaSesiones.add(json2SesionGp(array.getJSONObject(i)));
			fecha = listaSesiones.get(i).getFecha();
		}
		gp.setFecha(fecha);
		gp.setSesiones(listaSesiones);
		return gp;
	}

	/**
	 * Conversor de JSONObject a Sesion
	 * 
	 * @param json
	 * @return
	 */
	public SesionGP json2SesionGp(JSONObject json) {
		SesionGP sesionGp = new SesionGP();
		sesionGp.setId(json.isNull(Constants.PARAM_ID) ? 0 : json.getLong(Constants.PARAM_ID));
		sesionGp.setFecha(json.isNull(Constants.PARAM_FECHA) ? "N/A" : json.getString(Constants.PARAM_FECHA));
		sesionGp.setSesion(json.isNull(Constants.PARAM_SESION) ? new Sesion()
				: json2Sesion(json.getJSONObject(Constants.PARAM_SESION)));
		return sesionGp;
	}

	/**
	 * Conversor de JSONObject a Sesion
	 * 
	 * @param json
	 * @return
	 */
	public Sesion json2Sesion(JSONObject json) {
		Sesion sesion = new Sesion();
		sesion.setId(json.isNull(Constants.PARAM_ID) ? 0 : json.getLong(Constants.PARAM_ID));
		sesion.setDescripcion(
				json.isNull(Constants.PARAM_DESCRIPCION) ? "N/A" : json.getString(Constants.PARAM_DESCRIPCION));
		sesion.setTipoSesion(json.isNull(Constants.PARAM_TIPO_SESION) ? new TipoSesion()
				: json2TipoSesion(json.getJSONObject(Constants.PARAM_TIPO_SESION)));
		return sesion;
	}

	/**
	 * Conversor de JSONObject a TipoSesion
	 * 
	 * @param jsonObject
	 * @return
	 */
	public TipoSesion json2TipoSesion(JSONObject json) {
		TipoSesion tSesion = new TipoSesion();
		tSesion.setId(json.isNull(Constants.PARAM_ID) ? 0 : json.getLong(Constants.PARAM_ID));
		tSesion.setDescripcion(
				json.isNull(Constants.PARAM_DESCRIPCION) ? "" : json.getString(Constants.PARAM_DESCRIPCION));
		return tSesion;
	}

	/**
	 * Conversor de json a Resultado
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Resultado json2Resultado(JSONObject json) throws IOException {
		Resultado resultado = new Resultado();
		resultado.setId(json.isNull(Constants.PARAM_ID) ? 0 : json.getLong(Constants.PARAM_ID));
		resultado.setInscripcion(json.isNull(Constants.PARAM_INSCRIPCION) ? new Inscripcion()
				: mapper.readValue(json.getJSONObject(Constants.PARAM_INSCRIPCION).toString(), Inscripcion.class));
		resultado.setSesionGP(json.isNull(Constants.PARAM_SESIONGP) ? new SesionGP()
				: json2SesionGp(json.getJSONObject(Constants.PARAM_SESIONGP)));
		resultado.setTiempo(json.isNull(Constants.PARAM_TIEMPO) ? 0 : json.getInt(Constants.PARAM_TIEMPO));
		resultado.setVueltas(json.isNull(Constants.PARAM_N_VUELTAS) ? 0 : json.getInt(Constants.PARAM_N_VUELTAS));
		resultado.setvRapida(json.isNull(Constants.PARAM_V_RAPIDA) ? 0 : json.getInt(Constants.PARAM_V_RAPIDA));
		return resultado;
	}

	/**
	 * Conversor de json a Vuelta
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Vuelta json2Vuelta(JSONObject json) throws IOException {
		Vuelta vuelta = new Vuelta();
		vuelta.setId(json.isNull(Constants.PARAM_ID) ? 0 : json.getLong(Constants.PARAM_ID));
		vuelta.setnVuelta(json.isNull(Constants.PARAM_N_VUELTA) ? 0 : json.getInt(Constants.PARAM_N_VUELTA));
		vuelta.setResultado(json.isNull(Constants.PARAM_RESULTADO) ? new Resultado()
				: json2Resultado(json.getJSONObject(Constants.PARAM_RESULTADO)));
		vuelta.setTiempo(json.isNull(Constants.PARAM_TIEMPO) ? 0 : json.getInt(Constants.PARAM_TIEMPO));
		return vuelta;
	}

	/**
	 * Conversor de json a Sancion
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Sancion json2Sancion(JSONObject json) throws IOException {
		Sancion sancion = new Sancion();
		sancion.setId(json.isNull(Constants.PARAM_ID) ? 0 : json.getLong(Constants.PARAM_ID));
		sancion.setDescripcion(
				json.isNull(Constants.PARAM_DESCRIPCION) ? "" : json.getString(Constants.PARAM_DESCRIPCION));
		sancion.setPuntos(json.isNull(Constants.PARAM_PUNTOS) ? 0 : json.getInt(Constants.PARAM_PUNTOS));
		sancion.setTiempo(json.isNull(Constants.PARAM_TIEMPO) ? "0"
				: "" + Double.valueOf(json.getInt(Constants.PARAM_TIEMPO)) / 1000);
		sancion.setResultado(json.isNull(Constants.PARAM_RESULTADO) ? new Resultado()
				: json2Resultado(json.getJSONObject(Constants.PARAM_RESULTADO)));
		return sancion;
	}

	/**
	 * Conversor de json a Clasificacion
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Clasificacion json2Clasificacion(JSONObject json) throws IOException {
		Clasificacion clasificacion = new Clasificacion();
		clasificacion.setPiloto(json.isNull(Constants.PARAM_INSCRIPCION) ? new Piloto()
				: mapper.readValue(json.getJSONObject(Constants.PARAM_INSCRIPCION).getJSONObject(Constants.PARAM_PILOTO).toString(), Piloto.class));
		clasificacion.setEquipo(json.isNull(Constants.PARAM_INSCRIPCION) ? new Equipo()
				: mapper.readValue(json.getJSONObject(Constants.PARAM_INSCRIPCION).getJSONObject(Constants.PARAM_EQUIPO).toString(), Equipo.class));
		clasificacion.setPuntos(json.isNull(Constants.PARAM_PUNTOS) ? 0 : json.getInt(Constants.PARAM_PUNTOS));
		return clasificacion;
	}

}
