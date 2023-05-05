package com.vpr.placestogetherapi.model.entity;

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
    @JoinColumn(name = "profile1_id")
    private Profile profile1;

    @ManyToOne
    @JoinColumn(name = "profile2_id")
    private Profile profile2;

    private String status; //todo enum
}