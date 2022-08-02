package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentMapper commentMapper, CommentRepository commentRepository) {
        this.commentMapper = commentMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(AdsComment commentDTO) {
        return commentRepository.save(commentMapper.dtoToModel(commentDTO));
    }

    @Override
    public AdsComment findCommentByPk(Integer pk) {
        return commentMapper.modelToDto(commentRepository.findById(pk).orElse(null));
    }

    @Override
    public List<AdsComment> findAllComments() {
        return commentMapper.modelsToDTOs(commentRepository.findAll());
    }

    @Override
    public void deleteCommentByPk(Integer pk) {
        AdsComment commentDTO = commentMapper.modelToDto(commentRepository.findById(pk).orElse(null));
        if (commentDTO != null) {
            commentRepository.deleteById(commentDTO.getPk());
        }
    }
}
