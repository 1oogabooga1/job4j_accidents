package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    private final AtomicInteger nextId = new AtomicInteger(0);

    @Override
    public Accident create(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        accidents.put(accident.getId(), accident);
        return accident;
    }

    @Override
    public boolean delete(int id) {
        return accidents.remove(id) != null;
    }

    @Override
    public boolean edit(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) -> new Accident(id,
                accident.getName(), accident.getDescription(), accident.getAddress(), accident.getType(),
                accident.getRules())) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}
