package com.karts.back.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.karts.back.component.KartsConverter;
import com.karts.back.dto.ReglamentoDTO;
import com.karts.back.entity.Reglamento;
import com.karts.back.repository.ReglamentoRepository;
import com.karts.back.services.ReglamentoService;

@Service("ReglamentoService")
public class ReglamentoServiceImpl implements ReglamentoService {

	@Autowired
	@Qualifier("ReglamentoRepository")
	ReglamentoRepository reglaRepo;

	@Autowired
	@Qualifier("KartsConverter")
	KartsConverter converter;

	public Reglamento crearReglamento(ReglamentoDTO reglamentoDto) {

		Reglamento reglamento = converter.dto2Reglamento(reglamentoDto);
		reglamento = reglaRepo.save(reglamento);

		return reglamento;
	}

	public List<Reglamento> getAllReglamentos() {
		return reglaRepo.findAll();
	}

	public List<Reglamento> getReglamento(Integer id) {
		List<Reglamento> list = new ArrayList<>();
		Optional<Reglamento> reglamento = reglaRepo.findById(id);
		if (reglamento.isPresent()) {
			list.add(reglamento.get());
		}
		return list;
	}

	public Reglamento updateReglamento(Integer id, ReglamentoDTO reglamentoDto) {
		Optional<Reglamento> regOpt = reglaRepo.findById(id);
		if (regOpt.isPresent()) {
			Reglamento reglamento = converter.dto2Reglamento(reglamentoDto);
			reglamento.setId(regOpt.get().getId());
			reglamento = reglaRepo.save(reglamento);
			return reglamento;
		} else {
			return null;
		}

	}
	
	public boolean borrarReglamento(Integer id) {
		Optional<Reglamento> regOpt = reglaRepo.findById(id);
		if (regOpt.isPresent()) {
			reglaRepo.delete(regOpt.get());
			return true;
		}else {
			return false;
		}
	}
}
