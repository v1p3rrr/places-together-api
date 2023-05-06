package com.vpr.placestogetherapi.model.entity;

import com.vpr.placestogetherapi.model.enums.MarkPlaceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MarkPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_place_id")
    private GroupPlace groupPlace;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MarkPlaceStatus status;
}
