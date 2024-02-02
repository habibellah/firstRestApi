package com.habibellah.firstRestApi.repositories;


import com.habibellah.firstRestApi.domain.entities.AuthorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity,Long>,
        PagingAndSortingRepository<AuthorEntity,Long> {

}
