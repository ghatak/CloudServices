package org.amdocs.elearning.order.service.user;

import java.time.LocalDate;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String middleInitial;
    private LocalDate dateOfBirth;
    private UserType userType;

    public User() {}

    public User(String id, String firstName, String lastName, String middleInitial, UserType userType, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleInitial = middleInitial;
        this.userType = userType;
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public UserType getUserType() {
        return userType;
    }
}
