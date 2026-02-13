package com.sps.player.controller;

import com.sps.player.dto.ApiResponse;
import com.sps.player.dto.PlayerRequestDTO;
import com.sps.player.dto.PlayerResponseDTO;
import com.sps.player.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for Player Management
 * Provides CRUD operations and additional player-related endpoints
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Player Management", description = "APIs for managing cricket players")
public class PlayerController {

    private final PlayerService playerService;

    /**
     * Get all players
     */
    @GetMapping
    @Operation(summary = "Get all players", description = "Retrieve all players from the database")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> getAllPlayers() {
        log.info("GET /api/players - Fetching all players");
        List<PlayerResponseDTO> players = playerService.getAllPlayers();
        return ResponseEntity.ok(ApiResponse.success("Players retrieved successfully", players));
    }

    /**
     * Get player by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get player by ID", description = "Retrieve a specific player by their ID")
    public ResponseEntity<ApiResponse<PlayerResponseDTO>> getPlayerById(@PathVariable Long id) {
        log.info("GET /api/players/{} - Fetching player by id", id);
        PlayerResponseDTO player = playerService.getPlayerById(id);
        return ResponseEntity.ok(ApiResponse.success("Player retrieved successfully", player));
    }

    /**
     * Create new player
     */
    @PostMapping
    @Operation(summary = "Create new player", description = "Add a new player to the database")
    public ResponseEntity<ApiResponse<PlayerResponseDTO>> createPlayer(
            @Valid @RequestBody PlayerRequestDTO requestDTO) {
        log.info("POST /api/players - Creating new player: {}", requestDTO.getName());
        PlayerResponseDTO player = playerService.createPlayer(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Player created successfully", player));
    }

    /**
     * Update existing player
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update player", description = "Update an existing player's information")
    public ResponseEntity<ApiResponse<PlayerResponseDTO>> updatePlayer(
            @PathVariable Long id,
            @Valid @RequestBody PlayerRequestDTO requestDTO) {
        log.info("PUT /api/players/{} - Updating player", id);
        PlayerResponseDTO player = playerService.updatePlayer(id, requestDTO);
        return ResponseEntity.ok(ApiResponse.success("Player updated successfully", player));
    }

    /**
     * Delete player
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete player", description = "Remove a player from the database")
    public ResponseEntity<ApiResponse<Void>> deletePlayer(@PathVariable Long id) {
        log.info("DELETE /api/players/{} - Deleting player", id);
        playerService.deletePlayer(id);
        return ResponseEntity.ok(ApiResponse.success("Player deleted successfully", null));
    }

    /**
     * Get players by status
     */
    @GetMapping("/status/{status}")
    @Operation(summary = "Get players by status", description = "Retrieve players by their status (ACTIVE/INACTIVE)")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> getPlayersByStatus(
            @PathVariable String status) {
        log.info("GET /api/players/status/{} - Fetching players by status", status);
        List<PlayerResponseDTO> players = playerService.getPlayersByStatus(status.toUpperCase());
        return ResponseEntity.ok(ApiResponse.success("Players retrieved successfully", players));
    }

    /**
     * Search players by name
     */
    @GetMapping("/search")
    @Operation(summary = "Search players", description = "Search players by name (partial match)")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> searchPlayersByName(
            @RequestParam String name) {
        log.info("GET /api/players/search?name={} - Searching players", name);
        List<PlayerResponseDTO> players = playerService.searchPlayersByName(name);
        return ResponseEntity.ok(ApiResponse.success("Search completed successfully", players));
    }

    /**
     * Get players by age range
     */
    @GetMapping("/age-range")
    @Operation(summary = "Get players by age range", description = "Retrieve players within a specific age range")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> getPlayersByAgeRange(
            @RequestParam int minAge,
            @RequestParam int maxAge) {
        log.info("GET /api/players/age-range?minAge={}&maxAge={}", minAge, maxAge);
        List<PlayerResponseDTO> players = playerService.getPlayersByAgeRange(minAge, maxAge);
        return ResponseEntity.ok(ApiResponse.success("Players retrieved successfully", players));
    }

    /**
     * Get players by birthday range
     */
    @GetMapping("/birthday-range")
    @Operation(summary = "Get players by birthday range", description = "Retrieve players born within a date range")
    public ResponseEntity<ApiResponse<List<PlayerResponseDTO>>> getPlayersByBirthdayRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("GET /api/players/birthday-range?startDate={}&endDate={}", startDate, endDate);
        List<PlayerResponseDTO> players = playerService.getPlayersByBirthdayRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success("Players retrieved successfully", players));
    }

    /**
     * Get active players count
     */
    @GetMapping("/count/active")
    @Operation(summary = "Get active players count", description = "Get the total number of active players")
    public ResponseEntity<ApiResponse<Long>> getActivePlayersCount() {
        log.info("GET /api/players/count/active - Getting active players count");
        long count = playerService.getActivePlayersCount();
        return ResponseEntity.ok(ApiResponse.success("Active players count retrieved", count));
    }

    /**
     * Get inactive players count
     */
    @GetMapping("/count/inactive")
    @Operation(summary = "Get inactive players count", description = "Get the total number of inactive players")
    public ResponseEntity<ApiResponse<Long>> getInactivePlayersCount() {
        log.info("GET /api/players/count/inactive - Getting inactive players count");
        long count = playerService.getInactivePlayersCount();
        return ResponseEntity.ok(ApiResponse.success("Inactive players count retrieved", count));
    }

    /**
     * Get total players count
     */
    @GetMapping("/count/total")
    @Operation(summary = "Get total players count", description = "Get the total number of all players")
    public ResponseEntity<ApiResponse<Long>> getTotalPlayersCount() {
        log.info("GET /api/players/count/total - Getting total players count");
        long activeCount = playerService.getActivePlayersCount();
        long inactiveCount = playerService.getInactivePlayersCount();
        long totalCount = activeCount + inactiveCount;
        return ResponseEntity.ok(ApiResponse.success("Total players count retrieved", totalCount));
    }
}
