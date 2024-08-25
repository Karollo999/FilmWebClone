package com.karol.filmwebdatabase.controller;

import com.karol.filmwebdatabase.model.Movie;
import com.karol.filmwebdatabase.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        return movie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movie createMovie(
            @RequestBody Movie movie,
            @RequestParam List<String> actorNames,
            @RequestParam String directorName,
            @RequestParam String typeName,
            @RequestParam(required = false) LocalDate directorBirthDate,
            @RequestParam(required = false) String directorNationality,
            @RequestParam(required = false) String directorBiography,
            @RequestParam(required = false) List<LocalDate> actorBirthDates,
            @RequestParam(required = false) List<String> actorNationalities,
            @RequestParam(required = false) List<String> actorBiographies) {

        return movieService.saveMovie(movie, actorNames, directorName, typeName,
                directorBirthDate, directorNationality, directorBiography,
                actorBirthDates, actorNationalities, actorBiographies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id, @RequestBody Movie movieDetails) {
        try {
            Movie updatedMovie = movieService.updateMovie(id, movieDetails);
            return ResponseEntity.ok(updatedMovie);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok().build();
    }
}
