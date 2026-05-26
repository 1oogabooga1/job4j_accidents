package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Accident;
import java.util.Collection;
import java.util.Optional;

public interface AccidentService {

    Accident create(Accident accident);

    boolean delete(int id);

    boolean edit(Accident accident);

    Optional<Accident> findById(int id);

    Collection<Accident> findAll();
}
