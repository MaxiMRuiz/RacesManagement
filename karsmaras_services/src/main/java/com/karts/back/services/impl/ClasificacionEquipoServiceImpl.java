package com.karts.back.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.karts.back.component.KartsConverter;
import com.karts.back.dto.ClasificacionEquiposDTO;
import com.karts.back.entity.ClasificacionEquipos;
import com.karts.back.services.ClasificacionEquipoService;

@Service("ClasificacionEquipoService")
public class ClasificacionEquipoServiceImpl implements ClasificacionEquipoService {

	@Autowired
	@Qualifier("KartsConverter")
	KartsConverter converter;

	public ClasificacionEquipos crearClasificacionEquipos(ClasificacionEquiposDTO clasiEquiposDto) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClasificacionEquipos> getAllClasificacionEquipos() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClasificacionEquipos> getClasificacionEquipos(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ClasificacionEquipos updateClasificacionEquipos(Integer id, ClasificacionEquiposDTO clasiEquiposDto) {
		// TODO Auto-generated method stub
		return null;
	}

	public void borrarClasificacionEquipos(Integer id) {
		// TODO Auto-generated method stub
		
	}


}
