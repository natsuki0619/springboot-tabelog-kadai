package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
}
