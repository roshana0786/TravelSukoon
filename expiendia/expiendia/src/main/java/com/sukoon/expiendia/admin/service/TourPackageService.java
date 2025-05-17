package com.sukoon.expiendia.admin.service;

import com.sukoon.expiendia.admin.entity.TourPackage;
import com.sukoon.expiendia.admin.repository.TourPackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourPackageService {

    private final TourPackageRepository repository;

    public TourPackageService(TourPackageRepository repository) {
        this.repository = repository;
    }

    public TourPackage saveTourPackage(TourPackage tourPackage) {
        return repository.save(tourPackage);
    }

    public List<TourPackage> getAllPackages() {
        List<TourPackage> all = repository.findAll();
        return all;
    }

    public Optional<TourPackage> getPackageById(Long id) {
        return repository.findById(id);
    }

    public void deleteTourPackage(Long id) {
        repository.deleteById(id);
    }
}
