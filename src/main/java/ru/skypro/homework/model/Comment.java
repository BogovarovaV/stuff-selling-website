package ru.skypro.homework.model;

import lombok.Data;

@Data
public class Comment {
    private int author;
    private String createdAt;
    private int pk;
    private String text;
}