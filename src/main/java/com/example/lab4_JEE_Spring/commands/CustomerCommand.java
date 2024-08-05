package com.example.lab4_JEE_Spring.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CustomerCommand {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String driverLicenseNumber;
    @Getter
    private Set<Long> rentalId  = new HashSet<>();
}
