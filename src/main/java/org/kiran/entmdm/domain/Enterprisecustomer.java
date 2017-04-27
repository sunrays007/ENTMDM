package org.kiran.entmdm.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Enterprisecustomer.
 */
@Entity
@Table(name = "enterprisecustomer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enterprisecustomer")
public class Enterprisecustomer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "enterprisecustomernr", length = 10, nullable = false)
    private String enterprisecustomernr;

    @Column(name = "pharmaacctnr")
    private String pharmaacctnr;

    @Column(name = "medicalacctnr")
    private String medicalacctnr;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Max(value = 99999)
    @Column(name = "zipcode")
    private Integer zipcode;

    @Column(name = "country")
    private String country;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "pharma_ship_to", nullable = false)
    private Integer pharmaShipTo;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "pharmabillto", nullable = false)
    private Integer pharmabillto;

    @NotNull
    @Min(value = 0)
    @Max(value = 1)
    @Column(name = "medicalshipto", nullable = false)
    private Integer medicalshipto;

    @Column(name = "dealicensenr")
    private String dealicensenr;

    @Column(name = "healthfacilitylicensenr")
    private String healthfacilitylicensenr;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnterprisecustomernr() {
        return enterprisecustomernr;
    }

    public Enterprisecustomer enterprisecustomernr(String enterprisecustomernr) {
        this.enterprisecustomernr = enterprisecustomernr;
        return this;
    }

    public void setEnterprisecustomernr(String enterprisecustomernr) {
        this.enterprisecustomernr = enterprisecustomernr;
    }

    public String getPharmaacctnr() {
        return pharmaacctnr;
    }

    public Enterprisecustomer pharmaacctnr(String pharmaacctnr) {
        this.pharmaacctnr = pharmaacctnr;
        return this;
    }

    public void setPharmaacctnr(String pharmaacctnr) {
        this.pharmaacctnr = pharmaacctnr;
    }

    public String getMedicalacctnr() {
        return medicalacctnr;
    }

    public Enterprisecustomer medicalacctnr(String medicalacctnr) {
        this.medicalacctnr = medicalacctnr;
        return this;
    }

    public void setMedicalacctnr(String medicalacctnr) {
        this.medicalacctnr = medicalacctnr;
    }

    public String getAddress() {
        return address;
    }

    public Enterprisecustomer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Enterprisecustomer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Enterprisecustomer state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipcode() {
        return zipcode;
    }

    public Enterprisecustomer zipcode(Integer zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(Integer zipcode) {
        this.zipcode = zipcode;
    }

    public String getCountry() {
        return country;
    }

    public Enterprisecustomer country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPharmaShipTo() {
        return pharmaShipTo;
    }

    public Enterprisecustomer pharmaShipTo(Integer pharmaShipTo) {
        this.pharmaShipTo = pharmaShipTo;
        return this;
    }

    public void setPharmaShipTo(Integer pharmaShipTo) {
        this.pharmaShipTo = pharmaShipTo;
    }

    public Integer getPharmabillto() {
        return pharmabillto;
    }

    public Enterprisecustomer pharmabillto(Integer pharmabillto) {
        this.pharmabillto = pharmabillto;
        return this;
    }

    public void setPharmabillto(Integer pharmabillto) {
        this.pharmabillto = pharmabillto;
    }

    public Integer getMedicalshipto() {
        return medicalshipto;
    }

    public Enterprisecustomer medicalshipto(Integer medicalshipto) {
        this.medicalshipto = medicalshipto;
        return this;
    }

    public void setMedicalshipto(Integer medicalshipto) {
        this.medicalshipto = medicalshipto;
    }

    public String getDealicensenr() {
        return dealicensenr;
    }

    public Enterprisecustomer dealicensenr(String dealicensenr) {
        this.dealicensenr = dealicensenr;
        return this;
    }

    public void setDealicensenr(String dealicensenr) {
        this.dealicensenr = dealicensenr;
    }

    public String getHealthfacilitylicensenr() {
        return healthfacilitylicensenr;
    }

    public Enterprisecustomer healthfacilitylicensenr(String healthfacilitylicensenr) {
        this.healthfacilitylicensenr = healthfacilitylicensenr;
        return this;
    }

    public void setHealthfacilitylicensenr(String healthfacilitylicensenr) {
        this.healthfacilitylicensenr = healthfacilitylicensenr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enterprisecustomer enterprisecustomer = (Enterprisecustomer) o;
        if (enterprisecustomer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, enterprisecustomer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Enterprisecustomer{" +
            "id=" + id +
            ", enterprisecustomernr='" + enterprisecustomernr + "'" +
            ", pharmaacctnr='" + pharmaacctnr + "'" +
            ", medicalacctnr='" + medicalacctnr + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zipcode='" + zipcode + "'" +
            ", country='" + country + "'" +
            ", pharmaShipTo='" + pharmaShipTo + "'" +
            ", pharmabillto='" + pharmabillto + "'" +
            ", medicalshipto='" + medicalshipto + "'" +
            ", dealicensenr='" + dealicensenr + "'" +
            ", healthfacilitylicensenr='" + healthfacilitylicensenr + "'" +
            '}';
    }
}
