package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReviewerRepositoryTest extends AbstractRepositoryTest {

    @Autowired private TestEntityManager entityManager;
    private Statistics statistics;

    @BeforeEach
    void setUp() {
        SessionFactory sf = entityManager.getEntityManager().getEntityManagerFactory().unwrap(SessionFactory.class);
        statistics = sf.getStatistics();

        initTestData();
        // Clear the cached test data entities from the EM.
        // We want to force the tests to load all entities from the database.
        entityManager.flush();
        entityManager.clear();

        // Enable statistics so we can verify execution counts
        statistics.setStatisticsEnabled(true);
        statistics.clear();
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

    @Test
    void findByName_returnsAllAssociatedEntitiesWithASingleFetch() {
        Reviewer reviewer = reviewerRepository.findByName("Joe Bloggs");
        Set<Review> reviews = reviewer.getReviews();

        // Get all the movies reviewed
        Set<Movie> movies = reviews.stream().map(Review::getMovie).collect(Collectors.toSet());
        assertThat(movies, hasSize(3));
        assertThat(movies, containsInAnyOrder(
                movieWithTitle("Ghostbusters"),
                movieWithTitle("Raiders of the Lost Ark"),
                movieWithTitle("Blues Brothers")
        ));

        // Get all the stars of movies reviewed
        Set<Star> stars = movies.stream().flatMap(movie -> movie.getStars().stream()).collect(Collectors.toSet());
        assertThat(stars, hasSize(7));
        assertThat(stars, containsInAnyOrder(
                starWithName("Dan", "Aykroyd"),
                starWithName("John", "Bellushi"),
                starWithName("Carrie", "Fisher"),
                starWithName("Harrison", "Ford"),
                starWithName("Ernie", "Hudson"),
                starWithName("Bill", "Murray"),
                starWithName("Harold", "Ramis")
        ));

        // Get all the awards won by the stars
        Set<Award> awards = stars.stream().flatMap(star -> star.getAwards().stream()).collect(Collectors.toSet());
        assertThat(awards, hasSize(5));
        assertThat(awards, containsInAnyOrder(
                awardTo("Dan", "Aykroyd", "Oscar", "Most Handsome"),
                awardTo("John", "Bellushi", "BAFTA", "King Bee"),
                awardTo("Carrie", "Fisher", "Golden Globe", "Best Bikini"),
                awardTo("Harrison", "Ford", "BAFTA", "Best Hat"),
                awardTo("Harrison", "Ford", "Oscar", "Best Chin")
        ));

        // All entities loaded with a single query
        assertEquals(1, statistics.getQueryExecutionCount());
        assertEquals(0, statistics.getCollectionFetchCount());
    }
}