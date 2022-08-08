package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;

public interface CommentService {

    AdsComment createComment(Integer adsId, AdsComment adsCommentDto);

    AdsComment getAdsComment(Integer adsId, Integer id);

    ResponseWrapperAdsComment getAdsAllComments(Integer adsId);

    void deleteAdsComment(Integer adsId, Integer id);

    AdsComment updateAdsComment(Integer adsPk, Integer pk, AdsComment adsCommentDto);

}
