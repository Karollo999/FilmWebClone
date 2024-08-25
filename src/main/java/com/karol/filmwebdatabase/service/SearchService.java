package com.karol.filmwebdatabase.service;

import com.karol.filmwebdatabase.model.Actor;
import com.karol.filmwebdatabase.model.Director;
import com.karol.filmwebdatabase.model.Movie;
import com.karol.filmwebdatabase.repository.ActorRepository;
import com.karol.filmwebdatabase.repository.DirectorRepository;
import com.karol.filmwebdatabase.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private DirectorRepository directorRepository;

    public List<Movie> searchMovies(String query, Integer minLength, Integer maxLength, String genre) {
        return movieRepository.findByTitleContainingIgnoreCaseOrDirector_FirstNameContainingIgnoreCaseOrDirector_LastNameContainingIgnoreCaseOrActors_FirstNameContainingIgnoreCaseOrActors_LastNameContainingIgnoreCaseOrMovieType_TypeNameContainingIgnoreCaseAndLengthBetween(
            query, query, query, query, query, genre,
            minLength != null ? minLength : 0,
            maxLength != null ? maxLength : Integer.MAX_VALUE
        );
    }

    public List<Actor> searchActors(String query, LocalDate birthDate) {
        if (birthDate != null) {
            return actorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndBirthDate(query, query, birthDate);
        } else {
            return actorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrMovies_TitleContainingIgnoreCase(query, query, query);
        }
    }

    public List<Director> searchDirectors(String query, LocalDate birthDate) {
        if (birthDate != null) {
            return directorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseAndBirthDate(query, query, birthDate);
        } else {
            return directorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrMovies_TitleContainingIgnoreCase(query, query, query);
        }
    }
}
