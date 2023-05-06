package com.vpr.placestogetherapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String groupPictureLink = "";

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupPlace> groupPlaces = new HashSet<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupMembership> memberships = new HashSet<>();
}