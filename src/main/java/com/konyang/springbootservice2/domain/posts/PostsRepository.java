package com.konyang.springbootservice2.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    // This is a custom query method that uses JPQL (Java Persistence Query Language) to retrieve all Posts entities
    // in descending order of their ID.
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
