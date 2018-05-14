package com.github.vlsidlyarevich.spring5homework.converters;

import com.github.vlsidlyarevich.spring5homework.commands.NotesCommand;
import com.github.vlsidlyarevich.spring5homework.domain.model.Notes;
import com.vlsidlyarevich.spring5homework.commands.NotesCommand;
import com.vlsidlyarevich.spring5homework.domain.model.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if(source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
    }
}
