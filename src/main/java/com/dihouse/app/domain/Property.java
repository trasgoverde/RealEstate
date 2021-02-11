package com.dihouse.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.dihouse.app.domain.enumeration.BuildingType;

import com.dihouse.app.domain.enumeration.ServiceType;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "property")
public class Property implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private Double price;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "building_type")
    private BuildingType buildingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_type")
    private ServiceType serviceType;

    @NotNull
    @Column(name = "ref", nullable = false)
    private String ref;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "sold")
    private Boolean sold;

    @Column(name = "terrace")
    private Boolean terrace;

    @Column(name = "m_2")
    private Integer m2;

    @Column(name = "number_bedroom")
    private Integer numberBedroom;

    @Column(name = "elevator")
    private Boolean elevator;

    @Column(name = "furnished")
    private Boolean furnished;

    @Column(name = "pool")
    private Boolean pool;

    @Column(name = "garage")
    private Boolean garage;

    @Column(name = "number_wc")
    private Integer numberWC;

    @Column(name = "ac")
    private Boolean ac;

    @OneToOne
    @JoinColumn(unique = true)
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties(value = "properties", allowSetters = true)
    private Appuser appuser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Property name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public Property price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public Property description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BuildingType getBuildingType() {
        return buildingType;
    }

    public Property buildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
        return this;
    }

    public void setBuildingType(BuildingType buildingType) {
        this.buildingType = buildingType;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public Property serviceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getRef() {
        return ref;
    }

    public Property ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Boolean isVisible() {
        return visible;
    }

    public Property visible(Boolean visible) {
        this.visible = visible;
        return this;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean isSold() {
        return sold;
    }

    public Property sold(Boolean sold) {
        this.sold = sold;
        return this;
    }

    public void setSold(Boolean sold) {
        this.sold = sold;
    }

    public Boolean isTerrace() {
        return terrace;
    }

    public Property terrace(Boolean terrace) {
        this.terrace = terrace;
        return this;
    }

    public void setTerrace(Boolean terrace) {
        this.terrace = terrace;
    }

    public Integer getm2() {
        return m2;
    }

    public Property m2(Integer m2) {
        this.m2 = m2;
        return this;
    }

    public void setm2(Integer m2) {
        this.m2 = m2;
    }

    public Integer getNumberBedroom() {
        return numberBedroom;
    }

    public Property numberBedroom(Integer numberBedroom) {
        this.numberBedroom = numberBedroom;
        return this;
    }

    public void setNumberBedroom(Integer numberBedroom) {
        this.numberBedroom = numberBedroom;
    }

    public Boolean isElevator() {
        return elevator;
    }

    public Property elevator(Boolean elevator) {
        this.elevator = elevator;
        return this;
    }

    public void setElevator(Boolean elevator) {
        this.elevator = elevator;
    }

    public Boolean isFurnished() {
        return furnished;
    }

    public Property furnished(Boolean furnished) {
        this.furnished = furnished;
        return this;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Boolean isPool() {
        return pool;
    }

    public Property pool(Boolean pool) {
        this.pool = pool;
        return this;
    }

    public void setPool(Boolean pool) {
        this.pool = pool;
    }

    public Boolean isGarage() {
        return garage;
    }

    public Property garage(Boolean garage) {
        this.garage = garage;
        return this;
    }

    public void setGarage(Boolean garage) {
        this.garage = garage;
    }

    public Integer getNumberWC() {
        return numberWC;
    }

    public Property numberWC(Integer numberWC) {
        this.numberWC = numberWC;
        return this;
    }

    public void setNumberWC(Integer numberWC) {
        this.numberWC = numberWC;
    }

    public Boolean isAc() {
        return ac;
    }

    public Property ac(Boolean ac) {
        this.ac = ac;
        return this;
    }

    public void setAc(Boolean ac) {
        this.ac = ac;
    }

    public Location getLocation() {
        return location;
    }

    public Property location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Appuser getAppuser() {
        return appuser;
    }

    public Property appuser(Appuser appuser) {
        this.appuser = appuser;
        return this;
    }

    public void setAppuser(Appuser appuser) {
        this.appuser = appuser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        return id != null && id.equals(((Property) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Property{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", description='" + getDescription() + "'" +
            ", buildingType='" + getBuildingType() + "'" +
            ", serviceType='" + getServiceType() + "'" +
            ", ref='" + getRef() + "'" +
            ", visible='" + isVisible() + "'" +
            ", sold='" + isSold() + "'" +
            ", terrace='" + isTerrace() + "'" +
            ", m2=" + getm2() +
            ", numberBedroom=" + getNumberBedroom() +
            ", elevator='" + isElevator() + "'" +
            ", furnished='" + isFurnished() + "'" +
            ", pool='" + isPool() + "'" +
            ", garage='" + isGarage() + "'" +
            ", numberWC=" + getNumberWC() +
            ", ac='" + isAc() + "'" +
            "}";
    }
}
