package org.example;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
    List<Apartment> findByBuilding(Building building);
    List<Apartment> findByOwner(Owner owner);
}
