package com.vpr.placestogetherapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<GroupMembership> memberships = new HashSet<>();

    @OneToMany(mappedBy = "profileRequest", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friendshipRequest = new HashSet<>();

    @OneToMany(mappedBy = "profileAccept", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Friendship> friendshipAccept = new HashSet<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<MarkPlace> marks = new HashSet<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<CommentPlace> comments = new HashSet<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<RatingPlace> ratings = new HashSet<>();

}