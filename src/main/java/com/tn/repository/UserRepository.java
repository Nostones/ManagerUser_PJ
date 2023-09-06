package com.tn.repository;

import com.tn.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Override
    Page<User> findAll(Pageable pageable);

    Optional<User> findByUserName(String username);


    Optional<User> findById (Integer id);

    List<User> findByGroupM_Id (Integer id);

}
