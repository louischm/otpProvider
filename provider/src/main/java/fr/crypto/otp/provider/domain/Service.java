package fr.crypto.otp.provider.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service")
public class Service implements Serializable {

    public Service() {}

    public Service(String name, String usernameservice, Oid oid) {
        this.name = name;
        this.usernameservice = usernameservice;
        this.oid = oid;
     }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "usernameservice")
    private String  usernameservice;

    @Column(name = "activate")
    private boolean activate;

    @Column(name = "login")
    private boolean login;

    @OneToOne(targetEntity = Oid.class)
    @JoinColumn(name = "oid_id", nullable = false)
    @JsonIgnore
    private Oid oid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsernameservice() {
        return usernameservice;
    }

    public void setUsernameservice(String usernameservice) {
        this.usernameservice = usernameservice;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }
}
