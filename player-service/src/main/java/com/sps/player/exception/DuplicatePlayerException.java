package com.sps.player.exception;

/**
 * Exception thrown when attempting to create a duplicate player
 *
 * @author SPS Cricket Club
 * @version 1.0.0
 */
public class DuplicatePlayerException extends RuntimeException {

    public DuplicatePlayerException(String message) {
        super(message);
    }

    public DuplicatePlayerException(String message, Throwable cause) {
        super(message, cause);
    }
}
