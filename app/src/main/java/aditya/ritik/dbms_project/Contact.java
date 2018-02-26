package aditya.ritik.dbms_project;
/**
 * Created by Ritik Srivastava on 02-05-2017.
 */

public class Contact {
    String  email, name, time, date;
    int id;
    public Contact(int id, String name, String email, String time, String date) {
        this.id=id;
        this.name=name;
        this.email=email;
        this.time=time;
        this.date=date;
    }
    public String getdate() {
        return date;
    }
    public String getemail() {
        return email;
    }
    public int getid() {
        return id;
    }
    public String getname() {
        return name;
    }
    public String gettime() {
        return time;
    }
}
