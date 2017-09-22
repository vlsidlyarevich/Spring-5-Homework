package com.github.vlsidlyarevich.spring5homework.repository;

import com.github.vlsidlyarevich.spring5homework.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

}
