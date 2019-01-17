--revenue per film
SELECT f.film_id, sum(p.amount)
FROM film f
       JOIN inventory i ON f.film_id = i.film_id
       JOIN rental r ON i.inventory_id = r.inventory_id
       JOIN payment p ON r.rental_id = p.rental_id
GROUP BY f.film_id
ORDER BY f.title;

--revenue per film per day
SELECT f.film_id, p.payment_date, SUM(p.amount)
FROM film f
  JOIN inventory i ON f.film_id = i.film_id
  JOIN rental r ON i.inventory_id = r.inventory_id
  JOIN payment p ON r.rental_id = p.rental_id
GROUP BY f.film_id, p.payment_date
ORDER BY f.title, p.payment_date;

--revenue per film per day per store
SELECT f.film_id, i.store_id, p.payment_date, SUM(p.amount)
FROM film f
  JOIN inventory i ON f.film_id = i.film_id
  JOIN rental r ON i.inventory_id = r.inventory_id
  JOIN payment p ON r.rental_id = p.rental_id
GROUP BY f.film_id, i.store_id, p.payment_date
ORDER BY f.title, i.store_id, p.payment_date;

--daily cumulative revenue per film per day per store
SELECT f.film_id, i.store_id, p.payment_date,
  SUM(SUM(p.amount)) OVER (
  PARTITION BY f.film_id, i.store_id
  ORDER BY p.payment_date
  )
FROM film f
  JOIN inventory i ON f.film_id = i.film_id
  JOIN rental r ON i.inventory_id = r.inventory_id
  JOIN payment p ON r.rental_id = p.rental_id
GROUP BY f.film_id, i.store_id, p.payment_date
ORDER BY f.title, i.store_id, p.payment_date;
