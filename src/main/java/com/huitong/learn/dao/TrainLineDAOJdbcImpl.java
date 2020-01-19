package com.huitong.learn.dao;

import com.huitong.learn.entity.TrainLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value="tradeLineDAO")
public class TrainLineDAOJdbcImpl implements TrainLineDAO {
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public List<TrainLine> getTrainLines(String startPosition, String destination) {
        String sql = "SELECT * FROM TrainLine where startPositionName=:startPosition and destinationName =:destination";
        Map<String, Object> param = new HashMap<>();
        param.put("startPosition", startPosition);
        param.put("destination", destination);
        return namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(TrainLine.class));
    }
}
