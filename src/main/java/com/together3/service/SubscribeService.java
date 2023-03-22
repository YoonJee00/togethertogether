package com.together3.service;

import com.together3.domain.subscribe.SubscribeRepository;
import com.together3.handler.ex.CustomApiException;
import com.together3.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em;

    @Transactional
    public void 구독하기(int from_user_id, int to_user_id) {
        try {
            subscribeRepository.mSubscribe(from_user_id, to_user_id);
        } catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }
    }

    @Transactional
    public void 구독취소하기(int from_user_id, int to_user_id) {
        subscribeRepository.mUnSubscribe(from_user_id, to_user_id);
    }

    @Transactional(readOnly = true)
    public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE from_user_id = ? AND to_user_id = u.id), 1, 0) subscribeState, ");
        sb.append("if ((? = u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.to_user_id ");
        sb.append("WHERE s.from_user_id = ?"); // 세미콜론 첨부하면 안됨

        // 1.물음표 principalId
        // 2.물음표 principalId
        // 3.물음표 pageUserId
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);

        JpaResultMapper result = new JpaResultMapper();
        List<SubscribeDto> subscribeDto = result.list(query, SubscribeDto.class);

        return subscribeDto;
    }
}
