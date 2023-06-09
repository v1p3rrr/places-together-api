package com.vpr.placestogetherapi.model.entity;

import com.vpr.placestogetherapi.model.enums.FriendshipStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_request_id")
    private Profile profileRequest;

    @ManyToOne
    @JoinColumn(name = "profile_accept_id")
    private Profile profileAccept;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
}