package com.SQD20.SQD20.LIVEPROJECT.repository;

import com.SQD20.SQD20.LIVEPROJECT.domain.entites.AppUser;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
}
