package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.Reviewer;
import org.springframework.data.repository.CrudRepository;

public interface ReviewerRepository extends CrudRepository<Reviewer, Long> {

    Reviewer findByName(String name);

}
