package com.visited_countries.server.Dao.impl;

import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.visited_countries.server.Dao.UserDao;
import com.visited_countries.server.Dto.UserDto;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcTemplate jdbcTemplateUpdate;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcTemplateUpdate = new JdbcTemplate(dataSource);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplateUpdate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    """
                                INSERT INTO users (`USERNAME`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `PASSWORD`, `MOBILE`)
                                VALUES (?, ?, ?, ?, ?, ?);
                            """,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userDto.getUsername());
            ps.setString(2, userDto.getFirstName());
            ps.setString(3, userDto.getLastName());
            ps.setString(4, userDto.getEmail());
            ps.setString(5, userDto.getPassword());
            ps.setString(6, userDto.getMobile());

            return ps;
        }, keyHolder);

        userDto.setId(keyHolder.getKey().intValue());

        return userDto;
    }

    @Override
    public UserDto getUserByID(int id) {
        List<UserDto> users = jdbcTemplateUpdate.query(
                "SELECT * FROM users WHERE ID = ?",
                (rs, rowNum) -> {
                    UserDto dto = new UserDto();
                    dto.setId(rs.getInt("ID"));
                    dto.setUsername(rs.getString("USERNAME"));
                    dto.setFirstName(rs.getString("FIRST_NAME"));
                    dto.setLastName(rs.getString("LAST_NAME"));
                    dto.setEmail(rs.getString("EMAIL"));
                    return dto;
                }, id);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        List<UserDto> users = jdbcTemplateUpdate.query(
                "SELECT * FROM users WHERE EMAIL = ?",
                (rs, rowNum) -> {
                    UserDto dto = new UserDto();
                    dto.setId(rs.getInt("ID"));
                    dto.setUsername(rs.getString("USERNAME"));
                    dto.setFirstName(rs.getString("FIRST_NAME"));
                    dto.setLastName(rs.getString("LAST_NAME"));
                    dto.setEmail(rs.getString("EMAIL"));
                    return dto;
                }, email);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public UserDto getUserByMobile(String mobile) {
        List<UserDto> users = jdbcTemplateUpdate.query(
                "SELECT * FROM users WHERE MOBILE = ?",
                (rs, rowNum) -> {
                    UserDto dto = new UserDto();
                    dto.setId(rs.getInt("ID"));
                    dto.setUsername(rs.getString("USERNAME"));
                    dto.setFirstName(rs.getString("FIRST_NAME"));
                    dto.setLastName(rs.getString("LAST_NAME"));
                    dto.setEmail(rs.getString("EMAIL"));
                    return dto;
                }, mobile);

        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public UserDto deleteUser(int id) {
        UserDto userDto = getUserByID(id);
        if (userDto != null) {
            jdbcTemplateUpdate.update(
                    "DELETE FROM users WHERE ID = ?",
                    id);
        }
        return userDto;
    }

}
