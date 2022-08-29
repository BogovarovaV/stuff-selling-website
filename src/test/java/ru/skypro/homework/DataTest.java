package ru.skypro.homework;

import java.time.OffsetDateTime;

public interface DataTest {

    Integer USER_ID = 1;
    String FIRSTNAME = "USER";
    String LASTNAME = "USEROV";
    String EMAIL_1 = "uu@gmail.com";
    String EMAIL_2 = "us@gmail.com";
    String PHONE = "+12345678901";
    String USERNAME = EMAIL_1;
    String PASSWORD = "1234";

    Integer ADS_ID = 1;
    Integer PRICE_1 = 20_000;
    Integer PRICE_2 = 15_000;
    String TITLE = "title";
    String IMAGE = "image";
    String DESC = "description";

    Integer COMMENT_ID_1 = 1;
    Integer COMMENT_ID_2 = 2;
    OffsetDateTime DATE_TIME_1 = OffsetDateTime.MIN;
    OffsetDateTime DATE_TIME_2 = OffsetDateTime.MAX;
    String TEXT_1 = "text";
    String TEXT_2 = "updated text";
}
