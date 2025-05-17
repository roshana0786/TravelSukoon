package com.sukoon.expiendia.admin.repository;

import com.sukoon.expiendia.admin.entity.TourDay;
import com.sukoon.expiendia.admin.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourDayRepository extends JpaRepository<TourDay, Long> {

    // Optional: Find all days for a given TourPackage
    List<TourDay> findByTourPackage(TourPackage tourPackage);

    // Optional: Find all days for a given TourPackage ID
    List<TourDay> findByTourPackageId(Long tourPackageId);
}

