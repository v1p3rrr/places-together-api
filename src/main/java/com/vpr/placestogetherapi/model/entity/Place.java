package com.vpr.placestogetherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "groupPlaces")
@EqualsAndHashCode(exclude = "groupPlaces")
@Entity
public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long dgisId;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String type;

    @Column
    private String address;

    @Column
    private Double longitude;

    @Column
    private Double latitude;

    @JsonIgnore
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GroupPlace> groupPlaces = new HashSet<>();
}