package com.example.demo.src.repository;

import com.example.demo.src.entity.KEYWORD;
import com.example.demo.src.entity.KEYWORDRANK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRankRepository extends JpaRepository<KEYWORDRANK, Long> {
    public List<KEYWORDRANK> findTop5ByOrderByCountDesc();
}
