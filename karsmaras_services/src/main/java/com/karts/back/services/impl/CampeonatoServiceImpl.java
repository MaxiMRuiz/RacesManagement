package com.karts.back.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.karts.back.component.KartsConverter;
import com.karts.back.dto.CampeonatoDTO;
import com.karts.back.entity.Campeonato;
import com.karts.back.repository.CampeonatoRepository;
import com.karts.back.services.CampeonatoService;

@Service("CampeonatoService")
public class CampeonatoServiceImpl implements CampeonatoService {

	@Autowired
	@Qualifier("CampeonatoRepository")
	CampeonatoRepository campeoRepo;

	@Autowired
	@Qualifier("KartsConverter")
	KartsConverter converter;

	public Campeonato crearCampeonato(CampeonatoDTO campeonatoDto) {
		Campeonato campeonato = converter.dto2Campeonato(campeonatoDto);
		campeonato = campeoRepo.save(campeonato);

		return campeonato;
	}

	public List<Campeonato> getAllCampeonatos() {
		return campeoRepo.findAll();
	}

	public List<Campeonato> getCampeonato(Integer id) {
		List<Campeonato> list = new ArrayList<>();
		Optional<Campeonato> campeonato = campeoRepo.findById(id);
		if (campeonato.isPresent()) {
			list.add(campeonato.get());
		}
		return list;
	}

	public Campeonato updateCampeonato(Integer id, CampeonatoDTO reglamentoDto) {
		Optional<Campeonato> campOpt = campeoRepo.findById(id);
		if (campOpt.isPresent()) {
			Campeonato campeonato = converter.dto2Campeonato(reglamentoDto);
			campeonato.setIdCampeonato(campOpt.get().getIdCampeonato());
			campeonato = campeoRepo.save(campeonato);
			return campeonato;
		} else {
			return null;
		}
	}

	public boolean borrarCampeonato(Integer id) {
		Optional<Campeonato> campOpt = campeoRepo.findById(id);
		if (campOpt.isPresent()) {
			campeoRepo.delete(campOpt.get());
			return true;
		}else {
			return false;
		}
	}
	
}
