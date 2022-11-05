package com.example.demo.src.repository;

import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.LIKEINFO;
import com.example.demo.src.entity.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface LikeInfoRepository extends JpaRepository<LIKEINFO, Long> {
    @Query("select l " +
            "from LIKEINFO l " +
            "where l.user.user_id = :user_id and l.novel.novel_id = :novel_id ")
    LIKEINFO findLIKEINFO(@Param("user_id")Long user_id, @Param("novel_id")Long novel_id);

    @Modifying(clearAutomatically = true)
    @Query(value = "update LIKEINFO l " +
            "set l.likeinfo_active = :likeinfo_active " +
            "where l.user_id = :user_id and l.novel_id = :novel_id  ",nativeQuery = true )
    Integer changeActive(@Param("likeinfo_active") boolean likeinfo_active, @Param("user_id")Long user_id, @Param("novel_id")Long novel_id);










}
