package com.huitong.learn.entity;

public class TrainLine {
    private String startPositionName;
    private String destinationName;
    private String lineName;
    private Double price;
    private String startTime;
    private String arriveTime;

    public String getStartPositionName() {
        return startPositionName;
    }

    public void setStartPositionName(String startPositionName) {
        this.startPositionName = startPositionName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
}
