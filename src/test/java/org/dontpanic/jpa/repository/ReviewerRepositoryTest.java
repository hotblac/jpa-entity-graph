package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.Review;
import org.dontpanic.jpa.entity.Reviewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReviewerRepositoryTest extends AbstractRepositoryTest {

    @Autowired private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        initTestData();
        // Clear the cached test data entities from the EM.
        // We want to force the tests to load all entities from the database.
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    void findByName_returnsReviewer() {
        Reviewer reviewer = reviewerRepository.findByName("Joe Bloggs");
        assertNotNull(reviewer);
    }

    @Test
    void findByName_returnsReviewerAndReviews() {
        Reviewer reviewer = reviewerRepository.findByName("Joe Bloggs");
        Set<Review> reviews = reviewer.getReviews();
        assertThat(reviews, hasSize(3));
        assertThat(reviews, containsInAnyOrder(
                reviewWithWording("Who ya gonna call?"),
                reviewWithWording("They had top men working on it."),
                reviewWithWording("They're on a mission from God")
        ));
    }
}