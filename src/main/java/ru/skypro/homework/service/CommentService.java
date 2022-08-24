package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;

public interface CommentService {

    AdsComment createComment(Integer adsId, AdsComment adsCommentDto);

    AdsComment getAdsComment(Integer adsId, Integer id);

    ResponseWrapperAdsComment getAdsAllComments(Integer adsId);

    void deleteAdsComment(Integer adsId, Integer id, String username, UserDetails userDetails);

    AdsComment updateAdsComment(Integer adsId, Integer id, AdsComment adsCommentDto, String username, UserDetails userDetails);

}
