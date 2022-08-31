package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.AdsTo;
import ru.skypro.homework.dto.CreateAdsTo;
import ru.skypro.homework.dto.FullAdsTo;
import ru.skypro.homework.model.Advert;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    @Mapping(target = "image", ignore = true)
    Advert createAdsDtoToAdvertEntity(CreateAdsTo createAdsDto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    AdsTo advertEntityToAdsDto(Advert advert);

    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    @Mapping(source = "id", target = "pk")
    FullAdsTo advertEntityToFullAdsDto(Advert advert);

    List<AdsTo> advertEntitiesToAdsDtos(List<Advert> advertList);

}
