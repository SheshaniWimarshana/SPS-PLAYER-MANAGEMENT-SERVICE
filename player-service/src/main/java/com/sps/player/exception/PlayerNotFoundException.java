package com.sps.player.exception;

/**
 * Exception thrown when a player is not found
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
public class PlayerNotFoundException extends RuntimeException {

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
