-- Таблица машины
CREATE TABLE Car (
                     id SERIAL PRIMARY KEY,
                     brand VARCHAR(50),
                     model VARCHAR(50),
                     price NUMERIC
);

-- Таблица человека
CREATE TABLE Person (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100),
                        age INT,
                        has_license BOOLEAN,
                        car_id INT,
                        FOREIGN KEY (car_id) REFERENCES Car(id)
);
