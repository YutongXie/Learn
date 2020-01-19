package com.huitong.learn.dao;

import com.huitong.learn.entity.TicketBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository(value="ticketBalanceDAO")
public class TicketBalanceDAOJdbcImpl implements TicketBalanceDAO{
    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
    @Override
    public List<TicketBalance> getTicketBalance(String startPosition, String destination) {
        String sql = "SELECT tb.* FROM TicketBalance tb, TrainLine tl where tb.lineName = tl.lineName " +
                "and tl.startPositionName = :startPosition and tl.destinationName = :destination";
        Map<String, Object> param = new HashMap<>();
        param.put("startPosition", startPosition);
        param.put("destination", destination);
        return namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(TicketBalance.class));
    }
}
