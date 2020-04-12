package com.github.vlsidlyarevich.spring5homework.domain.repositories;

import com.github.vlsidlyarevich.spring5homework.domain.model.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByDescription(String description);
}
