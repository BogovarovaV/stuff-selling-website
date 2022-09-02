package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAdsCommentTo {

    private Integer count;
    private List<AdsCommentTo> results;

}