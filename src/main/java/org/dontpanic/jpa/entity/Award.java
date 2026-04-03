package org.dontpanic.jpa.entity;

import jakarta.persistence.*;

@Entity
public class Award {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private Star recipient;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "award_id")
    private AwardBody awardBody;

    private String category;

    public Award() {
    }

    public Award(Star recipient, AwardBody awardBody, String category) {
        this.recipient = recipient;
        this.awardBody = awardBody;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Star getRecipient() {
        return recipient;
    }

    public void setRecipient(Star recipient) {
        this.recipient = recipient;
    }

    public AwardBody getAwardBody() {
        return awardBody;
    }

    public void setAwardBody(AwardBody awardBody) {
        this.awardBody = awardBody;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        // Do not include sub-entities in toString()
        return "Award{" +
                "id=" + id +
                ", category='" + category + '\'' +
                '}';
    }
}
