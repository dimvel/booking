INSERT INTO hotel (name, address, star_rating) VALUES
  ('Hra', 'Panepistimiou 5', 2),
  ('Ermhs', 'Ermou 10', 3),
  ('Afrodith', 'Stadiou 20', 4),
  ('Dias', 'Leoforos Kifisias 50', 5);

INSERT INTO booking (customer_name, customer_surname, pax, price_amount, currency, hotel_id) VALUES
  ('Girgos', 'Papadopoulos', 2, 220.11, 'EUR', 1),
  ('Giannis', 'Petridis', 4, 330.22, 'EUR', 2),
  ('Kostas', 'Papazoglou', 8, 888.77, 'EUR', 3),
  ('Pavlos', 'Papadakis', 10, 1234.00, 'EUR', 4),
  ('Dimitris', 'Georgiou', 5, 555.44, 'EUR', 4);