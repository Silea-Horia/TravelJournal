package com.example.traveljournal.repository;

import com.example.traveljournal.domain.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByNameContainingIgnoreCaseAndRatingIn(String name, List<Integer> ratings, Pageable pageable);
    Page<Location> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Location> findByRatingIn(List<Integer> ratings, Pageable pageable);
}
