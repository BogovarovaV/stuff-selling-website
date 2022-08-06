package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AdvertRepository advertRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository, AdvertRepository advertRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.advertRepository = advertRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AdsComment createComment(Integer adsId, AdsComment adsCommentDto) {
        Comment createdComment = convertAdsCommentDtoToCommentEntity(adsCommentDto);
        createdComment.setUsers(userRepository.findById(adsCommentDto.getAuthor()).get());
        createdComment.setAds(advertRepository.findById(adsId).get());
        commentRepository.save(createdComment);
        return adsCommentDto;
    }

    @Override
    public void deleteAdsComment(Integer adsId, Integer id) {
        if (isCommentExists(adsId, id)) {
            commentRepository.deleteAdsComment(adsId, id);
        }
    }

    @Override
    public ResponseWrapperAdsComment getAdsAllComments(Integer adsId) {
        return convertCommentsListEntityToResponseWrapperAdsCommentDto(commentRepository.findAllByAdsId(adsId));
    }

    @Override
    public AdsComment getAdsComment(Integer adsId, Integer id) {
        if (isCommentExists(adsId, id)) {
            return convertCommentEntityToAdsCommentDto(commentRepository.findAdsComment(adsId, id).get());
        }
        return null;
    }

    @Override
    public AdsComment updateAdsComment(Integer adsId, Integer id, AdsComment adsCommentDto) {
        if (isCommentExists(adsId, id)) {
            commentRepository.save(convertAdsCommentDtoToCommentEntity(adsCommentDto));
            return adsCommentDto;
        }
        return null;
    }

    @Override
    public AdsComment convertCommentEntityToAdsCommentDto(Comment comment) {
        AdsComment adsCommentDto = new AdsComment();
        adsCommentDto.setAuthor(comment.getUsers().getId());
        adsCommentDto.setCreatedAt(comment.getCreatedAt());
        adsCommentDto.setPk(comment.getId());
        adsCommentDto.setText(comment.getText());
        return adsCommentDto;
    }

    @Override
    public Comment convertAdsCommentDtoToCommentEntity(AdsComment adsCommentDto) {
        Comment comment = new Comment();
        comment.setId(adsCommentDto.getPk());
        comment.setCreatedAt(adsCommentDto.getCreatedAt());
        comment.setText(adsCommentDto.getText());
        return comment;
    }

    @Override
    public ResponseWrapperAdsComment convertCommentsListEntityToResponseWrapperAdsCommentDto(List<Comment> comments) {
        ResponseWrapperAdsComment responseWrapperAdsComment = new ResponseWrapperAdsComment();
        List<AdsComment> list = new ArrayList<>(comments.size());
        for (Comment comment : comments) {
            list.add(convertCommentEntityToAdsCommentDto(comment));
        }
        responseWrapperAdsComment.setCount(list.size());
        responseWrapperAdsComment.setResults(list);
        return responseWrapperAdsComment;
    }

    private boolean isCommentExists(Integer adsId, Integer id) {
        return commentRepository.findAdsComment(adsId, id) != null;
    }
}

