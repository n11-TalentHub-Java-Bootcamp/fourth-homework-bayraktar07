package com.bayraktar.springboot.converter;

import com.bayraktar.springboot.dto.CollectionDTO;
import com.bayraktar.springboot.entity.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CollectionMapper {

    CollectionMapper INSTANCE = Mappers.getMapper(CollectionMapper.class);

    CollectionDTO convertCollectionToCollectionDTO(Collection collection);

    Collection convertCollectionDTOToCollection(CollectionDTO collectionDTO);

    List<CollectionDTO> convertCollectionListToCollectionListDTO(List<Collection> collectionList);
}
