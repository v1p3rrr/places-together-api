package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
