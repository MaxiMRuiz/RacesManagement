package com.karts.back.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.karts.back.component.KartsConverter;
import com.karts.back.dto.EquipoDTO;
import com.karts.back.entity.Equipo;
import com.karts.back.repository.EquipoRepository;
import com.karts.back.services.EquipoService;

@Service("EquipoService")
public class EquipoServiceImpl implements EquipoService {

	@Autowired
	@Qualifier("EquipoRepository")
	EquipoRepository equipoRepo;

	@Autowired
	@Qualifier("KartsConverter")
	KartsConverter converter;

	public Equipo crearEquipo(EquipoDTO equipoDto) {
		Equipo equipo = converter.dto2Equipo(equipoDto);
		equipo = equipoRepo.save(equipo);

		return equipo;
	}

	public List<Equipo> getAllEquipos() {
		return equipoRepo.findAll();
	}

	public List<Equipo> getEquipo(String alias) {
		List<Equipo> list = new ArrayList<>();
		Optional<Equipo> equipo = equipoRepo.findByAlias(alias);
		if (equipo.isPresent()) {
			list.add(equipo.get());
		}
		return list;
	}

	public Equipo updateEquipo(String alias, EquipoDTO equipoDto) {
		Optional<Equipo> equipoOpt = equipoRepo.findByAlias(alias);
		if (equipoOpt.isPresent()) {
			Equipo equipo = converter.dto2Equipo(equipoDto);
			equipo = equipoRepo.save(equipo);
			return equipo;
		} else {
			return null;
		}
	}

	public boolean borrarEquipo(String alias) {
		Optional<Equipo> equipoOpt = equipoRepo.findByAlias(alias);
		if (equipoOpt.isPresent()) {
			equipoRepo.delete(equipoOpt.get());
			return true;
		}else {
			return false;
		}
	}

}
