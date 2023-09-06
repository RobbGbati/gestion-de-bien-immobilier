package com.gracetech.gestionimmoback.converter;

import com.gracetech.gestionimmoback.enums.BienStatus;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class BienStatusConverter implements AttributeConverter<BienStatus, String> {

    @Override
    public String convertToDatabaseColumn(BienStatus bienStatus) {
        if (bienStatus == null) {
            return null;
        }
        return bienStatus.getDesc();
    }

    @Override
    public BienStatus convertToEntityAttribute(String desc) {
        if (desc == null) {
            return null;
        }

        return Stream.of(BienStatus.values())
            .filter(b -> b.getDesc().equals(desc))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
    
}
