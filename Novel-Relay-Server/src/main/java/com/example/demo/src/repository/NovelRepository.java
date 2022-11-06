package com.example.demo.src.repository;

import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import com.example.demo.src.entity.LIKEINFO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelRepository extends JpaRepository<NOVEL, Long>, NovelRepositoryCustom{


    @Modifying(clearAutomatically = true)
    @Query(value = "update NOVEL n " +
            "set n.like_count = :like_count " +
            "where n.novel_id = :novel_id ",nativeQuery = true )
    Integer updateLikeCnt(@Param("like_count")Long like_count, @Param("novel_id")Long novel_id);


    @Query("select n.like_count " +
            "from NOVEL n " +
            "where n.novel_id = :novel_id")
    Long findLikeCnt(@Param("novel_id")Long novel_id);


    @Query ("select n " +
            "from NOVEL n " +
            "where n.user.user_id = :user_id")
    List<NOVEL> findByUserIdInWrite(Long user_id);


    @Query ("select n " +
            "from NOVEL n " +
            "where n.novel_id in " +
            "   (select r.novel.novel_id " +
            "   from RELAY r " +
            "   where r.user.user_id = :user_id)")
    List<NOVEL> findByUserIdInParticipate(Long user_id);

    @Query ("select n " +
            "from NOVEL n " +
            "where n.novel_id in " +
            "   (select l.novel.novel_id" +
            "   from LIKEINFO l" +
            "   where l.user.user_id = :user_id)")
    List<NOVEL> findByUserIdInLike(Long user_id);

    @Query ("select n "+
            "from NOVEL n " +
            "where n.category = :category")
    List<NOVEL> findByCateInGroup(String category);

    @Query ("select n "+
            "from NOVEL n " +
            "order by n.like_count desc ")
    List<NOVEL> findByAll();


}
