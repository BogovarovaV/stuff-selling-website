package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegisterReqTo;
import ru.skypro.homework.dto.RoleTo;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterReqTo registerReq, RoleTo role);

    void changePassword(String username, String currentPassword, String newPassword);
}
