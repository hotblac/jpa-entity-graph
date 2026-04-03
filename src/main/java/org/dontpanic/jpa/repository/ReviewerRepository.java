package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.Reviewer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface ReviewerRepository extends CrudRepository<Reviewer, Long> {

    @EntityGraph(value = "Reviewer.allRelatedEntities")
    Reviewer findByName(String name);

}
