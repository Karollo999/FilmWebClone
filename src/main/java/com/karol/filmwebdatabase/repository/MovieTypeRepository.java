package com.karol.filmwebdatabase.repository;

import com.karol.filmwebdatabase.model.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
    Optional<MovieType> findByTypeName(String typeName);
}
