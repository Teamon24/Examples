CREATE VIEW news(id, section, header, score) AS
(
SELECT 1, 2, 'Заголовок1', 23
UNION ALL
SELECT 2, 1, 'Заголовок2', 6
UNION ALL
SELECT 3, 4, 'Заголовок3', 79
UNION ALL
SELECT 4, 3, 'Заголовок4', 36
UNION ALL
SELECT 5, 2, 'Заголовок5', 34
UNION ALL
SELECT 6, 2, 'Заголовок6', 95
UNION ALL
SELECT 7, 4, 'Заголовок7', 26
UNION ALL
SELECT 8,
       3,
       'Заголовок8',
       36 );

SELECT id, header, section, score, row_number() OVER () AS num
FROM news;

SELECT id, header, section, score, row_number() OVER (ORDER BY score DESC) AS rating
FROM news
ORDER BY id;

SELECT id, header, section, score, row_number() OVER (PARTITION BY section ORDER BY score DESC) AS rating_in_section
FROM news
ORDER BY section, rating_in_section;


CREATE VIEW transactions(id, change) AS
(
SELECT 1, 1.00
UNION ALL
SELECT 2, -2.00
UNION ALL
SELECT 3, 10.00
UNION ALL
SELECT 4, -4.00
UNION ALL
SELECT 5, 5.50);

SELECT id,
       change,
       sum(change) OVER (ORDER BY id) AS balance
FROM transactions
ORDER BY id;

SELECT id,
       change,
       sum(change) OVER () AS result_balance
FROM transactions
ORDER BY id;

SELECT id,
       change,
       sum(change) OVER (ORDER BY id) AS balance,
       sum(change) OVER ()            AS result_balance,
       round(
                       100.0 * sum(change) OVER (ORDER BY id) / sum(change) OVER (),
                       2
           )                          AS percent_of_result,
       count(*) OVER ()               AS transactions_count
FROM transactions
ORDER BY id;

SELECT id,
       section,
       score,
       row_number() OVER (PARTITION BY section ORDER BY score DESC) AS rating_in_section
FROM news
ORDER BY section, rating_in_section;
------------------------------------------------------ LAG -------------------------------------------------------------
SELECT id,
       section,
       header,
       score,
       row_number() OVER w       AS rating,
       lag(score) OVER w - score AS score_lag
FROM news
    WINDOW w AS (ORDER BY score DESC)
ORDER BY score desc;