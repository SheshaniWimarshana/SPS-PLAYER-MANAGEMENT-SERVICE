package com.sps.player.mapper;

import com.sps.player.dto.PlayerDTO;
import com.sps.player.dto.PlayerRequestDTO;
import com.sps.player.dto.PlayerResponseDTO;
import com.sps.player.entity.Player;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Player entity and DTOs
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Component
public class PlayerMapper {

    /**
     * Convert Player entity to PlayerDTO
     */
    public PlayerDTO toDTO(Player player) {
        if (player == null) {
            return null;
        }

        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setBirthday(player.getBirthday());
        dto.setImageName(player.getImageName());
        dto.setStatus(player.getStatus());
        dto.setAge(player.getAge());
        dto.setCreatedAt(player.getCreatedAt());
        dto.setUpdatedAt(player.getUpdatedAt());

        return dto;
    }

    /**
     * Convert Player entity to PlayerResponseDTO
     */
    public PlayerResponseDTO toResponseDTO(Player player) {
        if (player == null) {
            return null;
        }

        PlayerResponseDTO dto = new PlayerResponseDTO();
        dto.setId(player.getId());
        dto.setName(player.getName());
        dto.setBirthday(player.getBirthday());
        dto.setImageName(player.getImageName());
        dto.setStatus(player.getStatus());
        dto.setAge(player.getAge());
        dto.setCreatedAt(player.getCreatedAt());
        dto.setUpdatedAt(player.getUpdatedAt());

        return dto;
    }

    /**
     * Convert PlayerRequestDTO to Player entity
     */
    public Player toEntity(PlayerRequestDTO requestDTO) {
        if (requestDTO == null) {
            return null;
        }

        Player player = new Player();
        player.setName(requestDTO.getName());
        player.setBirthday(requestDTO.getBirthday());
        player.setImageName(requestDTO.getImageName());
        player.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : "ACTIVE");

        return player;
    }

    /**
     * Update existing Player entity with PlayerRequestDTO data
     */
    public void updateEntityFromDTO(PlayerRequestDTO requestDTO, Player player) {
        if (requestDTO == null || player == null) {
            return;
        }

        player.setName(requestDTO.getName());
        player.setBirthday(requestDTO.getBirthday());
        player.setImageName(requestDTO.getImageName());
        player.setStatus(requestDTO.getStatus());
    }
}
