package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.dto.AdsCommentTo;
import ru.skypro.homework.dto.ResponseWrapperAdsCommentTo;
import ru.skypro.homework.exception.AdvertNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.NoAccessException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, AdvertRepository advertRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    /**
     * Create comment by advert ID
     * @param adsId - advert ID from client
     * @param adsCommentDto - advert information from client
     * @return created comment as AdsCommentTo (DTO)
     */
    @Override
    public AdsCommentTo createComment(Integer adsId, AdsCommentTo adsCommentDto) {
        Comment createdComment = commentMapper.adsCommentDtoToCommentEntity(adsCommentDto);
        createdComment.setUser(userRepository.findById(adsCommentDto.getAuthor()).orElseThrow(UserNotFoundException::new));
        createdComment.setAds(advertRepository.findById(adsId).orElseThrow(AdvertNotFoundException::new));
        commentRepository.save(createdComment);
        return adsCommentDto;
    }

    /**
     * Delete comment by advert ID and comment ID
     * @param adsId - advert ID from client
     * @param id - comment ID from client
     * @param username username from client
     * @param userDetails - user details from client
     */
    @Transactional
    @Override
    public void deleteAdsComment(Integer adsId, Integer id, String username, UserDetails userDetails) {
        Comment comment = commentRepository.findCommentById(id).orElseThrow(CommentNotFoundException::new);
        User user = userRepository.getById(comment.getUser().getId());
        if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN")
                || username.equals(user.getUsername())) {
            commentRepository.delete(comment);
        } else {
            throw new NoAccessException();
        }
    }

    /**
     * Get all comments of a specific advert by advert ID
     * @param adsId - advert ID from client
     * @return list of all comments of a specific advert
     * as ResponseWrapperAdsCommentTo (DTO)
     */
    @Transactional
    @Override
    public ResponseWrapperAdsCommentTo getAdsAllComments(Integer adsId) {
        List<AdsCommentTo> adsCommentList = commentMapper.commentEntitiesToAdsCommentDtos(commentRepository.findAllByAdsIdOrderByIdDesc(adsId));
        ResponseWrapperAdsCommentTo responseWrapperAdsCommentTo = new ResponseWrapperAdsCommentTo();
        responseWrapperAdsCommentTo.setCount(adsCommentList.size());
        responseWrapperAdsCommentTo.setResults(adsCommentList);
        return responseWrapperAdsCommentTo;
    }

    /**
     * Get comment of an advert by advert ID and comment ID
     * @param adsId - advert ID from client
     * @param id - comment ID from client
     * @return found comment as AdsCommentTo (DTO)
     */
    @Transactional
    @Override
    public AdsCommentTo getAdsComment(Integer adsId, Integer id) {
        Comment comment = commentRepository.findCommentById(id).orElseThrow(CommentNotFoundException::new);
        return commentMapper.commentEntityToAdsCommentDto(comment);
    }

    /**
     * Update advert comment by advert ID and comment ID
     * @param adsId - advert ID from client
     * @param id - comment ID from client
     * @param adsCommentDto - comment information from client
     * @param username - username from client
     * @param userDetails - user details from client
     * @return updated comment as AdsCommentTo (DTO) or throw exception
     */
    @Transactional
    @Override
    public AdsCommentTo updateAdsComment(Integer adsId, Integer id, AdsCommentTo adsCommentDto, String username, UserDetails userDetails) {
        Comment comment = commentRepository.findCommentById(id).orElseThrow(CommentNotFoundException::new);
        User user = userRepository.getById(comment.getUser().getId());
        if (userDetails.getAuthorities().toString().contains("ROLE_ADMIN")
                || username.equals(user.getUsername())) {
            comment.setCreatedAt(adsCommentDto.getCreatedAt());
            comment.setText(adsCommentDto.getText());
            commentRepository.save(comment);
            return adsCommentDto;
        } else {
            throw new NoAccessException();
        }
    }
}

