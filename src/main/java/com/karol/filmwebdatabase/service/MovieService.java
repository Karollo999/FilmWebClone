package com.karol.filmwebdatabase.service;

import com.karol.filmwebdatabase.model.Actor;
import com.karol.filmwebdatabase.model.Director;
import com.karol.filmwebdatabase.model.Movie;
import com.karol.filmwebdatabase.model.MovieType;
import com.karol.filmwebdatabase.repository.ActorRepository;
import com.karol.filmwebdatabase.repository.DirectorRepository;
import com.karol.filmwebdatabase.repository.MovieRepository;
import com.karol.filmwebdatabase.repository.MovieTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieTypeRepository movieTypeRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                        ActorRepository actorRepository,
                        DirectorRepository directorRepository,
                        MovieTypeRepository movieTypeRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
        this.directorRepository = directorRepository;
        this.movieTypeRepository = movieTypeRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Transactional
	public Movie saveMovie(Movie movie, List<String> actorNames, String directorName, String typeName,
                       LocalDate directorBirthDate, String directorNationality, String directorBiography,
                       List<LocalDate> actorBirthDates, List<String> actorNationalities, List<String> actorBiographies) {

		actorNames = actorNames != null ? actorNames : Collections.emptyList();
		actorBirthDates = actorBirthDates != null ? actorBirthDates : Collections.emptyList();
		actorNationalities = actorNationalities != null ? actorNationalities : Collections.emptyList();
		actorBiographies = actorBiographies != null ? actorBiographies : Collections.emptyList();
		
        // Handle MovieType
        MovieType movieType = getOrCreateMovieType(typeName);
        movie.setMovieType(movieType);

        // Handle Director
        Director director = getOrCreateDirector(directorName, directorBirthDate, directorNationality, directorBiography);
        movie.setDirector(director);

        // Handle Actors
        Set<Actor> actors = getOrCreateActors(actorNames, actorBirthDates, actorNationalities, actorBiographies);
        movie.setActors(actors);

        return movieRepository.save(movie);
    }

    public List<MovieType> getAllMovieTypes() {
        return movieTypeRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    private MovieType getOrCreateMovieType(String typeName) {
        return movieTypeRepository.findByTypeName(typeName)
                .orElseGet(() -> {
                    MovieType newType = new MovieType();
                    newType.setTypeName(typeName);
                    return movieTypeRepository.save(newType);
                });
    }

    private Director getOrCreateDirector(String fullName, LocalDate birthDate, String nationality, String biography) {
        String[] names = fullName.split(" ", 2);
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";

        return directorRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(director -> {
                    // Update existing director with new info if provided
                    if (birthDate != null) director.setBirthDate(birthDate);
                    if (nationality != null) director.setNationality(nationality);
                    if (biography != null) director.setBiography(biography);
                    return directorRepository.save(director);
                })
                .orElseGet(() -> {
                    Director newDirector = new Director();
                    newDirector.setFirstName(firstName);
                    newDirector.setLastName(lastName);
                    newDirector.setBirthDate(birthDate);
                    newDirector.setNationality(nationality);
                    newDirector.setBiography(biography);
                    return directorRepository.save(newDirector);
                });
    }

    private Set<Actor> getOrCreateActors(List<String> actorNames, List<LocalDate> birthDates,
                                         List<String> nationalities, List<String> biographies) {
        return actorNames.stream()
                .map(name -> {
                    String[] names = name.split(" ", 2);
                    String firstName = names[0];
                    String lastName = names.length > 1 ? names[1] : "";

                    return actorRepository.findByFirstNameAndLastName(firstName, lastName)
                            .map(actor -> {
                                // Update existing actor with new info if provided
                                int index = actorNames.indexOf(name);
                                if (index < birthDates.size()) actor.setBirthDate(birthDates.get(index));
                                if (index < nationalities.size()) actor.setNationality(nationalities.get(index));
                                if (index < biographies.size()) actor.setBiography(biographies.get(index));
                                return actorRepository.save(actor);
                            })
                            .orElseGet(() -> {
                                Actor newActor = new Actor();
                                newActor.setFirstName(firstName);
                                newActor.setLastName(lastName);
                                int index = actorNames.indexOf(name);
                                if (index < birthDates.size()) newActor.setBirthDate(birthDates.get(index));
                                if (index < nationalities.size()) newActor.setNationality(nationalities.get(index));
                                if (index < biographies.size()) newActor.setBiography(biographies.get(index));
                                return actorRepository.save(newActor);
                            });
                })
                .collect(Collectors.toSet());
    }
	
	@Transactional
    public Movie updateMovie(Long id, Movie movieDetails) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setTitle(movieDetails.getTitle());
        movie.setLength(movieDetails.getLength());

        if (movieDetails.getDirector() != null) {
            Director director = getOrCreateDirector(
                movieDetails.getDirector().getFirstName() + " " + movieDetails.getDirector().getLastName(),
                movieDetails.getDirector().getBirthDate(),
                movieDetails.getDirector().getNationality(),
                movieDetails.getDirector().getBiography()
            );
            movie.setDirector(director);
        }

        if (movieDetails.getMovieType() != null) {
            MovieType movieType = getOrCreateMovieType(movieDetails.getMovieType().getTypeName());
            movie.setMovieType(movieType);
        }

        if (movieDetails.getActors() != null) {
            Set<Actor> actors = movieDetails.getActors().stream()
                .map(actor -> getOrCreateActor(
                    actor.getFirstName() + " " + actor.getLastName(),
                    actor.getBirthDate(),
                    actor.getNationality(),
                    actor.getBiography()
                ))
                .collect(Collectors.toSet());
            movie.setActors(actors);
        }

        return movieRepository.save(movie);
    }
	
	private Actor getOrCreateActor(String fullName, LocalDate birthDate, String nationality, String biography) {
        String[] names = fullName.split(" ", 2);
        String firstName = names[0];
        String lastName = names.length > 1 ? names[1] : "";

        return actorRepository.findByFirstNameAndLastName(firstName, lastName)
                .map(actor -> {
                    // Update existing actor with new info if provided
                    if (birthDate != null) actor.setBirthDate(birthDate);
                    if (nationality != null) actor.setNationality(nationality);
                    if (biography != null) actor.setBiography(biography);
                    return actorRepository.save(actor);
                })
                .orElseGet(() -> {
                    Actor newActor = new Actor();
                    newActor.setFirstName(firstName);
                    newActor.setLastName(lastName);
                    newActor.setBirthDate(birthDate);
                    newActor.setNationality(nationality);
                    newActor.setBiography(biography);
                    return actorRepository.save(newActor);
                });
    }
	
	
}
