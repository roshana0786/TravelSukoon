package com.sukoon.expiendia.admin.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String itinerary;

    @Lob
    private String inclusions;

    @Lob
    private String exclusions;

    @Lob
    private String accommodation;

    @Lob
    private String cancellationPolicy;

    @Lob
    private String paymentPolicy;

    @Lob
    private String termsAndConditions;

    @Lob
    private String aboutDestination;

    private String destination;

    private int durationInDays;

    private double price;

    private String packageType; // e.g., Standard, Deluxe

    @OneToMany(mappedBy = "tourPackage", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<TourDay> tourDay;


}

