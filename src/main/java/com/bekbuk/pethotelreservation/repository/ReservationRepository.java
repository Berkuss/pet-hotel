package com.bekbuk.pethotelreservation.repository;

import com.bekbuk.pethotelreservation.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByPetName(String petName);
}
