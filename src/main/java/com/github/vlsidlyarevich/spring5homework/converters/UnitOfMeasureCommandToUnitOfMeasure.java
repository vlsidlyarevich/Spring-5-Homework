package com.github.vlsidlyarevich.spring5homework.converters;

import com.github.vlsidlyarevich.spring5homework.commands.UnitOfMeasureCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.UnitOfMeasure;
import com.vlsidlyarevich.spring5homework.commands.UnitOfMeasureCommand;
import com.vlsidlyarevich.spring5homework.domain.model.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }
}
