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

    @ManyToOne
    @JoinColumn(name = "award_id")
    private AwardBody awardBody;

    private String category;

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
}
