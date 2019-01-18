-- relational algebra notation calc
SELECT f.title
FROM film f
WHERE f.film_id IN (
  SELECT DISTINCT fa_res.film_id
  FROM film_actor fa_res EXCEPT
  SELECT t3.film_id
  FROM (SELECT t1.film_id, t2.actor_id
        FROM (SELECT * FROM film_actor) AS t1,
             (SELECT * FROM actor a WHERE a.actor_id IN (28, 37)) AS t2
             EXCEPT
        SELECT fa_t1.film_id, fa_t1.actor_id
        FROM film_actor fa_t1) AS t3);

-- by logical tautology
SELECT DISTINCT f.title
FROM film f
WHERE f.film_id IN (
  SELECT fa_g.film_id
  FROM film_actor fa_g
  WHERE NOT EXISTS(SELECT 1
                   FROM actor a
                   WHERE a.actor_id IN (28, 37)
                     AND NOT EXISTS(SELECT 1
                                    FROM film_actor fa_l
                                    WHERE fa_l.actor_id = a.actor_id
                                      AND fa_l.film_id = fa_g.film_id)));

--set containment instead of double negation
SELECT DISTINCT f.title
FROM film f
WHERE f.film_id IN (
  SELECT fa_g.film_id
  FROM film_actor fa_g
  WHERE NOT EXISTS(SELECT a.actor_id
                   FROM actor a
                   WHERE a.actor_id IN (28, 37) EXCEPT
                   SELECT fa_l.actor_id
                   FROM film_actor fa_l, actor a_l
                   WHERE fa_l.actor_id = a_l.actor_id
                     AND fa_l.film_id = fa_g.film_id));

SELECT f.title
FROM film f
WHERE NOT EXISTS(SELECT 1
                 FROM film_actor fa_a
                 WHERE fa_a.actor_id IN (28, 37)
                   AND NOT fa_a.actor_id IN (SELECT fa_b.actor_id FROM film_actor fa_b WHERE fa_b.film_id = f.film_id));

--by counting
SELECT f.title
FROM film f
       JOIN film_actor fa ON f.film_id = fa.film_id
       JOIN actor a ON fa.actor_id = a.actor_id
WHERE fa.actor_id IN (28, 37)
GROUP BY f.title
HAVING COUNT(a.actor_id) = (SELECT COUNT(*) FROM actor a_inner WHERE a_inner.actor_id IN (28, 37));

SELECT fa.film_id
FROM film_actor fa
       JOIN actor a ON fa.actor_id = a.actor_id
WHERE a.actor_id IN (28, 37)
GROUP BY fa.film_id
HAVING COUNT(a.actor_id) = 2;

SELECT f.title
FROM film f
       JOIN film_actor fa ON f.film_id = fa.film_id
GROUP BY f.film_id
HAVING ARRAY_AGG(fa.actor_id :: INT) @> ARRAY [28, 37];
