package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class RuleMem implements RuleRepository {
    private final AtomicInteger id = new AtomicInteger(0);

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public RuleMem() {
        create(new Rule(0, "Rule. 1"));
        create(new Rule(0, "Rule. 2"));
        create(new Rule(0, "Rule. 3"));
    }

    @Override
    public Rule create(Rule rule) {
        rule.setId(id.incrementAndGet());
        rules.put(rule.getId(), rule);
        return rule;
    }

    @Override
    public Optional<Rule> findById(int id) {
        return Optional.of(rules.get(id));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules.values();
    }

    @Override
    public Set<Rule> findRulesByIds(Set<Integer> ids) {
        Set<Rule> result = new HashSet<>();
        for (Map.Entry<Integer, Rule> entry : rules.entrySet()) {
            for (Integer id : ids) {
                if (entry.getKey().equals(id)) {
                    result.add(entry.getValue());
                }
            }
        }
        return result;
    }
}
