package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByDgisId(Long dgisId);
    Optional<Place> findByName(String name);
    List<Place> findByAddress(String address);
    List<Place> findByType(String type);
    List<Place> findByNameContaining(String partOfName);

}