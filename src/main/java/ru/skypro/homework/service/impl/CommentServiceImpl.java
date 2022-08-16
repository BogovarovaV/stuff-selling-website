package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;
import ru.skypro.homework.exception.AdvertNotFoundException;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Comment;
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

    @Override
    public AdsComment createComment(Integer adsId, AdsComment adsCommentDto) {
        Comment createdComment = commentMapper.adsCommentDtoToCommentEntity(adsCommentDto);
        createdComment.setUsers(userRepository.findById(adsCommentDto.getAuthor()).orElseThrow(UserNotFoundException::new));
        createdComment.setAds(advertRepository.findById(adsId).orElseThrow(AdvertNotFoundException::new));
        commentRepository.save(createdComment);
        return adsCommentDto;
    }

    @Override
    public void deleteAdsComment(Integer adsId, Integer id) {
        Comment comment = commentRepository.findAdsComment(adsId, id).orElseThrow(CommentNotFoundException::new);
        commentRepository.delete(comment);
        }

    @Override
    public ResponseWrapperAdsComment getAdsAllComments(Integer adsId) {
        List<AdsComment> adsCommentList = commentMapper.commentEntitiesToAdsCommentDtos(commentRepository.findAllByAdsIdOrderByIdDesc(adsId));
        ResponseWrapperAdsComment responseWrapperAdsComment = new ResponseWrapperAdsComment();
        responseWrapperAdsComment.setCount(adsCommentList.size());
        responseWrapperAdsComment.setResults(adsCommentList);
        return responseWrapperAdsComment;
    }

    @Override
    public AdsComment getAdsComment(Integer adsId, Integer id) {
        Comment comment = commentRepository.findAdsComment(adsId, id).orElseThrow(CommentNotFoundException::new);
        return commentMapper.commentEntityToAdsCommentDto(comment);
    }

    @Override
    public AdsComment updateAdsComment(Integer adsId, Integer id, AdsComment adsCommentDto) {
        Comment comment = commentRepository.findAdsComment(adsId, id).orElseThrow(CommentNotFoundException::new);
        comment.setCreatedAt(adsCommentDto.getCreatedAt());
        comment.setText(adsCommentDto.getText());
        commentRepository.save(comment);
        return adsCommentDto;
    }
}

