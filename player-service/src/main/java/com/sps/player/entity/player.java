package com.sps.player.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Player Entity class representing a cricket player
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Entity
@Table(name = "players", indexes = {
        @Index(name = "idx_status", columnList = "status"),
        @Index(name = "idx_name", columnList = "name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Player name is required")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday must be in the past")
    private LocalDate birthday;

    @Column(name = "image_name")
    private String imageName;

    @Column(length = 20)
    private String status = "ACTIVE";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Calculate player's age
     */
    @Transient
    public int getAge() {
        if (birthday == null) {
            return 0;
        }
        return Period.between(birthday, LocalDate.now()).getYears();
    }

    /**
     * Get formatted birthday
     */
    @Transient
    public String getFormattedBirthday() {
        if (birthday == null) {
            return "N/A";
        }
        return birthday.toString();
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) {
            status = "ACTIVE";
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
