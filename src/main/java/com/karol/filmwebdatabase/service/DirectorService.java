package com.karol.filmwebdatabase.service;

import com.karol.filmwebdatabase.model.Director;
import com.karol.filmwebdatabase.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectorService {

    private final DirectorRepository directorRepository;

    @Autowired
    public DirectorService(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    public List<Director> getAllDirectors() {
        return directorRepository.findAll();
    }

    public Optional<Director> getDirectorById(Long id) {
        return directorRepository.findById(id);
    }

    public Director saveDirector(Director director) {
        return directorRepository.save(director);
    }

    public void deleteDirector(Long id) {
        directorRepository.deleteById(id);
    }

    public Director updateDirector(Long id, Director directorDetails) {
        Director director = directorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Director not found"));

        director.setFirstName(directorDetails.getFirstName());
        director.setLastName(directorDetails.getLastName());
        director.setMovies(directorDetails.getMovies());

        return directorRepository.save(director);
    }
}
