package org.goravski.restaurantVoting.repository.vote;

import org.goravski.restaurantVoting.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface JpaVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id =:id")
    int delete(@Param("id") int id);

    @Transactional
    @EntityGraph(Vote.GRATH_RESTAURANT_JOIN)
    @Query("SELECT v FROM Vote v ORDER BY v.dateVote DESC")
    List<Vote> getAll();
}
