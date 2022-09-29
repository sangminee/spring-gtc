package com.example.gtc.src.tddEx.app.membership.repository;

import com.example.gtc.src.tddEx.app.enums.MembershipType;
import com.example.gtc.src.tddEx.app.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByUserIdAndMembershipType(final String userId, final MembershipType membershipType);
}
