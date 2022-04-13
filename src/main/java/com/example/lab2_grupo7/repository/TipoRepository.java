package com.example.lab2_grupo7.repository;

import com.example.lab2_grupo7.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Integer>{
}
