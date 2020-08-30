package com.joniks.loyalty.api.model.params;

import java.io.Serializable;

public class SocialUserParams implements Serializable {
	private static final long serialVersionUID = 1L;
    
    String id;
    String email;
    String firstName;
    String lastName;
    String photoUrl;
    String provider;

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getId() {
        return this.id;
    }
    
    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public String getProvider() {
        return this.provider;
    }

    @Override
	public String toString() {
		return "SocialUser [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", photoUrl=" + photoUrl + ", provider=" + provider + "]";
	}
}