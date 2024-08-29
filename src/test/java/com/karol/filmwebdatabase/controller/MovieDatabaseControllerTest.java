package com.karol.filmwebdatabase.controller;

import com.karol.filmwebdatabase.model.Movie;
import com.karol.filmwebdatabase.service.MovieService;
import com.karol.filmwebdatabase.service.ActorService;
import com.karol.filmwebdatabase.service.DirectorService;
import com.karol.filmwebdatabase.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MovieDatabaseControllerTest {

    @Mock
    private MovieService movieService;

    @Mock
    private ActorService actorService;

    @Mock
    private DirectorService directorService;

    @Mock
    private SearchService searchService;

    @InjectMocks
    private MovieDatabaseController movieDatabaseController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(movieDatabaseController)
                .setViewResolvers(viewResolver)
                .build();
    }
	
    @Test
    void testMainPage() throws Exception {
        when(movieService.getAllMovies()).thenReturn(Arrays.asList(new Movie(), new Movie()));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("main"))
                .andExpect(model().attributeExists("movies"));
    }

    @Test
    void testShowMovieDetails() throws Exception {
        Movie movie = new Movie();
        movie.setId(1L);
        when(movieService.getMovieById(1L)).thenReturn(Optional.of(movie));

        mockMvc.perform(get("/movie/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("movie-details"))
                .andExpect(model().attributeExists("movie"));
    }

    @Test
    void testShowSearchForm() throws Exception {
        mockMvc.perform(get("/search"))
                .andExpect(status().isOk())
                .andExpect(view().name("search"));
    }

    @Test
    void testPerformSearch() throws Exception {
        when(searchService.searchMovies(anyString(), anyInt(), anyInt(), anyString()))
                .thenReturn(Arrays.asList(new Movie(), new Movie()));

        mockMvc.perform(post("/search")
                .param("query", "Test")
                .param("minLength", "60")
                .param("maxLength", "180")
                .param("genre", "Action"))
                .andExpect(status().isOk())
                .andExpect(view().name("search-results"))
                .andExpect(model().attributeExists("movies"));
    }
	
}
