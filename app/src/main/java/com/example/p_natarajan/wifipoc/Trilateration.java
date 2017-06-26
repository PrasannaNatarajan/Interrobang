package com.example.p_natarajan.wifipoc;

import android.util.Log;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.Arrays;

/**
 * Created by Sahiti_Kunchay on 6/21/2017.
 */

public class Trilateration {
    double[][] mPositions;
    double[] mDistances;

    public Trilateration(double[][] pPositions, double[] pDistances) {
        this.mPositions = pPositions;
        this.mDistances = pDistances;
    }

    public double[][] getPositions() {
        return mPositions;
    }

    public void setPositions(double[][] pPositions) {
        this.mPositions = pPositions;
    }

    public double[] getDistances() {
        return mDistances;
    }

    public void setDistances(double[] pDistances) {
        this.mDistances = pDistances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trilateration)) return false;

        Trilateration that = (Trilateration) o;

        if (!Arrays.deepEquals(mPositions, that.mPositions)) return false;
        return Arrays.equals(mDistances, that.mDistances);

    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(mPositions);
        result = 31 * result + Arrays.hashCode(mDistances);
        return result;
    }

    public double[] getLocation(){
        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(mPositions, mDistances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();


        double[] centroid = optimum.getPoint().toArray();
        Log.d("centroid",centroid.length+"");
        Log.d("centroid",centroid[0]+" : "+centroid[1]+"  :  "+centroid[2]+"");
        return centroid;
    }

}
