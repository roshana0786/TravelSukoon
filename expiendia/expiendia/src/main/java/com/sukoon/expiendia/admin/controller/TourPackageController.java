package com.sukoon.expiendia.admin.controller;

import com.sukoon.expiendia.admin.entity.TourPackage;
import com.sukoon.expiendia.admin.service.TourPackageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/tour-packages")
@CrossOrigin(origins = "*")
public class TourPackageController {

    private final TourPackageService service;

    public TourPackageController(TourPackageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TourPackage> createPackage(@RequestBody TourPackage tourPackage) {
        return ResponseEntity.ok(service.saveTourPackage(tourPackage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id) {
        service.deleteTourPackage(id);
        return ResponseEntity.ok().build();
    }
}
