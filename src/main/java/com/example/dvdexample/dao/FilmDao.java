package com.example.dvdexample.dao;

import com.example.dvdexample.bussiness.ProfitReport;
import com.example.dvdexample.model.Film;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Repository
public class FilmDao {
    private static final String NATIVE_QUERY = "profitNative";

    private EntityManager entityManager;

    public List<Film> findAll() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Film> query = builder.createQuery(Film.class);
        Root<Film> root = query.from(Film.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public List<ProfitReport> getProfits() {
        return entityManager.createQuery("SELECT new com.example.dvdexample.bussiness.ProfitReport(f.id, p.paymentDate, SUM(p.amount)) " +
                "FROM Film f " +
                "   JOIN f.inventories i " +
                "   JOIN i.rentals r " +
                "   JOIN r.payment p " +
                "GROUP BY f, p.paymentDate " +
                "ORDER BY f.id", ProfitReport.class).getResultList();
    }

    public List<ProfitReport> getProfitsNative() {
        List<Object[]> resultList = entityManager.createNativeQuery("SELECT f.film_id AS title, p.payment_date as paymentDate, sum(p.amount) AS profit " +
                "FROM film f " +
                "   JOIN inventory i ON f.film_id = i.film_id " +
                "   JOIN rental r ON i.inventory_id = r.inventory_id " +
                "   JOIN payment p ON r.rental_id = p.rental_id " +
                "GROUP BY f.film_id, p.payment_date").getResultList();
        return resultList.stream()
                .map(record -> new ProfitReport((Integer) record[0], (Date) record[1], (BigDecimal) record[2]))
                .collect(Collectors.toList());
    }
}
