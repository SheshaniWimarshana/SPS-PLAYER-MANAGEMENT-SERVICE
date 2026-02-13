package com.sps.player.repository;

import com.sps.player.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Player entity
 * Provides database operations for Player management
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Find players by status
     * @param status Player status (ACTIVE/INACTIVE)
     * @return List of players with specified status
     */
    List<Player> findByStatus(String status);

    /**
     * Find players by name containing (case-insensitive search)
     * @param name Search term
     * @return List of players matching the search
     */
    List<Player> findByNameContainingIgnoreCase(String name);

    /**
     * Find player by exact name (case-insensitive)
     * @param name Player name
     * @return Optional Player
     */
    Optional<Player> findByNameIgnoreCase(String name);

    /**
     * Find players born after a specific date
     * @param date Birth date threshold
     * @return List of players born after the date
     */
    List<Player> findByBirthdayAfter(LocalDate date);

    /**
     * Find players born before a specific date
     * @param date Birth date threshold
     * @return List of players born before the date
     */
    List<Player> findByBirthdayBefore(LocalDate date);

    /**
     * Find players born between two dates
     * @param startDate Start date
     * @param endDate End date
     * @return List of players born within the date range
     */
    List<Player> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Count active players
     * @return Number of active players
     */
    long countByStatus(String status);

    /**
     * Check if player exists by name
     * @param name Player name
     * @return true if exists, false otherwise
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Custom query to find players by age range
     * @param minAge Minimum age
     * @param maxAge Maximum age
     * @return List of players within age range
     */
    @Query("SELECT p FROM Player p WHERE " +
            "FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', p.birthday) " +
            "BETWEEN :minAge AND :maxAge")
    List<Player> findPlayersByAgeRange(@Param("minAge") int minAge,
                                       @Param("maxAge") int maxAge);

    /**
     * Find all active players ordered by name
     * @return List of active players sorted by name
     */
    List<Player> findByStatusOrderByNameAsc(String status);
}
