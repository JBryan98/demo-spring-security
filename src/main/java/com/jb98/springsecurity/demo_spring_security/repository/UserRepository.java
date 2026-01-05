package com.jb98.springsecurity.demo_spring_security.repository;

import com.jb98.springsecurity.demo_spring_security.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u.id FROM User u")
    Page<Long> findAllIdsOnly(Pageable pageable);

    @EntityGraph(attributePaths = "roles")
    List<User> findByIdIn(Set<Long> ids);

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String email);
}
