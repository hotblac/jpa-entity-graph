package org.dontpanic.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AwardBody {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String organization;
    private String awardName;

    public AwardBody() {
    }

    public AwardBody(String organization, String awardName) {
        this.organization = organization;
        this.awardName = awardName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }
}
