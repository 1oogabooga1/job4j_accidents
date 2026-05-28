package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentTypeMem implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    private final AtomicInteger id = new AtomicInteger(0);

    public AccidentTypeMem() {
        create(new AccidentType(0, "Two cars"));
        create(new AccidentType(0, "A car and a person"));
        create(new AccidentType(0, "A car and a bike"));
    }

    @Override
    public AccidentType create(AccidentType type) {
        type.setId(id.incrementAndGet());
        types.put(type.getId(), type);
        return type;
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(types.get(id));
    }

    @Override
    public Collection<AccidentType> findAll() {
        return types.values();
    }
}
