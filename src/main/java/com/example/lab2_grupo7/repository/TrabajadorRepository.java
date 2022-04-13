package com.example.lab2_grupo7.repository;

import com.example.lab2_grupo7.entity.Trabajador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrabajadorRepository extends JpaRepository<Trabajador,String> {

    @Query(value = "select * from trabajadores where idsede = ?1", nativeQuery = true)
    List<Trabajador> buscarPorSede(Integer idSede);

}
