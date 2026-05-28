package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AccidentService {

    Accident create(Accident accident, Set<Integer> ruleIds);

    boolean delete(int id);

    boolean edit(Accident accident, Set<Integer> ruleIds);

    Optional<Accident> findById(int id);

    Collection<Accident> findAll();
}
