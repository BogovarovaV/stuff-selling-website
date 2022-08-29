package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    AdsComment commentEntityToAdsCommentDto(Comment comment);

    @Mapping(source = "pk", target = "id")
    Comment adsCommentDtoToCommentEntity(AdsComment adsCommentDto);

    List<AdsComment> commentEntitiesToAdsCommentDtos(List<Comment> commentList);
}
