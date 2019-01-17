package com.example.dvdexample.bussiness;

import com.example.dvdexample.dao.FilmDao;
import com.example.dvdexample.model.Film;
import com.example.dvdexample.model.Inventory;
import com.example.dvdexample.model.Payment;
import com.example.dvdexample.model.Rental;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class FilmReportingServiceImpl implements FilmReportingService {
    private FilmDao filmDao;

    @Override
    public Map<Film, Map<LocalDate, BigDecimal>> filmFinancialReport() {
        Map<Film, Map<LocalDate, BigDecimal>> results = new HashMap<>();

        List<Film> films = filmDao.findAll();
        for (Film film : films) {
            Set<Inventory> inventories = film.getInventories();

            for (Inventory inventory : inventories) {
                Set<Rental> rentals = inventory.getRentals();

                for (Rental rental : rentals) {
                    Payment payment = rental.getPayment();

                    if (payment != null) {
                        Map<LocalDate, BigDecimal> moneyPerDay = results.computeIfAbsent(film, f -> new HashMap<>());
                        Timestamp paymentDate = payment.getPaymentDate();
                        LocalDate day = paymentDate.toLocalDateTime().toLocalDate();

                        BigDecimal sum = moneyPerDay.computeIfAbsent(day, localDate -> BigDecimal.ZERO);
                        moneyPerDay.put(day, sum.add(payment.getAmount()));
                    }
                }
            }
        }

        return results;
    }

    @Override
    public List<ProfitReport> filmFinancialReportSQL() {
        return filmDao.getProfits();
    }

    @Override
    public List<ProfitReport> filmFinancialReportNative() {
        return filmDao.getProfitsNative();
    }
}
