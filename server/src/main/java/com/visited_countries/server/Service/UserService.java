package com.visited_countries.server.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.visited_countries.server.Dao.UserDao;
import com.visited_countries.server.Dto.UserDto;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UserDao userDao;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    public UserDto createNewUser(UserDto userDto) {
        log.info(userDto.toString());

        // Check if user already exists
        Boolean userEmailExist = userDao.getUserByEmail(userDto.getEmail()) != null;
        if (userEmailExist) {
            throw new RuntimeException("This email address is already registered.");
        }

        Boolean userMobileExist = userDao.getUserByMobile(userDto.getMobile()) != null;
        if (userMobileExist) {
            throw new RuntimeException("This mobile number is already registered.");
        }

        Boolean userIdExist = userDao.getUserByID(userDto.getId()) != null;
        if (userIdExist) {
            throw new RuntimeException("User with ID " + userDto.getId() + " already exists.");
        }

        // hash password
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userDao.createUser(userDto);
    }

    public Boolean deleteUser(int id) {
        if (userDao.getUserByID(id) == null) {
            throw new RuntimeException("This user doesn't exist.");
        }

        return userDao.deleteUser(id);
    }

}
