package com.karol.filmwebdatabase.service;

import com.karol.filmwebdatabase.model.Movie;
import com.karol.filmwebdatabase.model.Director;
import com.karol.filmwebdatabase.model.Actor;
import com.karol.filmwebdatabase.model.MovieType;
import com.karol.filmwebdatabase.repository.MovieRepository;
import com.karol.filmwebdatabase.repository.DirectorRepository;
import com.karol.filmwebdatabase.repository.ActorRepository;
import com.karol.filmwebdatabase.repository.MovieTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private DirectorRepository directorRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private MovieTypeRepository movieTypeRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMovies() {
        when(movieRepository.findAll()).thenReturn(Arrays.asList(new Movie(), new Movie()));
        assertEquals(2, movieService.getAllMovies().size());
    }

    @Test
    void testGetMovieById() {
        Movie movie = new Movie();
        movie.setId(1L);
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        assertTrue(movieService.getMovieById(1L).isPresent());
        assertEquals(1L, movieService.getMovieById(1L).get().getId());
    }

    @Test
	void testSaveMovie() {
		Movie movie = new Movie();
		movie.setTitle("Test Movie");
		when(movieRepository.save(any(Movie.class))).thenReturn(movie);

		List<String> actorNames = Arrays.asList("Actor 1");
		List<LocalDate> actorBirthDates = Arrays.asList(LocalDate.now());
		List<String> actorNationalities = Arrays.asList("Nationality");
		List<String> actorBiographies = Arrays.asList("Biography");

		Movie savedMovie = movieService.saveMovie(
			movie,
			actorNames,
			"Director 1",
			"Action",
			LocalDate.now(),
			"Director Nationality",
			"Director Biography",
			actorBirthDates,
			actorNationalities,
			actorBiographies
		);
		assertEquals("Test Movie", savedMovie.getTitle());
	}


    @Test
    void testUpdateMovie() {
        Movie existingMovie = new Movie();
        existingMovie.setId(1L);
        existingMovie.setTitle("Old Title");

        Movie updatedMovie = new Movie();
        updatedMovie.setId(1L);
        updatedMovie.setTitle("New Title");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(existingMovie));
        when(movieRepository.save(any(Movie.class))).thenReturn(updatedMovie);

        Movie result = movieService.updateMovie(1L, updatedMovie);
        assertEquals("New Title", result.getTitle());
    }

    @Test
    void testDeleteMovie() {
        movieService.deleteMovie(1L);
        verify(movieRepository, times(1)).deleteById(1L);
    }

}
