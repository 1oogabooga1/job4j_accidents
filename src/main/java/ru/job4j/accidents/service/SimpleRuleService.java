package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.RuleRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleRuleService implements RuleService {

    private final RuleRepository ruleJdbcTemplate;

    @Override
    public Rule create(Rule rule) {
        return ruleJdbcTemplate.create(rule);
    }

    @Override
    public Optional<Rule> findById(int id) {
        return ruleJdbcTemplate.findById(id);
    }

    @Override
    public Collection<Rule> findAll() {
        return ruleJdbcTemplate.findAll();
    }

    @Override
    public Set<Rule> findRulesByIds(Set<Integer> ids) {
        return ruleJdbcTemplate.findRulesByIds(ids);
    }
}
