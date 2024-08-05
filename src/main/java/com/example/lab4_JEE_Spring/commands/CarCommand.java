package com.example.lab4_JEE_Spring.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CarCommand {
    private Long id;
    private String carMaker;
    private String carModel;
    private String productionYear;
    private String licensePlate;
    private String status;
    @Getter
    private Long locationId;
}
