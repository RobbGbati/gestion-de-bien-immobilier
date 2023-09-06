package com.gracetech.gestionimmoback.converter;

import com.gracetech.gestionimmoback.enums.Offre;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OffreConverter implements AttributeConverter<Offre, String> {

    @Override
    public String convertToDatabaseColumn(Offre offre) {
        if (offre == null) {
            return null;
        }
        return offre.getDescription();
    }

    @Override
    public Offre convertToEntityAttribute(String desc) {
        if (desc == null) {
            return null;
        }

        return Stream.of(Offre.values())
            .filter(o -> o.getDescription().equals(desc))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
    
}
