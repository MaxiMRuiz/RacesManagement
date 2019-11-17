package com.races.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.races.entity.Campeonato;

@Repository("CampeonatoRepository")
public interface CampeonatoRepository extends JpaRepository<Campeonato, Serializable>{

}