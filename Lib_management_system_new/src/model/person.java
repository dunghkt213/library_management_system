package model;

public class person {
    String id;
    String name;
    String birthday;
    String phoneNumber;
    String email;
    public person() {}
    public person(String name, String birthday, String phoneNumber, String email) {
        this.name = name;
        this.birthday = birthday;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
