package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@AllArgsConstructor
public class RuleJdbcTemplate implements RuleRepository {
    private final JdbcTemplate jdbc;

    private final NamedParameterJdbcTemplate namedJdbc;

    @Override
    public Rule create(Rule rule) {
        jdbc.update("""
                INSERT INTO rules(name) VALUES (?)
                """, rule.getName());
        return rule;
    }

    @Override
    public Optional<Rule> findById(int id) {
        var rule = jdbc.queryForObject("""
                SELECT r.id, r.name FROM rules r WHERE r.id = ?
                """, this::map, id);
        return Optional.of(rule);
    }

    @Override
    public Collection<Rule> findAll() {
        return jdbc.query("""
                SELECT r.id, r.name FROM rules r
                """, this::map);
    }

    @Override
    public Set<Rule> findRulesByIds(Set<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return Set.of();
        }
        return Set.copyOf(namedJdbc.query("""
                SELECT r.id, r.name
                FROM rules r
                WHERE r.id IN (:ids)
                ORDER BY r.id
                """,
                Map.of("ids", ids),
                this::map
        ));
    }

    private Rule map(ResultSet resultSet, int rowNum) throws SQLException {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    }
}
