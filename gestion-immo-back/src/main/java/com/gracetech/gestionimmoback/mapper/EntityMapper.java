package com.gracetech.gestionimmoback.mapper;

import java.util.List;

/**
 * contract for a generic dto to entity mapper
 * @author Robbile
 *
 * @param <D> dto type parameter
 * @param <E> entity type parameter
 */
public interface EntityMapper<D, E> {

	E toEntity(D dto);
	
	D toDto(E entity);
	
	List<E> toEntity(List<D> dtoList);
	
	List<D> toDto(List<E> entityList);
}
