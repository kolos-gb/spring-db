-- Возраст студента не может быть меньше 16 лет.
ALTER TABLE Student
    ADD CONSTRAINT check_student_age CHECK (age >= 16);

-- Имена студентов должны быть уникальными и не равны нулю.
ALTER TABLE Student
    ALTER COLUMN name SET NOT NULL;

ALTER TABLE Student
    ADD CONSTRAINT unique_student_name UNIQUE (name);

-- Пара “значение названия” - “цвет факультета” должна быть уникальной.
ALTER TABLE Faculty
    ADD CONSTRAINT unique_faculty_name_color UNIQUE (name, color);

-- При создании студента без возраста ему автоматически должно присваиваться 20 лет.
ALTER TABLE Student
    ALTER COLUMN age SET DEFAULT 20;
