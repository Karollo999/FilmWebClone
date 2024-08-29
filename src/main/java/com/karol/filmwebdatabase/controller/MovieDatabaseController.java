package com.karol.filmwebdatabase.controller;

import com.karol.filmwebdatabase.model.Actor;
import com.karol.filmwebdatabase.model.Director;
import com.karol.filmwebdatabase.model.Movie;
import com.karol.filmwebdatabase.model.MovieType;
import com.karol.filmwebdatabase.service.ActorService;
import com.karol.filmwebdatabase.service.DirectorService;
import com.karol.filmwebdatabase.service.MovieService;
import com.karol.filmwebdatabase.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MovieDatabaseController {

    private final MovieService movieService;
    private final ActorService actorService;
    private final DirectorService directorService;
    private final SearchService searchService;

    @Autowired
    public MovieDatabaseController(MovieService movieService,
                                   ActorService actorService,
                                   DirectorService directorService,
                                   SearchService searchService) {
        this.movieService = movieService;
        this.actorService = actorService;
        this.directorService = directorService;
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String mainPage(Model model) {
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "main";
    }

    @GetMapping("/search")
    public String showSearchForm() {
        return "search";
    }

    @PostMapping("/search")
    public String performSearch(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Integer minLength,
            @RequestParam(required = false) Integer maxLength,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) LocalDate birthDate,
            Model model) {

        List<Movie> movies = searchService.searchMovies(query, minLength, maxLength, genre);
        List<Actor> actors = searchService.searchActors(query, birthDate);
        List<Director> directors = searchService.searchDirectors(query, birthDate);

        model.addAttribute("movies", movies);
        model.addAttribute("actors", actors);
        model.addAttribute("directors", directors);

        return "search-results";
    }

    @GetMapping("/movie/{id}")
    public String showMovieDetails(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        model.addAttribute("movie", movie);
        return "movie-details";
    }

    @GetMapping("/actor/{id}")
    public String showActorDetails(@PathVariable Long id, Model model) {
        Actor actor = actorService.getActorById(id)
                .orElseThrow(() -> new RuntimeException("Actor not found"));
        model.addAttribute("actor", actor);
        return "actor-details";
    }

    @GetMapping("/director/{id}")
    public String showDirectorDetails(@PathVariable Long id, Model model) {
        Director director = directorService.getDirectorById(id)
                .orElseThrow(() -> new RuntimeException("Director not found"));
        model.addAttribute("director", director);
        return "director-details";
    }

    // Admin functionalities

    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("movies", movieService.getAllMovies());
        return "admin";
    }

    @GetMapping("/admin/edit-movie/{id}")
    public String editMoviePage(@PathVariable Long id, Model model) {
        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        model.addAttribute("movie", movie);
        return "edit-movie";
    }

    @PostMapping("/admin/save-movie")
    public String saveMovie(
            @ModelAttribute Movie movie,
            @RequestParam(required = false) List<String> actorNames,
            @RequestParam(required = false) String directorName,
            @RequestParam(required = false) String typeName,
            @RequestParam(required = false) LocalDate directorBirthDate,
            @RequestParam(required = false) String directorNationality,
            @RequestParam(required = false) String directorBiography,
            @RequestParam(required = false) List<LocalDate> actorBirthDates,
            @RequestParam(required = false) List<String> actorNationalities,
            @RequestParam(required = false) List<String> actorBiographies) {

        movieService.saveMovie(movie, actorNames, directorName, typeName,
                directorBirthDate, directorNationality, directorBiography,
                actorBirthDates, actorNationalities, actorBiographies);
        return "redirect:/admin";
    }

    @GetMapping("/admin/add-movie")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("movieTypes", movieService.getAllMovieTypes());
        return "add-movie";
    }

    @PostMapping("/admin/add-movie")
    public String addMovie(@ModelAttribute Movie movie) {
        movieService.addMovie(movie);
        return "redirect:/admin";
    }

    @GetMapping("/admin/delete-movie/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "redirect:/admin";
    }
	
	@GetMapping("/admin/actors")
	public String showActorsList(Model model) {
		model.addAttribute("actors", actorService.getAllActors());
		return "admin-actors";
	}

	@GetMapping("/admin/add-actor")
	public String showAddActorForm(Model model) {
		model.addAttribute("actor", new Actor());
		return "add-actor";
	}

	@PostMapping("/admin/add-actor")
	public String addActor(@ModelAttribute Actor actor) {
		actorService.saveActor(actor);
		return "redirect:/admin/actors";
	}
	
	@GetMapping("/admin/edit-actor/{id}")
	public String showEditActorForm(@PathVariable Long id, Model model) {
		Actor actor = actorService.getActorById(id)
				.orElseThrow(() -> new RuntimeException("Actor not found"));
		model.addAttribute("actor", actor);
		return "edit-actor";
	}

	@PostMapping("/admin/edit-actor/{id}")
	public String editActor(@PathVariable Long id, @ModelAttribute Actor actor) {
		actorService.updateActor(id, actor);
		return "redirect:/admin/actors";
	}

	@GetMapping("/admin/delete-actor/{id}")
	public String deleteActor(@PathVariable Long id) {
		actorService.deleteActor(id);
		return "redirect:/admin/actors";
	}
	
	@GetMapping("/admin/directors")
	public String showDirectorsList(Model model) {
		model.addAttribute("directors", directorService.getAllDirectors());
		return "admin-directors";
	}

	@GetMapping("/admin/add-director")
	public String showAddDirectorForm(Model model) {
		model.addAttribute("director", new Director());
		return "add-director";
	}

	@PostMapping("/admin/add-director")
	public String addDirector(@ModelAttribute Director director) {
		directorService.saveDirector(director);
		return "redirect:/admin/directors";
	}

	@GetMapping("/admin/edit-director/{id}")
	public String showEditDirectorForm(@PathVariable Long id, Model model) {
		Director director = directorService.getDirectorById(id)
				.orElseThrow(() -> new RuntimeException("Director not found"));
		model.addAttribute("director", director);
		return "edit-director";
	}

	@PostMapping("/admin/edit-director/{id}")
	public String editDirector(@PathVariable Long id, @ModelAttribute Director director) {
		directorService.updateDirector(id, director);
		return "redirect:/admin/directors";
	}

	@GetMapping("/admin/delete-director/{id}")
	public String deleteDirector(@PathVariable Long id) {
		directorService.deleteDirector(id);
		return "redirect:/admin/directors";
	}

		
}
