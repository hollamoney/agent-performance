package com.agent.agentperformance.repos;

import com.agent.agentperformance.entities.UserPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserPerformanceRepository extends JpaRepository<UserPerformance, Long> {


    List<UserPerformance> findByUserId(Long userId);

    @Query(value = "select * from user_performance where begin_time between :first and :second ",
            nativeQuery = true)
    List<UserPerformance> findBetweenTwoDates(@Param("first") Optional<String> first,
                                              @Param("second") Optional<String> second);

    @Query(value = "select * from user_performance where begin_time between :first and :second and user_id = :userId ",
    nativeQuery = true)
    List<UserPerformance> findBetweenTwoDateswithUserId(@Param("first") Optional<String> first,
                                                        @Param("second") Optional<String> second,
                                                        @Param("userId") Optional<Long> userId );
}
