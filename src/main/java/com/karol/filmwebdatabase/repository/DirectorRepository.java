package com.karol.filmwebdatabase.repository;

import com.karol.filmwebdatabase.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Long> {
    Optional<Director> findByFirstNameAndLastName(String firstName, String lastName);

    List<Director> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndBirthDate(
        String firstName, String lastName, LocalDate birthDate
    );

    List<Director> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrMovies_TitleContainingIgnoreCase(
        String firstName, String lastName, String movieTitle
    );
}
