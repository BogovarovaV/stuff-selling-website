package ru.skypro.homework;

import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;

public interface DataTest {

    Integer USER_ID = 1;
    String FIRSTNAME = "USER";
    String LASTNAME = "USEROV";
    String EMAIL_1 = "uu@gmail.com";
    String EMAIL_2 = "us@gmail.com";
    String PHONE = "+79175678901";
    String PHONE_2 = "+79175678905";
    String USERNAME = EMAIL_1;
    String PASSWORD = "12345678";

    Integer ADS_ID = 1;
    Integer PRICE_1 = 20000;
    Integer PRICE_2 = 15000;
    String TITLE = "title";
    String IMAGE = "/api/1/image";
    String DESC = "description";

    Integer COUNT = 1;

    Integer COMMENT_ID_1 = 1;
    Integer COMMENT_ID_2 = 2;
    OffsetDateTime DATE_TIME_1 = OffsetDateTime.now();
    OffsetDateTime DATE_TIME_2 = OffsetDateTime.MAX;
    String TEXT_1 = "texttext";
    String TEXT_2 = "updated_text";

    Integer ADS_AVATAR_ID = 1;
    byte[] ADS_AVATAR_IMAGE = "f6d73k9r".getBytes(StandardCharsets.UTF_8);


}
