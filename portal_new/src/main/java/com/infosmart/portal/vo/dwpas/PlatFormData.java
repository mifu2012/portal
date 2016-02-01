package com.infosmart.portal.vo.dwpas;



import java.io.Serializable;

public class PlatFormData implements Serializable {

    private String title;
    private String value;
    private String trend;
    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public PlatFormData() {
        super();
    }

    public PlatFormData(String title, String val, int trendParam) {
        super();
        this.title = title;
        this.value = val;
        if (trendParam == 1) {
            this.trend = "up";
        }
        if (trendParam == -1) {
            this.trend = "down";
        }
        if (trendParam == 0) {
            this.trend = "bal";
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
