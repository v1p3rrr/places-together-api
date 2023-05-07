package com.vpr.placestogetherapi.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vpr.placestogetherapi.model.enums.GroupMemberRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GroupMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties("memberships") // ignore the memberships field in Group to prevent infinite recursion
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @JsonIgnoreProperties("memberships") // ignore the memberships field in Group to prevent infinite recursion
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupMemberRole role;
}