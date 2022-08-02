package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "comment.id", target = "pk")
    @Mapping(source = "comment.users.id", target = "author")
    @Mapping(source = "comment.ads", target = "ads")
    AdsComment modelToDto(Comment comment);

    @Mapping(source = "commentDTO.pk", target = "id")
    @Mapping(source = "commentDTO.author", target = "users.id")
    Comment dtoToModel(AdsComment commentDTO);

    List<AdsComment> modelsToDTOs(List<Comment> comments);
}
