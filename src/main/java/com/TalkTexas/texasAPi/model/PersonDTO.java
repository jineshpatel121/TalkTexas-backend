package com.TalkTexas.texasAPi.model;

public class PersonDTO {
    private String name;
    private String party;
    private String email;
    private String title;
    private String orgClass;
    private String district;
    private String number;
    private String imageUrl;
    private String moreUrl;

    // Constructor
    public PersonDTO(String name, String party, String email, String title, String orgClass, String district, String number, String imageUrl, String moreUrl) {
        this.name = name;
        this.party = party;
        this.email = email;
        this.title = title;
        this.orgClass = orgClass;
        this.district = district;
        this.number = number;
        this.imageUrl = imageUrl;
        this.moreUrl = moreUrl;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgClass() {
        return orgClass;
    }

    public void setOrgClass(String orgClass) {
        this.orgClass = orgClass;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMoreUrl() {
        return moreUrl;
    }

    public void setMoreUrl(String moreUrl) {
        this.moreUrl = moreUrl;
    }
    
}
