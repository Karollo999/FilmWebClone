package com.karol.filmwebdatabase.service;

import com.karol.filmwebdatabase.model.MovieType;
import com.karol.filmwebdatabase.repository.MovieTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieTypeService {

    private final MovieTypeRepository movieTypeRepository;

    @Autowired
    public MovieTypeService(MovieTypeRepository movieTypeRepository) {
        this.movieTypeRepository = movieTypeRepository;
    }

    public List<MovieType> getAllMovieTypes() {
        return movieTypeRepository.findAll();
    }

    public Optional<MovieType> getMovieTypeById(Long id) {
        return movieTypeRepository.findById(id);
    }

    public MovieType saveMovieType(MovieType movieType) {
        return movieTypeRepository.save(movieType);
    }

    public void deleteMovieType(Long id) {
        movieTypeRepository.deleteById(id);
    }

    public MovieType updateMovieType(Long id, MovieType movieTypeDetails) {
        MovieType movieType = movieTypeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("MovieType not found"));

        movieType.setTypeName(movieTypeDetails.getTypeName());
        return movieTypeRepository.save(movieType);
    }
}
