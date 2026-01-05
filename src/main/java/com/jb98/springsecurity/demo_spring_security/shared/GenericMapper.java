package com.jb98.springsecurity.demo_spring_security.shared;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

public interface GenericMapper<D, E> {
    /**
     * To entity.
     *
     * @param dto the dto
     * @return the e
     */
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    E toEntity(D dto);

    /**
     * To dto.
     *
     * @param entity the entity
     * @return the d
     */
    D toDto(E entity);

    /**
     * To entity.
     *
     * @param dtoList the dto list
     * @return the list
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * To dto.
     *
     * @param entityList the entity list
     * @return the list
     */
    List<D> toDto(List<E> entityList);

    /**
     * To dto.
     *
     * @param entityList the entity list
     * @return the sets the
     */
    Set<D> toDto(Set<E> entityList);

    /**
     * Update Entity from Dto
     *
     * */
    @Mapping(target = "id", ignore = true)
    void update(D dto, @MappingTarget E entity);
}
