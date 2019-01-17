package com.example.dvdexample.bussiness;

import com.example.dvdexample.model.Film;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface FilmReportingService {
    Map<Film, Map<LocalDate, BigDecimal>> filmFinancialReport();
    List<ProfitReport> filmFinancialReportSQL();
    List<ProfitReport> filmFinancialReportNative();
}
