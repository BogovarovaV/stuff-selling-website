package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Advert;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Integer> {

//    @Query(value = "select * from advert where lower(title) like lower(concat('%', ?1,'%'))", nativeQuery = true)
//    List<Advert> findAds(String search);

    List<Advert> findAllByTitleContainsIgnoreCase(String search);

//    @Query(value = "select * from advert where advert.users_id =?1", nativeQuery = true)
//    List<Advert> findAdsByUsersId(Integer author);

    List<Advert> findAllByUserId(int id);

//    Advert findByImage(String image);

}
