package com.example.lab4_JEE_Spring.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class RentalCommand {
    private Long id;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private int price;
    @Getter
    private Long customerId;
    @Getter
    private Long carId;
    private Long pickupLocationId;
    private Long dropoffLocationId;
}
