package com.example.nagoyameshi.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Reservation;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReservationRegisterForm;
import com.example.nagoyameshi.repository.ReservationRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository, ShopRepository shopRepository,
            UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.shopRepository = shopRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void create(ReservationRegisterForm reservationRegisterForm) {
        Reservation reservation = new Reservation();
        Shop shop = shopRepository.getReferenceById(reservationRegisterForm.getShopId());
        User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
        LocalDate reservationDate = LocalDate.parse(reservationRegisterForm.getReservationDate());
        LocalTime reservationTime = LocalTime.parse(reservationRegisterForm.getReservationTime());

        reservation.setShop(shop);
        reservation.setUser(user);
        reservation.setReservationDate(reservationDate);
        reservation.setReservationTime(reservationTime);
        reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
        reservation.setRemarks(reservationRegisterForm.getRemarks());

        reservationRepository.save(reservation);
    }

    public boolean isWithinOpening(String fromReservationTime, String opening) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime parsedOpening = LocalTime.parse(opening, formatter);
        LocalTime parsedFromReservationTime = LocalTime.parse(fromReservationTime, formatter);
        return parsedFromReservationTime.isAfter(parsedOpening) || parsedFromReservationTime.equals(parsedOpening);
    }

    public boolean isWithinClosed(String fromReservationTime, String closed) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime parsedClosed = LocalTime.parse(closed, formatter);
        LocalTime parsedFromReservationTime = LocalTime.parse(fromReservationTime, formatter);
        return parsedFromReservationTime.isBefore(parsedClosed) || parsedFromReservationTime.equals(parsedClosed);
    }

    public long getTotalReservationCount() {
        return reservationRepository.count();
    }
}