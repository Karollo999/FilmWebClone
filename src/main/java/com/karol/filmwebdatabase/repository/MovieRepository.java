package com.karol.filmwebdatabase.repository;

import com.karol.filmwebdatabase.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCaseOrDirector_FirstNameContainingIgnoreCaseOrDirector_LastNameContainingIgnoreCaseOrActors_FirstNameContainingIgnoreCaseOrActors_LastNameContainingIgnoreCaseOrMovieType_TypeNameContainingIgnoreCaseAndLengthBetween(
        String title, String directorFirstName, String directorLastName,
        String actorFirstName, String actorLastName, String genre,
        int minLength, int maxLength
    );
}
