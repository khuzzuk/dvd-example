package com.example.dvdexample.bussiness


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Stepwise

@SpringBootTest
@Stepwise
class FilmReportingServiceImplSpec extends Specification {
    @Autowired
    FilmReportingServiceImpl filmReportingService

    def "init"() {
        expect:
        filmReportingService
    }

    def "check pure java reporting"() {
        when:
        def report = filmReportingService.filmFinancialReport()

        then:
        report.values().size() == 958
    }

    def "check jpql query"() {
        when:
        def report = filmReportingService.filmFinancialReportSQL()

        then:
        report.size() == 14582
    }

    def "check native sql query"() {
        when:
        def report = filmReportingService.filmFinancialReportNative()

        then:
        report.size() == 14582
    }
    }
