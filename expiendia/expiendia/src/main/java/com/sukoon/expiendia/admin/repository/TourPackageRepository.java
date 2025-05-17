package com.sukoon.expiendia.admin.repository;

import com.sukoon.expiendia.admin.entity.TourPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TourPackageRepository extends JpaRepository<TourPackage, Long> {
}

