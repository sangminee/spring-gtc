package com.example.gtc.src.tddEx.app.membership.repository;

import com.example.gtc.src.tddEx.app.enums.MembershipType;
import com.example.gtc.src.tddEx.app.membership.entity.Membership;
import com.example.gtc.src.tddEx.app.membership.repository.MembershipRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MembershipRepositoryTest {

    @Autowired
    private MembershipRepository membershipRepository;

    /* MembershipRepository가 Null인지 검사하는 테스트는 이제 불필요하므로 제거하자.
        (리팩토링의 단계를 진행한 것이다.)
    @Test
    public void MembershipRepository가Null이아님(){
        assertThat(membershipRepository).isNotNull();
    }
    */

    @Test
    public void 멤버십등록(){
        // given
        final Membership membership = Membership.builder()
                .userId("userID")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        // when
        final Membership result = membershipRepository.save(membership);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getUserId()).isEqualTo("userID");
        assertThat(result.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(result.getPoint()).isEqualTo(10000);

    }

    @Test
    public void 멤버십이존재하는지테스트(){
        // given
        final Membership membership = Membership.builder()
                .userId("userId")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        // when
        membershipRepository.save(membership);
        final Membership findResult = membershipRepository.findByUserIdAndMembershipType("userId",MembershipType.NAVER);

        // then
        assertThat(findResult).isNotNull();
        assertThat(findResult.getId()).isNotNull();
        assertThat(findResult.getUserId()).isEqualTo("userId");
        assertThat(findResult.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(findResult.getPoint()).isEqualTo(10000);
    }



}
