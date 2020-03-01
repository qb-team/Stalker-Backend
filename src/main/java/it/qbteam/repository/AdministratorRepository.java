package it.qbteam.repository;

import org.springframework.data.repository.CrudRepository;

import it.qbteam.model.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Long> {

}