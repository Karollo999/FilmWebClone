package com.karol.filmwebdatabase.repository;

import com.karol.filmwebdatabase.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findByFirstNameAndLastName(String firstName, String lastName);

    List<Actor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndBirthDate(
        String firstName, String lastName, LocalDate birthDate
    );

    List<Actor> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrMovies_TitleContainingIgnoreCase(
        String firstName, String lastName, String movieTitle
    );
}
