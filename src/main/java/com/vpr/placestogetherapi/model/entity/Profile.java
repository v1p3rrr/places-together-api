package com.vpr.placestogetherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"account", "memberships", "friendshipRequest", "friendshipAccept", "marks", "comments", "ratings"})
@EqualsAndHashCode(exclude = {"account", "memberships", "friendshipRequest", "friendshipAccept", "marks", "comments", "ratings"})
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = true)
    private String profilePictureLink;

    @Column(nullable = false)
    private String status = "";

    @JsonIgnoreProperties("profile")
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @JsonIgnore
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<GroupMembership> memberships = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "profileRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friendshipRequest = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "profileAccept", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friendshipAccept = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<MarkPlace> marks = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<CommentPlace> comments = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<RatingPlace> ratings = new HashSet<>();

}