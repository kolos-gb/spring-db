-- Получить всех студентов, возраст которых находится между 10 и 20 лет
SELECT * FROM students WHERE age BETWEEN 10 AND 20;

-- Получить всех студентов, но отобразить только список их имен
SELECT name FROM students;

-- Получить всех студентов, у которых в имени присутствует буква 'О' (регистр не учитывается)
SELECT * FROM students WHERE LOWER(name) LIKE '%o%';

-- Получить всех студентов, у которых возраст меньше идентификатора
SELECT * FROM students WHERE age < id;

-- Получить всех студентов, упорядоченных по возрасту (по возрастанию)
SELECT * FROM students ORDER BY age ASC;
