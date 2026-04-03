package org.dontpanic.jpa.repository;

import org.dontpanic.jpa.entity.*;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Set;

/**
 * Superclass for repository tests with supporting methods
 */
@DataJpaTest
public abstract class AbstractRepositoryTest {
    @Autowired protected ReviewRepository reviewRepository;
    @Autowired protected ReviewerRepository reviewerRepository;
    @Autowired protected MovieRepository movieRepository;
    @Autowired protected AwardRepository awardRepository;

    protected void initTestData(){

        // Movies, Studios and Stars
        Star aykroyd = new Star("Dan", "Aykroyd");
        Star bellushi = new Star("John", "Bellushi");
        Star fisher = new Star("Carrie", "Fisher");
        Star ford = new Star("Harrison", "Ford");
        Star hamill = new Star("Mark", "Hamill");
        Star hudson = new Star("Ernie", "Hudson");
        Star murray = new Star("Bill", "Murray");
        Star ramis = new Star("Harold", "Ramis");

        Studio lucasfilm = new Studio("Lucasfilm");
        Studio universal = new Studio("Universal");
        Studio columbia = new Studio("Columbia");

        Movie starWarsIV = new Movie("Star Wars IV: A New Hope", lucasfilm);
        Movie starWarsV = new Movie("Star Wars V: The Empire Strikes Back", lucasfilm);
        Movie starWarsVI = new Movie("Star Wars VI: Return of the Jedi", lucasfilm);
        Movie indiana1 = new Movie("Raiders of the Lost Ark", lucasfilm);
        Movie indiana2 = new Movie("Temple of Doom", lucasfilm);
        Movie indiana3 = new Movie("Last Crusade", lucasfilm);
        Movie bluesBrothers = new Movie("Blues Brothers", universal);
        Movie ghostbusters = new Movie("Ghostbusters", columbia);

        starWarsIV.addStars(fisher, ford, hamill);
        starWarsV.addStars(fisher, ford, hamill);
        starWarsVI.addStars(fisher, ford, hamill);
        indiana1.addStars(ford);
        indiana2.addStars(ford);
        indiana3.addStars(ford);
        bluesBrothers.addStars(aykroyd, bellushi, fisher);
        ghostbusters.addStars(aykroyd, hudson, murray, ramis);

        movieRepository.saveAll(List.of(starWarsIV, starWarsV, starWarsVI, indiana1, indiana2, indiana3, bluesBrothers, ghostbusters));

        // Reviewers and reviews
        Reviewer joeBloggs = new Reviewer("Joe Bloggs");
        Review ghostbustersReview = new Review(ghostbusters, joeBloggs, "Who ya gonna call?");
        Review indiana1Review = new Review(indiana1, joeBloggs, "They had top men working on it.");
        Review bluesBrothersReview = new Review(bluesBrothers, joeBloggs, "They're on a mission from God");
        joeBloggs.setReviews(Set.of(ghostbustersReview, indiana1Review, bluesBrothersReview));
        reviewerRepository.save(joeBloggs);
        reviewRepository.saveAll(List.of(ghostbustersReview, indiana1Review, bluesBrothersReview));

        // Awards and award bodies
        AwardBody oscar = new AwardBody("Academy of Motion Picture Arts and Sciences", "Oscar");
        AwardBody bafta = new AwardBody("British Academy of Film and Television Arts", "BAFTA");
        AwardBody goldenGlobes = new AwardBody("Dick Clark Productions", "Golden Globe");

        Award mostHandsome = new Award(aykroyd, oscar, "Most Handsome");
        Award kingBee = new Award(bellushi, bafta, "King Bee");
        Award bestBikini = new Award(fisher, goldenGlobes, "Best Bikini");
        Award bestHat = new Award(ford, bafta, "Best Hat");
        Award bestChin = new Award(ford, oscar, "Best Chin");
        aykroyd.addAward(mostHandsome);
        bellushi.addAward(kingBee);
        fisher.addAward(bestBikini);
        ford.addAward(bestHat);
        ford.addAward(bestChin);
        awardRepository.saveAll(List.of(mostHandsome, kingBee, bestBikini, bestHat, bestChin));
    }

    protected Matcher<Review> reviewWithWording(String wording) {
        return new TypeSafeDiagnosingMatcher<>() {

            @Override
            public void describeTo(Description description) {
                description.appendText("a review with wording: ").appendValue(wording);
            }

            @Override
            protected boolean matchesSafely(Review review, Description description) {
                return review.getWording().equals(wording);
            }
        };
    }

    protected Matcher<Movie> movieWithTitle(String title) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("a movie with title: ").appendValue(title);
            }

            @Override
            protected boolean matchesSafely(Movie movie, Description description) {
                return movie.getTitle().equals(title);
            }
        };
    }

    protected Matcher<Star> starWithName(String firstName, String lastName) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("a star with name: ").appendValue(firstName).appendText(" ").appendValue(lastName);
            }

            @Override
            protected boolean matchesSafely(Star star, Description description) {
                return star.getFirstName().equals(firstName) && star.getLastName().equals(lastName);
            }
        };
    }

   protected Matcher<Award> awardTo(String firstName, String lastName, String awardName, String category) {
        return new TypeSafeDiagnosingMatcher<>() {
            @Override
            public void describeTo(Description description) {
                description.appendValue(awardName)
                        .appendText(" to ").appendValue(firstName).appendText(" ").appendValue(lastName)
                        .appendText(" for ").appendValue(category);
            }
            @Override
            protected boolean matchesSafely(Award award, Description description) {
                return award.getRecipient().getFirstName().equals(firstName) &&
                        award.getRecipient().getLastName().equals(lastName) &&
                        award.getAwardBody().getAwardName().equals(awardName) &&
                        award.getCategory().equals(category);
            }
        };
   }
}
