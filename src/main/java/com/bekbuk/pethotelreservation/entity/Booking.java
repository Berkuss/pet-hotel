package com.bekbuk.pethotelreservation.entity;

import com.bekbuk.pethotelreservation.model.enums.CheckedStatus;
import com.bekbuk.pethotelreservation.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "booking")
@Data
@SequenceGenerator(sequenceName = "seq_booking", name = "seq_booking")
@Builder
public class Booking {

    @Id
    @GeneratedValue(generator = "seq_booking")
    private Long id;

    @Column(name = "pet_name")
    private String petName;

    @Column(name = "checkin_date")
    private Date checkinDate;

    @Column(name = "checkout_date")
    private Date checkoutDate;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "owner_phone")
    private String ownerPhone;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name = "checking_status")
    @Enumerated(EnumType.STRING)
    private CheckedStatus checkingStatus;

    public Booking() {

    }
}
