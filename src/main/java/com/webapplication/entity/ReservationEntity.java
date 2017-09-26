package com.webapplication.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by panagiotis on 6/8/2017.
 */
@Entity
@Table(name="Reservation")
public class ReservationEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "RESERVATION_ID")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "RESIDENCE_ID")
    private ResidenceEntity residenceEntity;

    @Column(name = "ARRIVAL_DATE")
    private Date arrivalDate;

    @Column(name = "DEPARTURE_DATE")
    private Date departureDate;

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ResidenceEntity getResidenceEntity() {
        return residenceEntity;
    }

    public void setResidenceEntity(ResidenceEntity residenceEntity) {
        this.residenceEntity = residenceEntity;
    }
}
