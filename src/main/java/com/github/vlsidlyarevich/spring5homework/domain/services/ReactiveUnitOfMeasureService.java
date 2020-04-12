package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.UnitOfMeasureCommand;
import com.github.vlsidlyarevich.spring5homework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReactiveUnitOfMeasureService implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand commandConverter;

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return repository.findAll()
                .map(unitOfMeasure -> commandConverter.convert(unitOfMeasure))
                .collect(Collectors.toSet())
                .block();
    }
}
