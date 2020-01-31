package com.huitong.learn.dao;

import com.huitong.learn.entity.TicketBalance;
import com.huitong.learn.entity.TicketBalanceDetail;
import com.huitong.learn.entity.TrainLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<TicketBalance> getTicketBalance(String startPosition, String destination, String day) {
        String sql = "SELECT tb.*, tl.* FROM TicketBalance tb, TrainLine tl where tb.lineName = tl.lineName " +
                "and tl.startPositionName = :startPosition and tl.destinationName = :destination and tb.day = :day";
        Map<String, Object> param = new HashMap<>();
        param.put("startPosition", startPosition);
        param.put("destination", destination);
        param.put("day", day);
        return namedParameterJdbcTemplate.query(sql, param, new RowMapper<TicketBalance>() {
            @Override
            public TicketBalance mapRow(ResultSet resultSet, int i) throws SQLException {
                TicketBalance ticketBalance = new TicketBalance();
                ticketBalance.setId(resultSet.getInt("id"));
                ticketBalance.setDay(resultSet.getString("day"));
                ticketBalance.setSeatABalance(resultSet.getInt("seatABalance"));
                ticketBalance.setSeatBBalance(resultSet.getInt("seatBBalance"));
                ticketBalance.setSeatCBalance(resultSet.getInt("seatCBalance"));
                ticketBalance.setSeatEBalance(resultSet.getInt("seatEBalance"));
                ticketBalance.setSeatFBalance(resultSet.getInt("seatFBalance"));
                TrainLine trainLine = new TrainLine();
                trainLine.setLineName(resultSet.getString("lineName"));
                trainLine.setPrice(resultSet.getDouble("price"));
                trainLine.setStartPositionName(resultSet.getString("startPositionName"));
                trainLine.setDestinationName(resultSet.getString("destinationName"));
                trainLine.setStartTime(resultSet.getString("startTime"));
                trainLine.setArriveTime(resultSet.getString("arriveTime"));
                ticketBalance.setTrainLine(trainLine);
                return ticketBalance;
            }
        });
    }

    @Override
    public List<TicketBalanceDetail> getTicketBalanceDetail(int id) {
        String sql = "SELECT * FROM TicketBalanceDetail where id = :id";
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        return namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(TicketBalanceDetail.class));
    }

    @Override
    public String updateTicketBalance(int id, String type, int count) {
        String seatBlance = "";
        switch (type) {
            case "A":
                seatBlance = "seatABalance";
                break;
            case "B":
                seatBlance = "seatBBalance";
                break;
            case "C":
                seatBlance = "seatCBalance";
                break;
            case "E":
                seatBlance = "seatEBalance";
                break;
            case "F":
                seatBlance = "seatFBalance";
                break;
            default:
                break;
        }
        String sql = "UPDATE TicketBalance set " + seatBlance + "=" + seatBlance +"- :count"
                + " where id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("count", count);
        namedParameterJdbcTemplate.update(sql, params);

        String detailQuerySql = "SELECT * from TicketBalanceDetail where id = :id and seatType = :seatType";
        Map<String, Object> detailQueryParams = new HashMap<>();
        detailQueryParams.put("id", id);
        detailQueryParams.put("seatType", type);
        List<TicketBalanceDetail> ticketBalanceDetailList = namedParameterJdbcTemplate.query(detailQuerySql, detailQueryParams, new BeanPropertyRowMapper<>(TicketBalanceDetail.class));

        String condition = "";
        int coachNum = 0;
        if(ticketBalanceDetailList.get(0).getCoach1() > 0) {
            condition = "coach1";
            coachNum = 1;
        } else if (ticketBalanceDetailList.get(0).getCoach2() > 0) {
            condition = "coach2";
            coachNum = 2;
        } else if (ticketBalanceDetailList.get(0).getCoach3() > 0) {
            condition = "coach3";
            coachNum = 3;
        } else if (ticketBalanceDetailList.get(0).getCoach4() > 0) {
            condition = "coach4";
            coachNum = 4;
        } else if (ticketBalanceDetailList.get(0).getCoach5() > 0) {
            condition = "coach5";
            coachNum = 5;
        } else if (ticketBalanceDetailList.get(0).getCoach6() > 0) {
            condition = "coach6";
            coachNum = 6;
        } else if (ticketBalanceDetailList.get(0).getCoach7() > 0) {
            condition = "coach7";
            coachNum = 7;
        } else if (ticketBalanceDetailList.get(0).getCoach8() > 0) {
            condition = "coach8";
            coachNum = 8;
        } else if (ticketBalanceDetailList.get(0).getCoach9() > 0) {
            condition = "coach9";
            coachNum = 9;
        } else if (ticketBalanceDetailList.get(0).getCoach10() > 0) {
            condition = "coach10";
            coachNum = 10;
        }
        String detailUpdateSql = "UPDATE TicketBalanceDetail set " + condition + "=" + condition +" - :count where id = :id and seatType = :seatType";

        Map<String, Object> detailUpdateParams = new HashMap<>();
        detailUpdateParams.put("id", id);
        detailUpdateParams.put("seatType", type);
        detailUpdateParams.put("count", count);
        namedParameterJdbcTemplate.update(detailUpdateSql, detailUpdateParams);
        return coachNum + "_10" + type;
    }
}
