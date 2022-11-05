package com.example.demo.src.repository;

import com.example.demo.src.entity.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<USER, Long> {
    USER findByUserIdx(Long id);
}
