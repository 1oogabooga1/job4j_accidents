CREATE TABLE IF NOT EXISTS accidents (
  id SERIAL PRIMARY KEY,
  name TEXT,
  description TEXT,
  address TEXT,
  type_id INT REFERENCES accident_types(id)
);