package model;

public class staff extends person{
    private int staffId;
    private String position;
    public staff() {
        super();
    }
    public staff(int staffId, String firstName, String lastName, String email, String phoneNumber, String position) {
        this.staffId = staffId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.position = position;
    }
    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

