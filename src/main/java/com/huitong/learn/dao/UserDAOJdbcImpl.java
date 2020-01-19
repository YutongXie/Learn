package com.huitong.learn.dao;

import com.huitong.learn.entity.User;
import com.huitong.learn.entity.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value="userDAO")
public class UserDAOJdbcImpl implements UserDAO{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(this.jdbcTemplate);
    }
    private int BATCH_SIZE = 100;

    @Override
    public boolean isUserExist(User user) {
        return false;
    }

    @Override
    public void saveUser(List<User> userList) {
        String sql = "INSERT INTO User(id, name, type) values(?, ?, ?)";
        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                User user = userList.get(i);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getUserType().name());
            }

            @Override
            public int getBatchSize() {
                return userList.size();
            }
        };
        jdbcTemplate.batchUpdate(sql, batchPreparedStatementSetter);
    }

    @Override
    public void saveUser(User user) {
        String sql = "INSERT INTO User(id, name, type) values(:id, :name, :type)";
        Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("name", user.getName());
        params.put("type", user.getUserType().name());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<User> getUserByName(String name) {
        String sql = "Select * from User where name = :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return namedParameterJdbcTemplate.query(sql, params, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setUserType(UserType.getUserTpye(resultSet.getString("type")));
                return user;
            }
        });
    }
}
