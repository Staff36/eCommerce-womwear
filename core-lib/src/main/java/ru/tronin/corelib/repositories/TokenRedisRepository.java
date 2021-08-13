package ru.tronin.corelib.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRedisRepository extends CrudRepository<String, String> {

}
