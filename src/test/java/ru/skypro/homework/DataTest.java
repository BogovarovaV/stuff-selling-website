package ru.skypro.homework;

import ru.skypro.homework.model.Users;

import java.time.OffsetDateTime;

public interface DataTest {

    Integer USER_ID = 1;
    Users.Roles ROLE = Users.Roles.USER;
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

    Integer COMMENT_ID = 1;
    OffsetDateTime DATE_TIME = OffsetDateTime.MIN;
    String TEXT_1 = "text";
    String TEXT_2 = "updated text";
}
