package com.karol.filmwebdatabase.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.karol.filmwebdatabase.model.*;
import com.karol.filmwebdatabase.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
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
	
	@Test
    void testAddMovieType() throws Exception {
        MovieType movieType = new MovieType();
        movieType.setTypeName("Sci-Fi");

        when(movieService.saveMovieType(any(MovieType.class))).thenReturn(movieType);

        mockMvc.perform(post("/admin/add-movie-type")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("typeName", "Sci-Fi"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/movie-types"));

        verify(movieService).saveMovieType(any(MovieType.class));
    }

    @Test
    void testShowActorsPage() throws Exception {
        when(actorService.getAllActors()).thenReturn(Arrays.asList(new Actor(), new Actor()));

        mockMvc.perform(get("/admin/actors"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-actors"))
                .andExpect(model().attributeExists("actors"));
    }

    @Test
    void testAddActor() throws Exception {
        Actor actor = new Actor();
        actor.setFirstName("John");
        actor.setLastName("Doe");
        actor.setBirthDate(LocalDate.of(1980, 1, 1));

        when(actorService.saveActor(any(Actor.class))).thenReturn(actor);

        mockMvc.perform(post("/admin/add-actor")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("birthDate", "1980-01-01"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/actors"));

        verify(actorService).saveActor(any(Actor.class));
    }

    @Test
    void testShowDirectorsPage() throws Exception {
        when(directorService.getAllDirectors()).thenReturn(Arrays.asList(new Director(), new Director()));

        mockMvc.perform(get("/admin/directors"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-directors"))
                .andExpect(model().attributeExists("directors"));
    }

    @Test
    void testAddDirector() throws Exception {
        Director director = new Director();
        director.setFirstName("Steven");
        director.setLastName("Spielberg");
        director.setBirthDate(LocalDate.of(1946, 12, 18));

        when(directorService.saveDirector(any(Director.class))).thenReturn(director);

        mockMvc.perform(post("/admin/add-director")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("firstName", "Steven")
                .param("lastName", "Spielberg")
                .param("birthDate", "1946-12-18"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/directors"));

        verify(directorService).saveDirector(any(Director.class));
    }
	
	@Test
	void testAddMovie() throws Exception {
		Movie movie = new Movie();
		movie.setTitle("Inception");
		movie.setLength(148);

		MovieType movieType = new MovieType();
		movieType.setId(1L);
		movieType.setTypeName("Sci-Fi");

		when(movieService.getMovieTypeById(1L)).thenReturn(Optional.of(movieType));
		when(movieService.saveMovie(
				any(Movie.class),
				anyList(),
				anyString(),
				anyString(),
				any(),
				anyString(),
				anyString(),
				anyList(),
				anyList(),
				anyList()
		)).thenReturn(movie);

		mockMvc.perform(post("/admin/add-movie")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("title", "Inception")
				.param("length", "148")
				.param("movieTypeId", "1")
				.param("directorName", "Christopher Nolan")
				.param("actorNames", "Leonardo DiCaprio,Ellen Page")
				.param("directorBirthDate", "1970-07-30")
				.param("directorNationality", "British")
				.param("directorBiography", "Christopher Nolan is a British-American film director...")
				.param("actorBirthDates", "1974-11-11,1987-02-21")
				.param("actorNationalities", "American,Canadian")
				.param("actorBiographies", "Leonardo DiCaprio is an American actor...,Ellen Page is a Canadian actress...")
		)
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/admin"));

		verify(movieService).saveMovie(
				argThat(m -> m.getTitle().equals("Inception") && m.getLength() == 148),
				eq(Arrays.asList("Leonardo DiCaprio", "Ellen Page")),
				eq("Christopher Nolan"),
				eq("Sci-Fi"),
				eq(LocalDate.of(1970, 7, 30)),
				eq("British"),
				eq("Christopher Nolan is a British-American film director..."),
				eq(Arrays.asList(LocalDate.of(1974, 11, 11), LocalDate.of(1987, 2, 21))),
				eq(Arrays.asList("American", "Canadian")),
				eq(Arrays.asList("Leonardo DiCaprio is an American actor...", "Ellen Page is a Canadian actress..."))
		);
	}

	
}
