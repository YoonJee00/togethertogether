package com.together3.domain.subscribe;

import com.together3.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
    @Modifying
    @Query(value = "INSERT INTO subscribe(from_user_id, to_user_id, create_date) VALUES(:from_user_id, :to_user_id, now())", nativeQuery = true)
    void mSubscribe(@Param("from_user_id") int fromUserId, @Param("to_user_id") int toUserId);

    @Modifying
    @Query(value = "DELETE FROM subscribe WHERE from_user_id = :from_user_id AND to_user_id = :to_user_id", nativeQuery = true)
    void mUnSubscribe(@Param("from_user_id") int fromUserId, @Param("to_user_id") int toUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE from_user_id = :principalId AND to_user_id = :pageUserId", nativeQuery = true)
    int mSubscribeState(int principalId, int pageUserId);

    @Query(value = "SELECT COUNT(*) FROM subscribe WHERE from_user_id = :pageUserId", nativeQuery = true)
    int mSubscribeCount(int pageUserId);
}
