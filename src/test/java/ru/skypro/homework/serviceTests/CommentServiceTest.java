package ru.skypro.homework.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.AdsComment;
import ru.skypro.homework.dto.ResponseWrapperAdsComment;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Advert;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdvertRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.impl.CommentServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ru.skypro.homework.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    private Comment comment;
    private AdsComment adsComment;
    private Users users;
    private Advert advert;
    private ResponseWrapperAdsComment responseWrapperAdsComment;

    @Mock
    private CommentRepository commentRepositoryMock;

    @Mock
    private AdvertRepository advertRepositoryMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private CommentMapper commentMapperMock;

    @InjectMocks
    private CommentServiceImpl out;

    @BeforeEach
    public void setUp() {
        //entity
        comment = new Comment();
        comment.setId(COMMENT_ID);
        comment.setCreatedAt(DATE_TIME);
        comment.setText(TEXT_1);
        users = new Users();
        users.setId(USER_ID);
        comment.setUsers(users);
        advert = new Advert();
        advert.setId(ADS_ID);
        comment.setAds(advert);

        //dto
        adsComment = new AdsComment();
        adsComment.setAuthor(USER_ID);
        adsComment.setCreatedAt(DATE_TIME);
        adsComment.setPk(COMMENT_ID);
        adsComment.setText(TEXT_1);

        responseWrapperAdsComment = new ResponseWrapperAdsComment();
        responseWrapperAdsComment.setCount(1);
        responseWrapperAdsComment.setResults(Arrays.asList(adsComment));

        out = new CommentServiceImpl(commentRepositoryMock, advertRepositoryMock, userRepositoryMock, commentMapperMock);
    }

    @Test
    public void testShouldCreateComment() {
        when(commentMapperMock.adsCommentDtoToCommentEntity(any(AdsComment.class))).thenReturn(comment);
        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(users));
        when(advertRepositoryMock.findById(ADS_ID)).thenReturn(Optional.of(advert));
        assertEquals(adsComment, out.createComment(ADS_ID, adsComment));
        verify(commentRepositoryMock, times(1)).save(comment);
    }

    @Test
    public void testShouldThrowExceptionWhenCommentNotFoundInDeleteRequest() {
        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID)).thenThrow(CommentNotFoundException.class);
        assertThrows(CommentNotFoundException.class, () -> out.deleteAdsComment(ADS_ID, COMMENT_ID));
    }

    @Test
    public void testShouldGetAllAdsComment() {
        when(commentRepositoryMock.findAllByAdsId(any())).thenReturn(Arrays.asList(comment));
        when(commentMapperMock.commentEntitiesToAdsCommentDtos(any(List.class))).thenReturn(Arrays.asList(adsComment));
        assertEquals(responseWrapperAdsComment, out.getAdsAllComments(ADS_ID));
    }

    @Test
    public void testShouldGetCommentById() {
        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID)).thenReturn(Optional.ofNullable(comment));
        when(commentMapperMock.commentEntityToAdsCommentDto(any(Comment.class))).thenReturn(adsComment);
        assertEquals(adsComment, out.getAdsComment(ADS_ID, COMMENT_ID));
    }

    @Test
    public void testShouldThrowExceptionWhenCommentNotFoundInGetRequest() {
        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID)).thenThrow(CommentNotFoundException.class);
        assertThrows(CommentNotFoundException.class, () -> out.getAdsComment(ADS_ID, COMMENT_ID));
    }

    @Test
    public void testShouldUpdateAdsComment() {
        comment.setText(TEXT_2);
        adsComment.setText(TEXT_2);
        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID)).thenReturn(Optional.of(comment));
        assertEquals(adsComment, out.updateAdsComment(ADS_ID, COMMENT_ID, adsComment));
    }
}

