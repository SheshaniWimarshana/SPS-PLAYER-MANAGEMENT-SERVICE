package com.sps.player.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Request DTO for creating/updating Player
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerRequestDTO {

    @NotBlank(message = "Player name is required and cannot be blank")
    private String name;

    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;

    private String imageName;

    private String status = "ACTIVE";
}
