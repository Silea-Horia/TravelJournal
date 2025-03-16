package com.example.traveljournal.repository;

import com.example.traveljournal.domain.TravelLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface LocationRepository extends JpaRepository<TravelLocation, String> {
    Optional<TravelLocation> findById(String id);
}
