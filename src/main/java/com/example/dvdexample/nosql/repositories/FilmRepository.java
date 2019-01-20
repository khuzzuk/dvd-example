package com.example.dvdexample.nosql.repositories;

import com.example.dvdexample.model.Film;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FilmRepository extends MongoRepository<Film, Long> {
}
