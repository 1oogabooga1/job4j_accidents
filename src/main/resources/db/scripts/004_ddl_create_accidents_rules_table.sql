CREATE TABLE IF NOT EXISTS accidents_rules (
id SERIAL PRIMARY KEY,
accident_id INT REFERENCES accidents(id),
rule_id INT REFERENCES rules(id),
UNIQUE(accident_id, rule_id)
);