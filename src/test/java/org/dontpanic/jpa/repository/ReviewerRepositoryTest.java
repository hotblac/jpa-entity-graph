package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.Reviewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReviewerRepositoryTest extends AbstractRepositoryTest {

    @BeforeEach
    void setUp() {
        initTestData();
    }

    @Test
    void findByName_returnsReviewer() {
        Reviewer reviewer = reviewerRepository.findByName("Joe Bloggs");
        assertNotNull(reviewer);
    }
}