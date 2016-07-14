package com.example.igorvilar.addressbookandroid;

/**
 * Created by igorvilar on 14/07/16.
 */
public class ContactBookModel {


    int IdContact;
    String Name;
    String Phone;
    String Address;


    public ContactBookModel(int IdContact, String Name, String Phone, String Address) {
        this.IdContact = IdContact;
        this.Name = Name;
        this.Phone = Phone;
        this.Address = Address;
    }

    public int getIdContact() {
        return IdContact;
    }

    public void setIdContact(int idContact) {
        IdContact = idContact;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

}