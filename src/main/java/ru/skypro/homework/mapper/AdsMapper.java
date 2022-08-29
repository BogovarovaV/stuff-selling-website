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

    @Mapping(target = "image", ignore = true)
    Advert createAdsDtoToAdvertEntity(CreateAds createAdsDto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    Ads advertEntityToAdsDto(Advert advert);

    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "id", target = "pk")
    FullAds advertEntityToFullAdsDto(Advert advert);

    List<Ads> advertEntitiesToAdsDtos(List<Advert> advertList);

}
