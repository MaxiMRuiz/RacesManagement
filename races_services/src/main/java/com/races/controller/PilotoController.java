package com.races.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.races.component.RacesException;
import com.races.dto.PilotoDto;
import com.races.entity.Piloto;
import com.races.services.PilotoService;

/**
 * Controlador para los servicios de Pilotos
 * 
 * @author Maximino Mañanes Ruiz
 *
 */
@RestController
public class PilotoController {

	private static final Log LOGGER = LogFactory.getLog(PilotoController.class);

	@Autowired
	@Qualifier("PilotoService")
	PilotoService pilotoService;

	/**
	 * Servicio de creacion de un nuevo piloto
	 * 
	 * @param pilotoDto
	 * @return
	 */
	@PostMapping("/piloto")
	public ResponseEntity<Piloto> crearPiloto(@RequestBody PilotoDto pilotoDto) {

		try {
			LOGGER.info("Creando nuevo Piloto: " + pilotoDto.getApodo());
			return new ResponseEntity<>(pilotoService.crearPiloto(pilotoDto), HttpStatus.OK);
		} catch (RacesException ex) {
			LOGGER.error("Error creando Piloto: " + ex.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

	}

	/**
	 * Servicio para la obtencion de pilotos, con filtros por id, nombre, apellido,
	 * apodo
	 * 
	 * @param id
	 * @param nombre
	 * @param apellido
	 * @param apodo
	 * @return
	 */
	@GetMapping("/piloto")
	public ResponseEntity<List<Piloto>> buscarPiloto(@RequestParam(required = false, name = "id") Long id,
			@RequestParam(required = false, name = "nombre") String nombre,
			@RequestParam(required = false, name = "apellido") String apellido,
			@RequestParam(required = false, name = "apodo") String apodo) {

		LOGGER.info("Buscando Pilotos: id[" + id + "] - nombre[" + nombre + "] - apellido[" + apellido + "] - apodo["
				+ apodo + "]");
		return new ResponseEntity<>(pilotoService.buscarPilotos(id, nombre, apellido, apodo), HttpStatus.OK);

	}

	/**
	 * Servicio para el borrado de un piloto
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/piloto/{id}")
	public ResponseEntity<Boolean> borrarPiloto(@PathVariable(name = "id") Long id) {

		try {
			LOGGER.info("Borrando Piloto: " + id);
			return new ResponseEntity<>(pilotoService.borrarPiloto(id), HttpStatus.OK);
		} catch (RacesException ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
