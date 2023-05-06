package com.vpr.placestogetherapi.repository;

import com.vpr.placestogetherapi.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupPlaceRepository extends JpaRepository<Account, Long> {
}
