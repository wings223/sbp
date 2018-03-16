package com.example.sbp.repository;

import com.example.sbp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Testdao extends JpaRepository<User,Long> {
}
