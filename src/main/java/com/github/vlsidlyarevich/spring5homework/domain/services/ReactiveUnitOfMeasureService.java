package com.github.vlsidlyarevich.spring5homework.domain.services;

import com.github.vlsidlyarevich.spring5homework.commands.UnitOfMeasureCommand;
import com.github.vlsidlyarevich.spring5homework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.github.vlsidlyarevich.spring5homework.domain.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReactiveUnitOfMeasureService implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository repository;
    private final UnitOfMeasureToUnitOfMeasureCommand commandConverter;

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        return repository.findAll()
                .map(commandConverter::convert);
    }
}
