package com.github.vlsidlyarevich.spring5homework.bootstrap;

import com.github.vlsidlyarevich.spring5homework.domain.Author;
import com.github.vlsidlyarevich.spring5homework.domain.Book;
import com.github.vlsidlyarevich.spring5homework.repository.AuthorRepository;
import com.github.vlsidlyarevich.spring5homework.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DbBootstrapper implements ApplicationListener<ContextRefreshedEvent> {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DbBootstrapper(final AuthorRepository authorRepository, final BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        init();
    }

    private void init() {
        final Book book = new Book("Clean code", new ArrayList<>());

        final Author author = new Author("Bob", "Martin", new ArrayList<>());
        author.getBooks().add(book);

        book.getAuthors().add(author);

        authorRepository.save(author);
        bookRepository.save(book);
    }
}
