package com.sukoon.expiendia.admin.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int dayNumber;

    @Lob
    private String description;

    @ManyToOne
    @JoinColumn(name = "tour_package_id")
    @JsonBackReference
    private TourPackage tourPackage;

}
