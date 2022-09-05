package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.AdsCommentTo;
import ru.skypro.homework.dto.ResponseWrapperAdsCommentTo;

public interface CommentService {

    AdsCommentTo createComment(Integer adsId, AdsCommentTo adsCommentDto, Authentication authentication);

    AdsCommentTo getAdsComment(Integer adsId, Integer id);

    ResponseWrapperAdsCommentTo getAdsAllComments(Integer adsId);

    void deleteAdsComment(Integer adsId, Integer id, String username, UserDetails userDetails);

    AdsCommentTo updateAdsComment(Integer adsId, Integer id, AdsCommentTo adsCommentDto, String username, UserDetails userDetails);

}
