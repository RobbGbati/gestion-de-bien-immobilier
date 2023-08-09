package com.gracetech.gestionimmoback.mapper;

import com.gracetech.gestionimmoback.dto.BienDto;
import com.gracetech.gestionimmoback.model.Bien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface BienMapper {

    BienMapper INSTANCE = Mappers.getMapper(BienMapper.class);

    Bien toEntity(BienDto dto);
    List<Bien> toEntity(List<BienDto> bienDtos);

    @Mapping(source="bien.client.id", target = "firstOwnerId")
    BienDto toDto(Bien bien);
    List<BienDto> toDto(List<Bien> biens);
}
