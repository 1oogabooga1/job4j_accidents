package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Optional;

public interface AccidentRepository {

    Accident create(Accident accident);

    boolean delete(int id);

    Optional<Accident> findById(int id);

    Collection<Accident> findAll();
}
