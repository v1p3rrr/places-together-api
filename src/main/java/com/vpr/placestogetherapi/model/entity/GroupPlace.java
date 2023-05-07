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
@Entity
public class GroupPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "groupPlace", cascade = CascadeType.ALL)
    private Set<MarkPlace> marks = new HashSet<>();

    @OneToMany(mappedBy = "groupPlace", cascade = CascadeType.ALL)
    private Set<CommentPlace> comments = new HashSet<>();

    @OneToMany(mappedBy = "groupPlace", cascade = CascadeType.ALL)
    private Set<RatingPlace> ratings = new HashSet<>();
}