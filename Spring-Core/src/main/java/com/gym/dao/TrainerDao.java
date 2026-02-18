package com.gym.dao;

import com.gym.model.Trainer;

import java.util.List;
import java.util.Optional;

public interface TrainerDao {
    void             save(Trainer trainer);
    void             update(Trainer trainer);
    Optional<Trainer> findById(Long id);
    List<Trainer>    findAll();
}