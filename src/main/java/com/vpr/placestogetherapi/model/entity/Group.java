package com.vpr.placestogetherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"groupPlaces", "memberships"})
@EqualsAndHashCode(exclude = {"groupPlaces", "memberships"})
@Entity(name = "users_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String groupPictureLink = "";

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupPlace> groupPlaces = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private Set<GroupMembership> memberships = new HashSet<>();
}