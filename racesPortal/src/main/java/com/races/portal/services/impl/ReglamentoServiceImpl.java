package com.races.portal.services.impl;

import java.util.ArrayList;
import java.util.List;

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
import com.races.portal.dto.Reglamento;
import com.races.portal.services.ReglamentoService;

@Service
public class ReglamentoServiceImpl implements ReglamentoService {

	private static final Log LOGGER = LogFactory.getLog(CampeonatoServiceImpl.class);

	@Autowired
	Utils utils;

	@Autowired
	Converter converter;

	@Autowired
	Environment env;
	
	@Override
	public List<Reglamento> buscarReglamentos() {
		
		List<Reglamento> listReglamentos = new ArrayList<>();

		String url = env.getProperty(Constants.SERVICES_HOST) + env.getProperty("races.services.reglamentos.buscar");

		try {
			HttpResponse<String> response = utils.executeHttpMethod(url, null, null, null, HttpMethod.GET);
			if (response == null || response.getStatus() != HttpStatus.SC_OK) {
				LOGGER.warn("Response " + (response == null ? "null" : response.getStatus()));
			} else {
				JSONArray jsonArray = new JSONArray(response.getBody());
				for (int i = 0; i < jsonArray.length(); i++) {
					listReglamentos.add(converter.json2Reglamento(jsonArray.getJSONObject(i)));
				}
			}

		} catch (UnirestException e) {
			LOGGER.error(e);
		}

		return listReglamentos;
		
	}

}
