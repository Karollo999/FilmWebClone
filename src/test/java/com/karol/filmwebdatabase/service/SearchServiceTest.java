package com.karol.filmwebdatabase.service;

import com.karol.filmwebdatabase.model.Movie;
import com.karol.filmwebdatabase.model.Actor;
import com.karol.filmwebdatabase.model.Director;
import com.karol.filmwebdatabase.repository.MovieRepository;
import com.karol.filmwebdatabase.repository.ActorRepository;
import com.karol.filmwebdatabase.repository.DirectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private DirectorRepository directorRepository;

    @InjectMocks
    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSearchMovies() {
        when(movieRepository.findByTitleContainingIgnoreCaseOrDirector_FirstNameContainingIgnoreCaseOrDirector_LastNameContainingIgnoreCaseOrActors_FirstNameContainingIgnoreCaseOrActors_LastNameContainingIgnoreCaseOrMovieType_TypeNameContainingIgnoreCaseAndLengthBetween(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt(), anyInt()
        )).thenReturn(Arrays.asList(new Movie(), new Movie()));

        assertEquals(2, searchService.searchMovies("Test", 60, 180, "Action").size());
    }

    @Test
    void testSearchActors() {
        when(actorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrMovies_TitleContainingIgnoreCase(
                anyString(), anyString(), anyString()
        )).thenReturn(Arrays.asList(new Actor(), new Actor()));

        assertEquals(2, searchService.searchActors("John", null).size());
    }

    @Test
    void testSearchDirectors() {
        when(directorRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrMovies_TitleContainingIgnoreCase(
                anyString(), anyString(), anyString()
        )).thenReturn(Arrays.asList(new Director(), new Director()));

        assertEquals(2, searchService.searchDirectors("Steven", null).size());
    }
}
