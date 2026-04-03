package org.dontpanic.jpa.entity;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reviewer_id")
    private Reviewer reviewer;

    private String wording;

    public Review() {
    }

    public Review(Movie movie, Reviewer reviewer, String wording) {
        this.movie = movie;
        this.reviewer = reviewer;
        this.wording = wording;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void addMovie(Movie movie) {
        this.movie = movie;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    @Override
    public String toString() {
        // Do not include sub-entities in toString()
        return "Review{" +
                "id=" + id +
                ", wording='" + wording + '\'' +
                '}';
    }
}
