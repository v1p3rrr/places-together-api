package com.vpr.placestogetherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"marks", "comments", "ratings"})
@EqualsAndHashCode(exclude = {"marks", "comments", "ratings"})
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

    @JsonIgnore
    @OneToMany(mappedBy = "groupPlace", cascade = CascadeType.ALL)
    private Set<MarkPlace> marks = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "groupPlace", cascade = CascadeType.ALL)
    private Set<CommentPlace> comments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "groupPlace", cascade = CascadeType.ALL)
    private Set<RatingPlace> ratings = new HashSet<>();
}