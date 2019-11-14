package models;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follows")
@NamedQueries({

@NamedQuery(
    name = "getfollowReports",
    query ="SELECT r FROM Report AS r WHERE r.employee IN ( SELECT f.employee FROM  Follow AS f WHERE f.login_id = :employee )"
    ),
@NamedQuery(
    name = "getfollowReportsCount",
    query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee IN ( SELECT f.employee FROM Follow  AS f WHERE  f.login_id = :employee )"
    )

})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "login_id", nullable = false)
    private Employee login_id;
    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;


public Integer getId() {
    return id;
}
 public void setId(Integer id) {
     this.id = id;
 }

 public Employee getEmployee() {
     return employee;
 }
 public void setEmployee(Employee employee) {
     this.employee = employee;
 }
 public Employee getLogin_id() {
     return login_id;
 }
 public void setLogin_id(Employee login_id ) {
     this.login_id = login_id;
 }
 public Timestamp getCreated_at() {
     return created_at;
 }
 public void setCreated_at(Timestamp created_at) {
     this.created_at = created_at;
 }


}

