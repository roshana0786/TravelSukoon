package com.sukoon.expiendia.admin.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourPackageRequest {
    private String title;
    private String itinerary;
    private String inclusions;
    private String exclusions;
    private String accommodation;
    private String cancellationPolicy;
    private String paymentPolicy;
    private String termsAndConditions;
    private String aboutDestination;
    private String destination;
    private int durationInDays;
    private double price;
    private String packageType;
}

