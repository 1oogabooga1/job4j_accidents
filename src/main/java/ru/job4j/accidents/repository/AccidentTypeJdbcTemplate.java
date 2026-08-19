package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcTemplate implements AccidentTypeRepository {

    private final JdbcTemplate jdbc;

    @Override
    public AccidentType create(AccidentType type) {
        jdbc.update("INSERT INTO accident_types(name) VALUES (?)", type.getName());
        return type;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        AccidentType type = jdbc.queryForObject("""
                SELECT t.id, t.name FROM accident_types t WHERE t.id = ?
                """, this::map, id);
        return Optional.of(type);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return jdbc.query("""
                SELECT t.id, t.name FROM accident_types t
                """, this::map);
    }

    private AccidentType map(ResultSet resultSet, int rowNum) throws SQLException {
        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("id"));
        type.setName(resultSet.getString("name"));
        return type;
    }
}
