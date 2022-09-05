package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.AdsAvatar;

import java.util.Optional;

@Repository
public interface AdsAvatarRepository extends JpaRepository<AdsAvatar, Integer> {

    Optional<AdsAvatar> findById(Integer id);
}
