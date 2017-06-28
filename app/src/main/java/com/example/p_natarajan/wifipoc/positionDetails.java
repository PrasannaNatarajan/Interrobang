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

    private String[] MAC_addresses;
    private Integer[] signalStrengths;

    private List<Map.Entry<String, Double>> list;

    public positionDetails(){

    }

    public List<Map.Entry<String, Double>> getList() {
        return list;
    }

    public positionDetails(double x, double y, String z, List<Map.Entry<String,Double>>list) {
        X = x;
        Y = y;
        Z = z;
        //this.MAC_addresses = MAC_addresses;
        //this.signalStrengths = signalStrengths;
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

    public String[] getMAC_addresses() {
        return MAC_addresses;
    }

    public Integer[] getSignalStrengths() {
        return signalStrengths;
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

    public void setMAC_addresses(String[] MAC_addresses) {
        this.MAC_addresses = MAC_addresses;
    }

    public void setSignalStrengths(Integer[] signalStrengths) {
        this.signalStrengths = signalStrengths;
    }
}
