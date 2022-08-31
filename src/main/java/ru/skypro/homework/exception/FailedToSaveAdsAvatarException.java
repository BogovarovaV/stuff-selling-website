package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FailedToSaveAdsAvatarException extends RuntimeException {

    public FailedToSaveAdsAvatarException(IOException e) {
        super("Failed to save advert avatar");
    }
}
