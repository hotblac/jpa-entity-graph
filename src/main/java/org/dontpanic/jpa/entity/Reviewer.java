package org.dontpanic.jpa.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@NamedEntityGraph(
        name = "Reviewer.allRelatedEntities",
        attributeNodes = @NamedAttributeNode(value = "reviews", subgraph = "Review.allRelatedEntities"),
        subgraphs = {
                @NamedSubgraph(
                        name = "Review.allRelatedEntities",
                        attributeNodes = @NamedAttributeNode(value = "movie", subgraph = "Movie.allRelatedEntities")
                ),
                @NamedSubgraph(
                        name = "Movie.allRelatedEntities",
                        attributeNodes = {
                                @NamedAttributeNode(value = "stars", subgraph = "Stars.allRelatedEntities"),
                                @NamedAttributeNode(value = "studio")
                        }
                ),
                @NamedSubgraph(
                        name = "Stars.allRelatedEntities",
                        attributeNodes = @NamedAttributeNode(value = "awards", subgraph = "Award.allRelatedEntities")
                ),
                @NamedSubgraph(
                        name = "Award.allRelatedEntities",
                        attributeNodes = @NamedAttributeNode(value = "awardBody")
                )
        }
)
@Entity
public class Reviewer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany
    private Set<Review> reviews = new HashSet<>();

    public Reviewer() {
    }

    public Reviewer(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        // Do not include sub-entities in toString()
        return "Reviewer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
