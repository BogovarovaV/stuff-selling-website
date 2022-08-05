package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Advert;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Integer> {
}
