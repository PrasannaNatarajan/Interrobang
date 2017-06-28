package com.example.p_natarajan.wifipoc;

import java.util.List;
import java.util.Map;

/**
 * Created by P_Natarajan on 6/27/2017.
 */

public class positionDetails {

    private double X;
    private double Y;
    private String Z;

    private List<Map.Entry<String, Double>> list;

    public positionDetails(){

    }

    public positionDetails(double x, double y, String z, List<Map.Entry<String,Double>>list) {
        X = x;
        Y = y;
        Z = z;
        this.list = list;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public String getZ() {
        return Z;
    }
    public List<Map.Entry<String, Double>> getList() {
        return list;
    }

    public void setX(double x) {
        X = x;
    }

    public void setY(double y) {
        Y = y;
    }

    public void setZ(String z) {
        Z = z;
    }

    public void setList(List<Map.Entry<String, Double>> list) {
        this.list = list;
    }
}
