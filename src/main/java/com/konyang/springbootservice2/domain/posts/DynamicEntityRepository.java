package com.konyang.springbootservice2.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Repository interface
public interface DynamicEntityRepository extends JpaRepository<DynamicEntity, Long> {
    // custom methods
}