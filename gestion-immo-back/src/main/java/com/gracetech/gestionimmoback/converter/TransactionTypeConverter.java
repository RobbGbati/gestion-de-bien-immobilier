package com.gracetech.gestionimmoback.converter;

import com.gracetech.gestionimmoback.enums.TransactionType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;


@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

    @Override
    public String convertToDatabaseColumn(TransactionType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public TransactionType convertToEntityAttribute(String desc) {
        if (desc == null) {
            return null;
        }

        return Stream. of(TransactionType.values())
                .filter(t -> t.getDescription().equals(desc))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
    
}
