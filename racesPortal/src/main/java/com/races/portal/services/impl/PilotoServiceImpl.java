package com.races.portal.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.races.portal.component.Converter;
import com.races.portal.component.Utils;
import com.races.portal.constants.Constants;
import com.races.portal.dto.Piloto;
import com.races.portal.services.PilotoService;

/**
 * Implementacion de la interfaz PilotoService
 * 
 * @author Maximino Mañanes Ruiz
 *
 */
@Service
public class PilotoServiceImpl implements PilotoService {

	private static final Log LOGGER = LogFactory.getLog(PilotoServiceImpl.class);

	@Autowired
	Utils utils;

	@Autowired
	Converter converter;

	@Autowired
	Environment env;

	@Override
	public List<Piloto> buscarPilotos(Long id, String nombre, String apellido, String apodo) {
		List<Piloto> listaPilotos = new ArrayList<>();

		String url = env.getProperty(Constants.SERVICES_HOST) + env.getProperty("races.services.pilotos.buscar");

		try {
			HttpResponse<String> response = utils.executeHttpMethod(url, null, null, null, HttpMethod.GET);
			if (response == null || response.getStatus() != HttpStatus.SC_OK) {
				LOGGER.warn("Response " + (response == null ? "null" : response.getStatus()));
			} else {
				JSONArray jsonArray = new JSONArray(response.getBody());
				for (int i = 0; i < jsonArray.length(); i++) {
					listaPilotos.add(converter.json2Piloto(jsonArray.getJSONObject(i)));
				}
			}

		} catch (UnirestException e) {
			LOGGER.error(e);
		}

		return listaPilotos;
	}

	@Override
	public Piloto buscarPilotos(String id) {
		String url = env.getProperty(Constants.SERVICES_HOST) + env.getProperty("races.services.pilotos.buscar");

		Map<String, Object> params = new HashMap<>();
		params.put(Constants.PARAM_ID, id);

		try {
			HttpResponse<String> response = utils.executeHttpMethod(url, params, null, null, HttpMethod.GET);
			if (response == null || response.getStatus() != HttpStatus.SC_OK) {
				LOGGER.warn("Response " + (response == null ? "null" : response.getStatus()));
			} else {
				JSONArray jsonArray = new JSONArray(response.getBody());
				if (jsonArray.length() > 0) {
					return converter.json2Piloto(jsonArray.getJSONObject(0));
				}
			}

		} catch (UnirestException e) {
			LOGGER.error(e);
		}

		return new Piloto();
	}

	@Override
	public Boolean borrarPiloto(String id) {
		String url = env.getProperty(Constants.SERVICES_HOST) + env.getProperty("races.services.pilotos.borrar") + id;

		try {
			HttpResponse<String> response = utils.executeHttpMethod(url, null, null, null, HttpMethod.DELETE);
			if (response == null || response.getStatus() != HttpStatus.SC_OK) {
				LOGGER.warn("Response " + (response == null ? "null" : response.getStatus()));
			} else {
				return true;
			}

		} catch (UnirestException e) {
			LOGGER.error(e);
		}

		return false;
	}

	@Override
	public Boolean crearPiloto(Piloto piloto) {
		String url = env.getProperty(Constants.SERVICES_HOST) + env.getProperty("races.services.pilotos.crear");

		Map<String, Object> body = new HashMap<>();
		body.put(Constants.PARAM_NOMBRE, piloto.getNombre());
		body.put(Constants.PARAM_APELLIDO, piloto.getApellido());
		body.put(Constants.PARAM_APODO, piloto.getApodo());

		Map<String, String> headers = new HashMap<>();
		headers.put(Constants.CONTENT_TYPE, Constants.APP_JSON);

		try {
			HttpResponse<String> response = utils.executeHttpMethod(url, null, body, headers, HttpMethod.POST);
			if (response == null || response.getStatus() != HttpStatus.SC_OK) {
				LOGGER.warn("Response " + (response == null ? "null" : response.getStatus()));
			} else {
				return true;
			}

		} catch (UnirestException e) {
			LOGGER.error(e);
		}

		return false;

	}

}
