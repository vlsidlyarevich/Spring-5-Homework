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
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {

    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }

        final CategoryCommand categoryCommand = new CategoryCommand();

        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());

        return categoryCommand;
    }
}
