package com.github.vlsidlyarevich.spring5homework.domain.repositories;

import com.github.vlsidlyarevich.spring5homework.domain.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, String> {

    Optional<Category> findByDescription(String description);
}
