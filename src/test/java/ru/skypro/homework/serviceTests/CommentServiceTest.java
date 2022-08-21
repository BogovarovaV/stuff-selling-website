//package ru.skypro.homework.serviceTests;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import ru.skypro.homework.dto.AdsComment;
//import ru.skypro.homework.dto.ResponseWrapperAdsComment;
//import ru.skypro.homework.exception.CommentNotFoundException;
//import ru.skypro.homework.mapper.CommentMapper;
//import ru.skypro.homework.model.Advert;
//import ru.skypro.homework.model.Comment;
//import ru.skypro.homework.model.Users;
//import ru.skypro.homework.repository.AdvertRepository;
//import ru.skypro.homework.repository.CommentRepository;
//import ru.skypro.homework.repository.UserRepository;
//import ru.skypro.homework.service.impl.CommentServiceImpl;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//import static ru.skypro.homework.DataTest.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CommentServiceTest {
//
//    private Comment comment1;
//    private Comment comment2;
//    private AdsComment adsComment1;
//    private AdsComment adsComment2;
//    private Users users;
//    private Advert advert;
//    private ResponseWrapperAdsComment responseWrapperAdsComment;
//
//    @Mock
//    private CommentRepository commentRepositoryMock;
//
//    @Mock
//    private AdvertRepository advertRepositoryMock;
//
//    @Mock
//    private UserRepository userRepositoryMock;
//
//    @Mock
//    private CommentMapper commentMapperMock;
//
//    @InjectMocks
//    private CommentServiceImpl out;
//
//    @BeforeEach
//    public void setUp() {
//        //entity
//        comment1 = new Comment();
//        comment1.setId(COMMENT_ID_1);
//        comment1.setCreatedAt(DATE_TIME_1);
//        comment1.setText(TEXT_1);
//        users = new Users();
//        users.setId(USER_ID);
//        comment1.setUsers(users);
//        advert = new Advert();
//        advert.setId(ADS_ID);
//        comment1.setAds(advert);
//
//        comment2 = new Comment();
//        comment2.setId(COMMENT_ID_2);
//        comment2.setCreatedAt(DATE_TIME_2);
//        comment2.setText(TEXT_2);
//        comment2.setUsers(users);
//        comment2.setAds(advert);
//
//        //dto
//        adsComment1 = new AdsComment();
//        adsComment1.setAuthor(USER_ID);
//        adsComment1.setCreatedAt(DATE_TIME_1);
//        adsComment1.setPk(COMMENT_ID_1);
//        adsComment1.setText(TEXT_1);
//
//        adsComment2 = new AdsComment();
//        adsComment2.setAuthor(USER_ID);
//        adsComment2.setCreatedAt(DATE_TIME_2);
//        adsComment2.setPk(COMMENT_ID_2);
//        adsComment2.setText(TEXT_2);
//
//        responseWrapperAdsComment = new ResponseWrapperAdsComment();
//        responseWrapperAdsComment.setCount(2);
//        responseWrapperAdsComment.setResults(Arrays.asList(adsComment1, adsComment2));
//
//        out = new CommentServiceImpl(commentRepositoryMock, advertRepositoryMock, userRepositoryMock, commentMapperMock);
//    }
//
//    @Test
//    public void testShouldCreateComment() {
//        when(commentMapperMock.adsCommentDtoToCommentEntity(any(AdsComment.class))).thenReturn(comment1);
//        when(userRepositoryMock.findById(USER_ID)).thenReturn(Optional.of(users));
//        when(advertRepositoryMock.findById(ADS_ID)).thenReturn(Optional.of(advert));
//        assertEquals(adsComment1, out.createComment(ADS_ID, adsComment1));
//        verify(commentRepositoryMock, times(1)).save(comment1);
//    }
//
//    @Test
//    public void testShouldThrowExceptionWhenCommentNotFoundInDeleteRequest() {
//        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID_1)).thenThrow(CommentNotFoundException.class);
//        assertThrows(CommentNotFoundException.class, () -> out.deleteAdsComment(ADS_ID, COMMENT_ID_1));
//    }
//
//    @Test
//    public void testShouldGetAllAdsCommentOrderedByIdDesc() {
//        when(commentRepositoryMock.findAllByAdsIdOrderByIdDesc(any())).thenReturn(Arrays.asList(comment1, comment2));
//        when(commentMapperMock.commentEntitiesToAdsCommentDtos(any(List.class))).thenReturn(Arrays.asList(adsComment1, adsComment2));
//        assertEquals(responseWrapperAdsComment, out.getAdsAllComments(ADS_ID));
//        assertEquals(adsComment2, out.getAdsAllComments(ADS_ID).getResults().get(1));
//    }
//
//    @Test
//    public void testShouldGetCommentById() {
//        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID_1)).thenReturn(Optional.ofNullable(comment1));
//        when(commentMapperMock.commentEntityToAdsCommentDto(any(Comment.class))).thenReturn(adsComment1);
//        assertEquals(adsComment1, out.getAdsComment(ADS_ID, COMMENT_ID_1));
//    }
//
//    @Test
//    public void testShouldThrowExceptionWhenCommentNotFoundInGetRequest() {
//        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID_1)).thenThrow(CommentNotFoundException.class);
//        assertThrows(CommentNotFoundException.class, () -> out.getAdsComment(ADS_ID, COMMENT_ID_1));
//    }
//
//    @Test
//    public void testShouldUpdateAdsComment() {
//        comment1.setText(TEXT_2);
//        adsComment1.setText(TEXT_2);
//        when(commentRepositoryMock.findAdsComment(ADS_ID, COMMENT_ID_1)).thenReturn(Optional.of(comment1));
//        assertEquals(adsComment1, out.updateAdsComment(ADS_ID, COMMENT_ID_1, adsComment1));
//    }
//}
//
