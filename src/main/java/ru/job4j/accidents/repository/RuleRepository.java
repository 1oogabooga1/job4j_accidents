package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RuleRepository {
    Rule create(Rule rule);

    Optional<Rule> findById(int id);

    Collection<Rule> findAll();

    Set<Rule> findRulesByIds(Set<Integer> ids);
}
