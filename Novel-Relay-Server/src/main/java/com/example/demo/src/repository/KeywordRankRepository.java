package com.example.demo.src.repository;

import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.KEYWORDRANK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRankRepository extends JpaRepository<KEYWORDRANK, Long> {
    public List<KEYWORDRANK> findTop5ByOrderByCountDesc();

    @Query("select k " +
            "from KEYWORDRANK k " +
            "where k.keyword = :keyword")
    KEYWORDRANK findByKeyword(@Param("keyword")String keyword);

    @Modifying(clearAutomatically = true)
    @Query(value = "update KEYWORDRANK k " +
            "set k.count = :count " +
            "where k.keyword = :keyword",nativeQuery = true )
    Integer updateCount(@Param("count")Integer count,@Param("keyword")String keyword);


}
