package com.races.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.races.entity.Piloto;

@Repository("PilotoRepository")
public interface PilotoRepository extends JpaRepository<Piloto, Serializable>{

}