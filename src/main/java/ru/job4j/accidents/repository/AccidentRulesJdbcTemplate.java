package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentRulesJdbcTemplate implements AccidentRulesRepository {

    private final JdbcTemplate jdbc;

    @Override
    public void saveRulesForAccident(int accidentId, Set<Rule> rules) {
        if (rules == null || rules.isEmpty()) {
            return;
        }
        for (Rule rule : rules) {
            jdbc.update("""
                    INSERT INTO accidents_rules(accident_id, rule_id) VALUES (?, ?)
                    """, accidentId, rule.getId());
        }
    }

    @Override
    public void deleteRulesByAccidentId(int accidentId) {
        jdbc.update("""
                DELETE FROM accidents_rules WHERE accident_id = ?
                """, accidentId);
    }

    @Override
    public Set<Rule> findRulesByAccidentId(int accidentId) {
        return new HashSet<>(jdbc.query("""
                SELECT r.id, r.name
                FROM rules r
                JOIN accidents_rules ar ON r.id = ar.rule_id
                WHERE ar.accident_id = ?
                ORDER BY r.id
                """,
                this::mapRule, accidentId
        ));
    }

    private Rule mapRule(ResultSet resultSet, int rowNum) throws SQLException {
        Rule rule = new Rule();
        rule.setId(resultSet.getInt("id"));
        rule.setName(resultSet.getString("name"));
        return rule;
    }
}
