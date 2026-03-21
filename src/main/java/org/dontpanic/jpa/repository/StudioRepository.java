package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.Studio;
import org.springframework.data.repository.CrudRepository;

public interface StudioRepository extends CrudRepository<Studio, Long> {
}
