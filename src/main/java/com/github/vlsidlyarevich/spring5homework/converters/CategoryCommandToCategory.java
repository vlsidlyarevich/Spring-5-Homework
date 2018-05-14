package com.github.vlsidlyarevich.spring5homework.converters;


import com.github.vlsidlyarevich.spring5homework.commands.CategoryCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.Category;
import com.vlsidlyarevich.spring5homework.commands.CategoryCommand;
import com.vlsidlyarevich.spring5homework.domain.model.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) {
            return null;
        }

        final Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }
}
