package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Set;

public interface AccidentRulesRepository {
    void saveRulesForAccident(int accidentId, Set<Rule> rules);

    void deleteRulesByAccidentId(int accidentId);

    Set<Rule> findRulesByAccidentId(int accidentId);

}
