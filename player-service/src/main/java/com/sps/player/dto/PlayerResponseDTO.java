package com.sps.player.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response DTO for Player
 * Used for API responses
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerResponseDTO {

    private Long id;
    private String name;
    private LocalDate birthday;
    private String imageName;
    private String status;
    private Integer age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
