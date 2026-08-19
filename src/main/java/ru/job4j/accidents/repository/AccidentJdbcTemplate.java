package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentJdbcTemplate implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private final AccidentRulesRepository accidentRulesRepository;

    @Override
    public Accident create(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO accidents(name, description, address, type_id)
                VALUES (?, ?, ?, ?)
                """, new String[]{"id"});
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getDescription());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, keyHolder);
        var key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Failed to get generated accident id");
        }
        accident.setId(key.intValue());
        accidentRulesRepository.saveRulesForAccident(accident.getId(), accident.getRules());
        return accident;
    }

    @Override
    public boolean delete(int id) {
        if (findById(id).isEmpty()) {
            return false;
        }
        accidentRulesRepository.deleteRulesByAccidentId(id);
        return jdbc.update("DELETE FROM accidents WHERE id = ?", id) != 0;
    }

    @Override
    public boolean edit(Accident accident) {
        int rsl = jdbc.update("""
                        UPDATE accidents
                        SET name = ?, description = ?, address = ?, type_id = ? 
                        WHERE id = ?
                        """,
                accident.getName(), accident.getDescription(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        if (rsl == 0) {
            return false;
        }
        accidentRulesRepository.deleteRulesByAccidentId(accident.getId());
        accidentRulesRepository.saveRulesForAccident(accident.getId(), accident.getRules());
        return true;
    }

    @Override
    public Optional<Accident> findById(int id) {
        try {
            Accident acc = jdbc.queryForObject("""
                        SELECT a.id,
                               a.name,
                               a.description,
                               a.address,
                               at.id AS type_id,
                               at.name AS type_name
                        FROM accidents a
                        LEFT JOIN accident_types at ON a.type_id = at.id
                        WHERE a.id = ?
                        """,
                    this::mapAccident,
                    id
            );
            acc.setRules(accidentRulesRepository.findRulesByAccidentId(id));
            return Optional.of(acc);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Collection<Accident> findAll() {
        var accidents = jdbc.query("""
                    SELECT a.id,
                           a.name,
                           a.description,
                           a.address,
                           at.id AS type_id,
                           at.name AS type_name
                    FROM accidents a
                    LEFT JOIN accident_types at ON a.type_id = at.id
                    ORDER BY a.id
                    """,
                this::mapAccident
        );
        accidents.forEach(accident ->
                accident.setRules(accidentRulesRepository.findRulesByAccidentId(accident.getId()))
        );
        return accidents;
    }

    private Accident mapAccident(ResultSet resultSet, int rowNum) throws SQLException {
        Accident accident = new Accident();
        accident.setId(resultSet.getInt("id"));
        accident.setName(resultSet.getString("name"));
        accident.setDescription(resultSet.getString("description"));
        accident.setAddress(resultSet.getString("address"));

        AccidentType type = new AccidentType();
        type.setId(resultSet.getInt("type_id"));
        type.setName(resultSet.getString("type_name"));

        accident.setType(type);
        return accident;
    }
}
