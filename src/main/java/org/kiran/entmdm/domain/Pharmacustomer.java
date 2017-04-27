package org.kiran.entmdm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Pharmacustomer.
 */
@Entity
@Table(name = "pharmacustomer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pharmacustomer")
public class Pharmacustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "pharmacustomernr", length = 10, nullable = false)
    private String pharmacustomernr;

    @NotNull
    @Size(max = 200)
    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @NotNull
    @Size(max = 100)
    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @NotNull
    @Size(max = 100)
    @Column(name = "state", length = 100, nullable = false)
    private String state;

    @NotNull
    @Max(value = 5)
    @Column(name = "zipcode", nullable = false)
    private Integer zipcode;

    @NotNull
    @Size(max = 100)
    @Column(name = "country", length = 100, nullable = false)
    private String country;

    @Column(name = "pharmashipto")
    private Integer pharmashipto;

    @Column(name = "pharmabillto")
    private Integer pharmabillto;

    @Column(name = "dealicensenr")
    private String dealicensenr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPharmacustomernr() {
        return pharmacustomernr;
    }

    public Pharmacustomer pharmacustomernr(String pharmacustomernr) {
        this.pharmacustomernr = pharmacustomernr;
        return this;
    }

    public void setPharmacustomernr(String pharmacustomernr) {
        this.pharmacustomernr = pharmacustomernr;
    }

    public String getAddress() {
        return address;
    }

    public Pharmacustomer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Pharmacustomer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Pharmacustomer state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public Pharmacustomer zipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public Pharmacustomer country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPharmashipto() {
        return pharmashipto;
    }

    public Pharmacustomer pharmashipto(Integer pharmashipto) {
        this.pharmashipto = pharmashipto;
        return this;
    }

    public void setPharmashipto(Integer pharmashipto) {
        this.pharmashipto = pharmashipto;
    }

    public Integer getPharmabillto() {
        return pharmabillto;
    }

    public Pharmacustomer pharmabillto(Integer pharmabillto) {
        this.pharmabillto = pharmabillto;
        return this;
    }

    public void setPharmabillto(Integer pharmabillto) {
        this.pharmabillto = pharmabillto;
    }

    public String getDealicensenr() {
        return dealicensenr;
    }

    public Pharmacustomer dealicensenr(String dealicensenr) {
        this.dealicensenr = dealicensenr;
        return this;
    }

    public void setDealicensenr(String dealicensenr) {
        this.dealicensenr = dealicensenr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pharmacustomer pharmacustomer = (Pharmacustomer) o;
        if (pharmacustomer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pharmacustomer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pharmacustomer{" +
            "id=" + id +
            ", pharmacustomernr='" + pharmacustomernr + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipcode='" + zipcode + "'" +
            ", country='" + country + "'" +
            ", pharmashipto='" + pharmashipto + "'" +
            ", pharmabillto='" + pharmabillto + "'" +
            ", dealicensenr='" + dealicensenr + "'" +
            '}';
    }
}
