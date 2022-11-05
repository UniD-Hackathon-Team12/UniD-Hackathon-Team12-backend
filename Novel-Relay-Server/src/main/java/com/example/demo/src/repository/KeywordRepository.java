package com.example.demo.src.repository;

import com.example.demo.src.entity.KEYWORD;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.src.entity.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<KEYWORD, Long> {
}
