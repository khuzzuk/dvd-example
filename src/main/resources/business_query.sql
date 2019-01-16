SELECT f.film_id, p.payment_date, SUM(p.amount)
FROM film f
  JOIN inventory i ON f.film_id = i.film_id
  JOIN rental r ON i.inventory_id = r.inventory_id
  JOIN payment p ON r.rental_id = p.rental_id
GROUP BY f.film_id, p.payment_date
ORDER BY f.title, p.payment_date;




