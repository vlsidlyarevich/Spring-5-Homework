package com.github.vlsidlyarevich.spring5homework.domain.repositories.reactive;

import com.github.vlsidlyarevich.spring5homework.domain.model.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {

}
