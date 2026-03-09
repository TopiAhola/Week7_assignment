CREATE DATABASE IF NOT EXISTS calc_data2;
USE calc_data2;

GRANT ALL PRIVILEGES ON calc_data2.* TO 'user'@'%' IDENTIFIED BY 'password';
FLUSH PRIVILEGES;


CREATE TABLE IF NOT EXISTS calc_results (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            number1 DOUBLE NOT NULL,
                                            number2 DOUBLE NOT NULL,
                                            sum_result DOUBLE NOT NULL,
                                            product_result DOUBLE NOT NULL,
                                            subtract_result DOUBLE NOT NULL,
                                            divide_result DOUBLE NOT NULL,
                                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);





