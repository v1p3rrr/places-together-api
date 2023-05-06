package com.vpr.placestogetherapi.repository;


import com.vpr.placestogetherapi.model.entity.CommentPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentPlaceRepository extends JpaRepository<CommentPlace, Long> {
}
