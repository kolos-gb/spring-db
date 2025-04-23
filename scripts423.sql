-- 1. Информация о студентах Хогвартса: имя, возраст и факультет
SELECT s.name, s.age, f.name AS faculty_name
FROM Student s
         INNER JOIN Faculty f ON s.faculty_id = f.id;


-- 2. Студенты, у которых есть аватарки
SELECT s.name
FROM Student s
         INNER JOIN Avatar a ON s.id = a.student_id;

