package fr.crypto.otp.provider.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "oid")
public class Oid implements Serializable {

    public Oid() {}

    public Oid(long id, long oid, String username) {
        this.oid = oid;
        this.username = username;
    }

    public Oid(long oid, String username, String password) {
        this.oid = oid;
        this.username = username;
        this.password = password;
    }

    public Oid(long oid, String username) {
        this.oid = oid;
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @JsonIgnore
    private long    id;

    @Column(name = "oid", nullable = false)
    private long    oid;

    @Column(name = "username", unique = true, nullable = false)
    private String  username;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String  password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
