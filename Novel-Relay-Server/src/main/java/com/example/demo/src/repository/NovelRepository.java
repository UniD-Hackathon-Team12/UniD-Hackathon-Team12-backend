package com.example.demo.src.repository;

import com.example.demo.src.entity.NOVEL;
import com.example.demo.src.entity.RELAY;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NovelRepository extends JpaRepository<NOVEL, Long>, NovelRepositoryCustom{


    @Modifying(clearAutomatically = true)
    @Query(value = "update NOVEL n " +
            "set n.like_count = :like_count " +
            "where n.novel_id = :novel_id ",nativeQuery = true )
    Integer updateLikeCnt(@Param("like_count")Long like_count, @Param("novel_id")Long novel_id);



}
