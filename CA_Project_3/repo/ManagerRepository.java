package com.team1.iss.trial.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team1.iss.trial.domain.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {

}
