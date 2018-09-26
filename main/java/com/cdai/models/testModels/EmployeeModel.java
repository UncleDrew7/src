package com.cdai.models.testModels;
import javax.persistence.*;

/**
 * Created by apple on 08/08/2017.
 */
@Entity
@Table(name = "exceltest_employee")
public class EmployeeModel {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(name = "name")
    private String name;
    @Column(name = "address")
    private String address;
    @Column(name = "contactno")
    private String contactno;
    @Column(name = "email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString(){
        return "name="+name+",address="+address+",...="+email+",contactno="+contactno+",email="+email;
    }

}
