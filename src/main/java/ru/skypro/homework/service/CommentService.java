package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.model.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(AdsComment commentDTO);

    AdsComment findCommentByPk(Integer pk);

    List<AdsComment> findAllComments();

    void deleteCommentByPk(Integer pk);
}
