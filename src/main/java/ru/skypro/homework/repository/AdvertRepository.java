package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Advert;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Integer> {

    List<Advert> findAllByTitleContainsIgnoreCase(String search);

    List<Advert> findAllByUserId(int id);

}
