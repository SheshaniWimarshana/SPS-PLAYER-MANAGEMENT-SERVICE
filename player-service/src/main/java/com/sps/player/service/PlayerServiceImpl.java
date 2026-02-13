package com.sps.player.service;

import com.sps.player.dto.PlayerRequestDTO;
import com.sps.player.dto.PlayerResponseDTO;
import com.sps.player.entity.Player;
import com.sps.player.exception.PlayerNotFoundException;
import com.sps.player.exception.DuplicatePlayerException;
import com.sps.player.mapper.PlayerMapper;
import com.sps.player.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of PlayerService
 * Handles all business logic for Player management
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> getAllPlayers() {
        log.info("Fetching all players");
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .map(playerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PlayerResponseDTO getPlayerById(Long id) {
        log.info("Fetching player with id: {}", id);
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));
        return playerMapper.toResponseDTO(player);
    }

    @Override
    public PlayerResponseDTO createPlayer(PlayerRequestDTO requestDTO) {
        log.info("Creating new player: {}", requestDTO.getName());

        // Check for duplicate name
        if (playerRepository.existsByNameIgnoreCase(requestDTO.getName())) {
            throw new DuplicatePlayerException("Player with name '" + requestDTO.getName() + "' already exists");
        }

        Player player = playerMapper.toEntity(requestDTO);
        Player savedPlayer = playerRepository.save(player);
        log.info("Player created successfully with id: {}", savedPlayer.getId());

        return playerMapper.toResponseDTO(savedPlayer);
    }

    @Override
    public PlayerResponseDTO updatePlayer(Long id, PlayerRequestDTO requestDTO) {
        log.info("Updating player with id: {}", id);

        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found with id: " + id));

        // Check for duplicate name (excluding current player)
        playerRepository.findByNameIgnoreCase(requestDTO.getName())
                .ifPresent(player -> {
                    if (!player.getId().equals(id)) {
                        throw new DuplicatePlayerException("Player with name '" + requestDTO.getName() + "' already exists");
                    }
                });

        playerMapper.updateEntityFromDTO(requestDTO, existingPlayer);
        Player updatedPlayer = playerRepository.save(existingPlayer);
        log.info("Player updated successfully with id: {}", id);

        return playerMapper.toResponseDTO(updatedPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        log.info("Deleting player with id: {}", id);

        if (!playerRepository.existsById(id)) {
            throw new PlayerNotFoundException("Player not found with id: " + id);
        }

        playerRepository.deleteById(id);
        log.info("Player deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> getPlayersByStatus(String status) {
        log.info("Fetching players with status: {}", status);
        List<Player> players = playerRepository.findByStatus(status);
        return players.stream()
                .map(playerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> searchPlayersByName(String name) {
        log.info("Searching players with name containing: {}", name);
        List<Player> players = playerRepository.findByNameContainingIgnoreCase(name);
        return players.stream()
                .map(playerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> getPlayersByAgeRange(int minAge, int maxAge) {
        log.info("Fetching players with age between {} and {}", minAge, maxAge);
        List<Player> players = playerRepository.findPlayersByAgeRange(minAge, maxAge);
        return players.stream()
                .map(playerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlayerResponseDTO> getPlayersByBirthdayRange(LocalDate startDate, LocalDate endDate) {
        log.info("Fetching players born between {} and {}", startDate, endDate);
        List<Player> players = playerRepository.findByBirthdayBetween(startDate, endDate);
        return players.stream()
                .map(playerMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countPlayersByStatus(String status) {
        log.info("Counting players with status: {}", status);
        return playerRepository.countByStatus(status);
    }

    @Override
    @Transactional(readOnly = true)
    public long getActivePlayersCount() {
        return countPlayersByStatus("ACTIVE");
    }

    @Override
    @Transactional(readOnly = true)
    public long getInactivePlayersCount() {
        return countPlayersByStatus("INACTIVE");
    }
}
