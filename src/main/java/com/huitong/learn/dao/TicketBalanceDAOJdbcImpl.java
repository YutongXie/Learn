package com.huitong.learn.dao;

import com.huitong.learn.entity.TicketBalance;
import com.huitong.learn.entity.TicketBalanceCoachDetail;
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
import java.util.ArrayList;
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
    public List<TicketBalanceDetail> getTicketBalanceDetail(int ticketBalanceId) {
        String sql = "SELECT * FROM TicketBalanceDetail where ticketBalanceId = :ticketBalanceId";
        Map<String, Object> param = new HashMap<>();
        param.put("ticketBalanceId", ticketBalanceId);
        return namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(TicketBalanceDetail.class));
    }

    @Override
    public List<TicketBalanceCoachDetail> getTicketBalanceCoachDetail(int ticketBalanceId) {
        String sql = "SELECT tbcd.* FROM TicketBalanceDetail tbd, TicketBalanceCoachDetail tbcd where tbd.id = tbcd.ticketBalanceDetailId and tbd.ticketBalanceId = :ticketBalanceId";
        Map<String, Object> param = new HashMap<>();
        param.put("ticketBalanceId", ticketBalanceId);
        return namedParameterJdbcTemplate.query(sql, param, new BeanPropertyRowMapper<>(TicketBalanceCoachDetail.class));
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

        String detailQuerySql = "SELECT * from TicketBalanceDetail where ticketBalanceId = :ticketBalanceId and seatType = :seatType";
        Map<String, Object> detailQueryParams = new HashMap<>();
        detailQueryParams.put("ticketBalanceId", id);
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
        String detailUpdateSql = "UPDATE TicketBalanceDetail set " + condition + "=" + condition +" - :count where id = :id";

        Map<String, Object> detailUpdateParams = new HashMap<>();
        detailUpdateParams.put("id", ticketBalanceDetailList.get(0).getId());
        detailUpdateParams.put("count", count);
        namedParameterJdbcTemplate.update(detailUpdateSql, detailUpdateParams);

        String coachDetailQuerySql = "SELECT * FROM TicketBalanceCoachDetail where coach = :coach and ticketBalanceDetailId = :ticketBalanceDetailId ";
        Map<String, Object> coachDetailQueryParams = new HashMap<>();
        coachDetailQueryParams.put("coach", coachNum);
        coachDetailQueryParams.put("ticketBalanceDetailId", ticketBalanceDetailList.get(0).getId());
        List<TicketBalanceCoachDetail> ticketBalanceCoachDetailList = namedParameterJdbcTemplate.query(coachDetailQuerySql, coachDetailQueryParams, new BeanPropertyRowMapper<>(TicketBalanceCoachDetail.class));
        String coachDetailCondition = "";
        int rowNum = 0;
        if(ticketBalanceCoachDetailList.get(0).getRow1() == 0) {
            coachDetailCondition = "row1";
            rowNum = 1;
        } else if (ticketBalanceCoachDetailList.get(0).getRow2() == 0) {
            coachDetailCondition = "row2";
            rowNum = 2;
        } else if (ticketBalanceCoachDetailList.get(0).getRow3() == 0) {
            coachDetailCondition = "row3";
            rowNum = 3;
        } else if (ticketBalanceCoachDetailList.get(0).getRow4() == 0) {
            coachDetailCondition = "row4";
            rowNum = 4;
        } else if (ticketBalanceCoachDetailList.get(0).getRow5() == 0) {
            coachDetailCondition = "row5";
            rowNum = 5;
        } else if (ticketBalanceCoachDetailList.get(0).getRow6() == 0) {
            coachDetailCondition = "row6";
            rowNum = 6;
        } else if (ticketBalanceCoachDetailList.get(0).getRow7() == 0) {
            coachDetailCondition = "row7";
            rowNum = 7;
        } else if (ticketBalanceCoachDetailList.get(0).getRow8() == 0) {
            coachDetailCondition = "row8";
            rowNum = 8;
        } else if (ticketBalanceCoachDetailList.get(0).getRow9() == 0) {
            coachDetailCondition = "row9";
            rowNum = 9;
        } else if (ticketBalanceCoachDetailList.get(0).getRow10() == 0) {
            coachDetailCondition = "row10";
            rowNum = 10;
        }

        String coachDetailUpdateSql = "UPDATE TicketBalanceCoachDetail set " + coachDetailCondition + " = 1 where coach = :coach and ticketBalanceDetailId = :ticketBalanceDetailId";
        Map<String, Object> coachDetailUpdateParams = new HashMap<>();
        coachDetailUpdateParams.put("coach", coachNum);
        coachDetailUpdateParams.put("ticketBalanceDetailId", ticketBalanceDetailList.get(0).getId());
        namedParameterJdbcTemplate.update(coachDetailUpdateSql, coachDetailUpdateParams);

        return coachNum + "_" + rowNum + type;
    }

    @Override
    public List<TicketBalance> getAllActiveTicketBalance(int range) {
        String sql = "Select tb.id as 'TicketBalanceId', tbd.id as 'TicketBalanceDetailId', tbcd.id as 'TicketBalanceCoachDetailId', tb.*, tbd.*, tbcd.* from TicketBalance tb, TicketBalanceDetail tbd, TicketBalanceCoachDetail tbcd ， TrainLine tl "
                + "where tb.id = tbd.ticketBalanceId and tbd.id = tbcd.ticketBalanceDetailId and tl.lineName = tb.lineName"
                + " and day = :day";
        Map<String, Object> params = new HashMap<>();
        params.put("day", range);

        return namedParameterJdbcTemplate.query(sql, params, new RowMapper<TicketBalance>() {
            @Override
            public TicketBalance mapRow(ResultSet resultSet, int i) throws SQLException {
                TicketBalance ticketBalance = new TicketBalance();
                ticketBalance.setId(resultSet.getInt("TicketBalanceId"));
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

                TicketBalanceDetail ticketBalanceDetail = new TicketBalanceDetail();
                ticketBalanceDetail.setId(resultSet.getInt("TicketBalanceDetailId"));
                ticketBalanceDetail.setTicketBalanceId(resultSet.getInt("TicketBalanceId"));
                ticketBalanceDetail.setSeatType(resultSet.getString("seatType"));
                ticketBalanceDetail.setCoach1(resultSet.getInt("coach1"));
                ticketBalanceDetail.setCoach2(resultSet.getInt("coach2"));
                ticketBalanceDetail.setCoach3(resultSet.getInt("coach3"));
                ticketBalanceDetail.setCoach4(resultSet.getInt("coach4"));
                ticketBalanceDetail.setCoach5(resultSet.getInt("coach5"));
                ticketBalanceDetail.setCoach6(resultSet.getInt("coach6"));
                ticketBalanceDetail.setCoach7(resultSet.getInt("coach7"));
                ticketBalanceDetail.setCoach8(resultSet.getInt("coach8"));
                ticketBalanceDetail.setCoach9(resultSet.getInt("coach9"));
                ticketBalanceDetail.setCoach10(resultSet.getInt("coach10"));
                List<TicketBalanceDetail> ticketBalanceDetailList = new ArrayList<>();
                ticketBalanceDetailList.add(ticketBalanceDetail);

                TicketBalanceCoachDetail ticketBalanceCoachDetail = new TicketBalanceCoachDetail();
                ticketBalanceCoachDetail.setId(resultSet.getInt("TicketBalanceCoachDetailId"));
                ticketBalanceCoachDetail.setTicketBalanceDetailId(resultSet.getInt("TicketBalanceDetailId"));
                ticketBalanceCoachDetail.setCoach(resultSet.getInt("coach"));
                ticketBalanceCoachDetail.setRow1(resultSet.getInt("row1"));
                ticketBalanceCoachDetail.setRow2(resultSet.getInt("row2"));
                ticketBalanceCoachDetail.setRow3(resultSet.getInt("row3"));
                ticketBalanceCoachDetail.setRow4(resultSet.getInt("row4"));
                ticketBalanceCoachDetail.setRow5(resultSet.getInt("row5"));
                ticketBalanceCoachDetail.setRow6(resultSet.getInt("row6"));
                ticketBalanceCoachDetail.setRow7(resultSet.getInt("row7"));
                ticketBalanceCoachDetail.setRow8(resultSet.getInt("row8"));
                ticketBalanceCoachDetail.setRow9(resultSet.getInt("row9"));
                ticketBalanceCoachDetail.setRow10(resultSet.getInt("row10"));

                List<TicketBalanceCoachDetail> ticketBalanceCoachDetailList = new ArrayList<>();
                ticketBalanceCoachDetailList.add(ticketBalanceCoachDetail);
                ticketBalanceDetail.setTicketBalanceCoachDetailList(ticketBalanceCoachDetailList);
                return ticketBalance;
            }
        });
    }
}
