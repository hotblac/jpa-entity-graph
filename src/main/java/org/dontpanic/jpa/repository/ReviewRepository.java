package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Long> {
}
