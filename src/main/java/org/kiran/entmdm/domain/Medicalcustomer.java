package org.kiran.entmdm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Medicalcustomer.
 */
@Entity
@Table(name = "medicalcustomer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "medicalcustomer")
public class Medicalcustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "medcustomernr", length = 10, nullable = false)
    private String medcustomernr;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @NotNull
    @Max(value = 99999)
    @Column(name = "zipcode", nullable = false)
    private Integer zipcode;

    @Column(name = "country")
    private String country;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedcustomernr() {
        return medcustomernr;
    }

    public Medicalcustomer medcustomernr(String medcustomernr) {
        this.medcustomernr = medcustomernr;
        return this;
    }

    public void setMedcustomernr(String medcustomernr) {
        this.medcustomernr = medcustomernr;
    }

    public String getAddress() {
        return address;
    }

    public Medicalcustomer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Medicalcustomer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Medicalcustomer state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public Medicalcustomer zipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public Medicalcustomer country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Medicalcustomer medicalcustomer = (Medicalcustomer) o;
        if (medicalcustomer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, medicalcustomer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Medicalcustomer{" +
            "id=" + id +
            ", medcustomernr='" + medcustomernr + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipcode='" + zipcode + "'" +
            ", country='" + country + "'" +
            '}';
    }
}
