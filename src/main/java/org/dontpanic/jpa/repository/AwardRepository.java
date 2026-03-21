package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.Award;
import org.springframework.data.repository.CrudRepository;

public interface AwardRepository extends CrudRepository<Award, Long> {
}
