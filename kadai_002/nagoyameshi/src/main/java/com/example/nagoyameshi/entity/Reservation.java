package com.example.nagoyameshi.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "reservations")
@Data
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @Column(name = "reservation_time")
    private LocalTime reservationTime;

    @Column(name = "number_of_people")
    private Integer numberOfPeople;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public LocalTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public boolean isFutureReservation() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        return reservationDate.isAfter(currentDate)
                || (reservationDate.isEqual(currentDate) && reservationTime.isAfter(currentTime));
    }

    public boolean isPastReservation() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        return reservationDate.isBefore(currentDate)
                || (reservationDate.isEqual(currentDate) && reservationTime.isBefore(currentTime));
    }
}
