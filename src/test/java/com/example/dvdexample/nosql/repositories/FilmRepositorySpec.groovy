package com.example.dvdexample.nosql.repositories

import com.example.dvdexample.model.Film
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class FilmRepositorySpec extends Specification {
    @Autowired
    FilmRepository filmRepository
    def "check repo saving"() {
        given:
        Film film = new Film()
        film.title = 'title'

        when:
        filmRepository.save(film)

        then:
        filmRepository.findAll().size() > 0
    }
}
