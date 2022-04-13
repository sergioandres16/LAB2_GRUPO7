package com.example.lab2_grupo7.repository;

import com.example.lab2_grupo7.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SedeRepository extends JpaRepository<Sede,Integer> {
}
