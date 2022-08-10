package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query(value = "select c from Comment c where c.ads.id = ?1 and c.id = ?2", nativeQuery = true)
    Optional<Comment> findAdsComment(Integer adsId, Integer id);

    @Query(value = "select c from Comment c where c.ads.id = ?1 and c.id = ?2", nativeQuery = true)
    Optional<Comment> deleteAdsComment(Integer adsId, Integer id);

    List<Comment> findAllByAdsId(Integer adsId);

    List<Comment> findAllByAdsIdOrderByIdDesc(Integer adsId);
}
