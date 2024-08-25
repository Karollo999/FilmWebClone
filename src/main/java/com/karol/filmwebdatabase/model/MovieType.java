package com.karol.filmwebdatabase.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class MovieType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;

    @OneToMany(mappedBy = "movieType")
    private Set<Movie> movies = new HashSet<>();

    // Constructors
    public MovieType() {}

    public MovieType(String typeName) {
        this.typeName = typeName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }
}
