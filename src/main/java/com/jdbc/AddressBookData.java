package com.jdbc;

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

    public AddressBookData(int id, String firstName, String lastName, String address, String city, String state,
                           String phoneNumber, int zipCode, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.phoneNumber = phoneNumber;
        this.zipCode = zipCode;
        this.email = email;

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
                '}';
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBookData that = (AddressBookData) o;
        return id == that.id && CharSequence.compare(that.email.email) == 0 && Objects.equals(firstName, that.firstName);
    }*/
}

