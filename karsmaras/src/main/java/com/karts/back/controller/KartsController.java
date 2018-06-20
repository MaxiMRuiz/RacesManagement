package com.karts.back.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.karts.back.dto.ReglamentoDTO;
import com.karts.back.entity.Reglamento;
import com.karts.back.services.KartingService;

@RestController
public class KartsController {

	private static final Log LOGGER = LogFactory.getLog(KartsController.class);

	@Autowired
	@Qualifier("KartingService")
	KartingService kartingService;

	@PostMapping("/karts/reglamento")
	public ResponseEntity<Reglamento> creaReglamento(@RequestBody ReglamentoDTO reglamentoDto) {

		LOGGER.info("Creando nuevo Reglamento: " + reglamentoDto.toString());

		Reglamento reglamento = kartingService.crearReglamento(reglamentoDto);

		return new ResponseEntity<>(reglamento, HttpStatus.OK);

	}

	@GetMapping("/karts/reglamento")
	public ResponseEntity<List<Reglamento>> getReglamento(@RequestParam(required = false, name = "id") Integer id) {

		LOGGER.info(id);

		List<Reglamento> list;
		if (id == null) {
			list = kartingService.getAllReglamentos();
		} else {
			list = kartingService.getReglamento(id);
		}

		return new ResponseEntity<>(list, HttpStatus.OK);

	}

	@PutMapping("/karts/reglamento/{id}")
	public ResponseEntity<Reglamento> putReglamento(@PathVariable(name = "id") Integer id,
			@RequestBody ReglamentoDTO reglamentoDto) {

		LOGGER.info(id);
		Reglamento reglamento = kartingService.updateReglamento(id, reglamentoDto);
		if(reglamento == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(reglamento, HttpStatus.OK);

	}
}
