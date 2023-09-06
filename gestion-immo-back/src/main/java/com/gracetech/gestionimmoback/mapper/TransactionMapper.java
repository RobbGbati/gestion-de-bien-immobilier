package com.gracetech.gestionimmoback.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gracetech.gestionimmoback.dto.TransactionDto;
import com.gracetech.gestionimmoback.model.TransactionImmo;

@Mapper(componentModel = "spring", uses = {BienMapper.class})
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);


    @Mapping(source="bien.description", target = "bienDescription")
    @Mapping(source="bien.id", target = "bienId")
    @Mapping(source="bien.city", target = "city")
    TransactionDto toDto(TransactionImmo source);
    List<TransactionDto> toDto(List<TransactionImmo> sources);
    
}
