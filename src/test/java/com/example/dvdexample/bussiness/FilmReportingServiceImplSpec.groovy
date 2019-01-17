package com.example.dvdexample.bussiness

import ch.qos.logback.core.util.SystemInfo
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
        given:
        long start = System.nanoTime()
        println 'used memory: ' + reportMemory() / 1_000_000

        when:
        def report = filmReportingService.filmFinancialReport()
        def t = System.nanoTime() - start
        println 'worked millis: ' + t / 1_000_000
        println 'used memory: ' + reportMemory() / 1_000_000

        then:
        report.values().size() == 958
    }

    def "check jpql query"() {
        given:
        long start = System.nanoTime()
        println 'used memory: ' + reportMemory() / 1_000_000

        when:
        def report = filmReportingService.filmFinancialReportSQL()
        def t = System.nanoTime() - start
        println 'worked millis: ' + t / 1_000_000
        println 'used memory: ' + reportMemory() / 1_000_000

        then:
        report.size() == 14582
    }

    def "check native sql query"() {
        given:
        long start = System.nanoTime()
        println 'used memory: ' + reportMemory() / 1_000_000

        when:
        def report = filmReportingService.filmFinancialReportNative()
        def t = System.nanoTime() - start
        println 'worked millis: ' + t / 1_000_000
        println 'used memory: ' + reportMemory() / 1_000_000

        then:
        report.size() == 14582
    }

    static long reportMemory() {
        Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()
    }
}
