package com.huitong.learn.dao;

import com.huitong.learn.entity.TicketRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository(value = "ticketRecordDAO")
public class TicketRecordDAOJdbcImpl implements TicketRecordDAO{

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public boolean saveNewTickets(List<TicketRecord> ticketRecordList) {
        String sql = "Insert into TicketRecord(requestId, lineName, buyer, passenger, startPosition, destination, seatNum, coachNum, createTime, status)" +
                " values (?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                TicketRecord ticketRecord = ticketRecordList.get(i);
                preparedStatement.setString(1, ticketRecord.getRequestId());
                preparedStatement.setString(2, ticketRecord.getLineName());
                preparedStatement.setString(3, ticketRecord.getBuyer());
                preparedStatement.setString(4, ticketRecord.getPassenger());
                preparedStatement.setString(5, ticketRecord.getStartPosition());
                preparedStatement.setString(6, ticketRecord.getDestination());
                preparedStatement.setString(7, ticketRecord.getSeatNum());
                preparedStatement.setInt(8, ticketRecord.getCoachNum());
                preparedStatement.setTimestamp(9, new java.sql.Timestamp(ticketRecord.getCreateTime().getTime()));
                preparedStatement.setString(10, ticketRecord.getStatus());
            }

            @Override
            public int getBatchSize() {
                return ticketRecordList.size();
            }
        });

        return true;
    }

    @Override
    public List<TicketRecord> queryTicketRecords(String buyerName) {
        String sql = "Select * from TicketRecord where buyer = :buyer";
        Map<String, Object> params = new HashMap<>();
        params.put("buyer", buyerName);

        return namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(TicketRecord.class));
    }
}
