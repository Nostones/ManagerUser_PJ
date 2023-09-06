package com.tn.repository;

import com.tn.entity.Group_M;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group_M,Integer> {
    @Override
    Page<Group_M> findAll(Pageable pageable);

    Optional<Group_M> findById (Integer id);
}
