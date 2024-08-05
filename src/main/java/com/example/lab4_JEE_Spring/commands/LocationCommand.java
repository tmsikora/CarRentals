package com.example.lab4_JEE_Spring.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationCommand {
    private Long id;
    private String locationName;
    private String city;
    private String address;
}
