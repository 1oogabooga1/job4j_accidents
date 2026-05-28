package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.AccidentRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleAccidentService implements AccidentService {

    private final AccidentRepository repository;

    private final AccidentTypeService typeService;

    private final RuleService ruleService;

    @Override
    public Accident create(Accident accident, Set<Integer> ruleIds) {
        accident.setRules(getExistingRules(ruleIds));
        accident.setType(getExistingType(accident));
        return repository.create(accident);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public boolean edit(Accident accident, Set<Integer> ruleIds) {
        accident.setRules(getExistingRules(ruleIds));
        accident.setType(getExistingType(accident));
        return repository.edit(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return repository.findAll();
    }

    private AccidentType getExistingType(Accident accident) {
        if (accident.getType() == null) {
            throw new IllegalArgumentException("The accident type is required");
        }
        return typeService.findById(accident.getType().getId())
                .orElseThrow(() -> new IllegalArgumentException("The accident type does not exist"));
    }

    private Set<Rule> getExistingRules(Set<Integer> ruleIds) {
        return ruleIds != null && !ruleIds.isEmpty() ? ruleService.findRulesByIds(ruleIds) : Set.of();
    }
}
