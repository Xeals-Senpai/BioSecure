package Main;

public class AccountDetails {
    private String fname;
    private String lname;
    private String passwd;
    private int dob;

    public void setFname(String name) {
        this.fname = name;
    }

    public String getFname() {
        return fname;
    }

    public void setLname(String name) {
        this.lname = name;
    }

    public String getLname() {
        return lname;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setDob(int date) {
        this.dob = date;
    }

    public int getDob() {
        return dob;
    }
}
