package com.habibellah.firstRestApi.mappers.impl;

import com.habibellah.firstRestApi.domain.dto.AuthorDto;
import com.habibellah.firstRestApi.domain.entities.AuthorEntity;
import com.habibellah.firstRestApi.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity,AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto,AuthorEntity.class);
    }
}
