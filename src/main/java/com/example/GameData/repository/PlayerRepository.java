package com.example.GameData.repository;


import com.example.GameData.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findAll();

}
