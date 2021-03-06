package com.jdbc;

import java.time.LocalDate;
import java.util.Objects;

public class AddressBookData {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String city;
    public String state;
    public String phoneNumber;
    public int zipCode;
    public String email;
    public String start;

    public AddressBookData(int id, String firstName, String lastName, String address, String city, String state,
                           String phoneNumber, int zipCode, String email, String start) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.email = email;
        this.start = start;

    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getZipCode() {
        return zipCode;
    }

    public String getEmail() {
        return email;
    }

    public String getStart() {
        return start;
    }


    @Override
    public String toString() {
        return "AddressBookData{" +
                "id=" + id +
                ", FirstName='" + firstName + '\'' +
                ", LastName='" + lastName + '\'' +
                ", Address ='" + address + '\'' +
                ", City ='" + city + '\'' +
                ", State ='" + state + '\'' +
                ", PhoneNumber='" + phoneNumber + '\'' +
                ", ZipCode='" + zipCode + '\'' +
                ", Email='" + email + '\'' +
                ", start='" +start + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookData that = (AddressBookData) o;
        return id == that.id && CharSequence.compare(that.email,email) == 0 && Objects.equals(firstName, that.firstName);
    }
}

