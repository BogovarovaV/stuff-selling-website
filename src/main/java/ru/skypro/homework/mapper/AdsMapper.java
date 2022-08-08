package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.model.Advert;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(source = "pk", target = "id")
    Advert createAdsDtoToAdvertEntity(CreateAds createAdsDto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "users.id", target = "author")
    Ads advertEntityToAdsDto(Advert advert);

    @Mapping(source = "users.firstName", target = "authorFirstName")
    @Mapping(source = "users.lastName", target = "authorLastName")
    @Mapping(source = "users.email", target = "email")
    @Mapping(source = "users.phone", target = "phone")
    @Mapping(source = "id", target = "pk")
    FullAds advertEntityToFullAdsDto(Advert advert);

    List<Ads> advertEntitiesToAdsDtos(List<Advert> advertList);

}
