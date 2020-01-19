package com.huitong.learn.dao;

import com.huitong.learn.entity.TrainLine;

import java.util.List;

public interface TrainLineDAO {
    List<TrainLine> getTrainLines(String startPosition, String destination);
}
