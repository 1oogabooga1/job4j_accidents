package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.AccidentTypeRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentTypeService implements AccidentTypeService {

    private final AccidentTypeRepository accidentTypeJdbcTemplate;

    @Override
    public AccidentType create(AccidentType type) {
        return accidentTypeJdbcTemplate.create(type);
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return accidentTypeJdbcTemplate.findById(id);
    }

    @Override
    public Collection<AccidentType> findAll() {
        return accidentTypeJdbcTemplate.findAll();
    }
}
