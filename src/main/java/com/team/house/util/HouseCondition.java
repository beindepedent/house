package com.team.house.util;

public class HouseCondition extends Page {
    //添加房屋条件查询
    private String title;
    private Integer districtid;//区域编号
    private Integer streetid;//街道编号
    private Integer typeid;//类型编号
    private Integer startPrice;//起始价格
    private Integer endPrice;//结束价格
    private Integer startArea;//起始面积
    private Integer endArea;//结束面积

    @Override
    public String toString() {
        return "HouseCondition{" +
                "title='" + title + '\'' +
                ", districtid=" + districtid +
                ", streetid=" + streetid +
                ", typeid=" + typeid +
                ", startPrice=" + startPrice +
                ", endPrice=" + endPrice +
                ", startArea=" + startArea +
                ", endArea=" + endArea +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDistrictid() {
        return districtid;
    }

    public void setDistrictid(Integer districtid) {
        this.districtid = districtid;
    }

    public Integer getStreetid() {
        return streetid;
    }

    public void setStreetid(Integer streetid) {
        this.streetid = streetid;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Integer getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Integer startPrice) {
        this.startPrice = startPrice;
    }

    public Integer getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Integer endPrice) {
        this.endPrice = endPrice;
    }

    public Integer getStartArea() {
        return startArea;
    }

    public void setStartArea(Integer startArea) {
        this.startArea = startArea;
    }

    public Integer getEndArea() {
        return endArea;
    }

    public void setEndArea(Integer endArea) {
        this.endArea = endArea;
    }
}
