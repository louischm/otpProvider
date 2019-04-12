package fr.crypto.otp.provider.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "otp")
public class Otp implements Serializable {

    public Otp() {}

    public Otp(String value, Service service) {
        this.value = value;
        this.service = service;
    }

    public Otp(long id, String value) {
        this.id = id;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long    id;

    @Column(name = "value")
    private String    value;

    @OneToOne
    @JoinColumn(name = "service_id", nullable = false)
    @JsonIgnore
    private Service service;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
