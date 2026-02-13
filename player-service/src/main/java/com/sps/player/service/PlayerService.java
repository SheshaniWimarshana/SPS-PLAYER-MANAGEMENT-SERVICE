package com.sps.player.service;

import com.sps.player.dto.PlayerRequestDTO;
import com.sps.player.dto.PlayerResponseDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for Player management operations
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
public interface PlayerService {

    /**
     * Get all players
     */
    List<PlayerResponseDTO> getAllPlayers();

    /**
     * Get player by ID
     */
    PlayerResponseDTO getPlayerById(Long id);

    /**
     * Create new player
     */
    PlayerResponseDTO createPlayer(PlayerRequestDTO requestDTO);

    /**
     * Update existing player
     */
    PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO requestDTO);

    /**
     * Delete player
     */
    void deletePlayer(Long id);

    /**
     * Get players by status
     */
    List<PlayerResponseDTO> getPlayersByStatus(String status);

    /**
     * Search players by name
     */
    List<PlayerResponseDTO> searchPlayersByName(String name);

    /**
     * Get players by age range
     */
    List<PlayerResponseDTO> getPlayersByAgeRange(int minAge, int maxAge);

    /**
     * Get players born between dates
     */
    List<PlayerResponseDTO> getPlayersByBirthdayRange(LocalDate startDate, LocalDate endDate);

    /**
     * Count players by status
     */
    long countPlayersByStatus(String status);

    /**
     * Get active players count
     */
    long getActivePlayersCount();

    /**
     * Get inactive players count
     */
    long getInactivePlayersCount();
}
