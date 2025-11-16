package com.visited_countries.server.Dao;

import com.visited_countries.server.Dto.UserDto;

public interface UserDao {
    UserDto createUser(UserDto userDto);

    UserDto getUserByID(int id);
    UserDto getUserByEmail(String email);
    UserDto getUserByMobile(String mobile);

    UserDto deleteUser(int id);
}
