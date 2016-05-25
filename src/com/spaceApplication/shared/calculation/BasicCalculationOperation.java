package com.spaceApplication.shared.calculation;

import java.util.Vector;

/**
 * Created by Кристина on 07.02.2016.
 */
public class BasicCalculationOperation {
    /**
     * функция для округления и отбрасывания "хвоста"
     */
    public static double r(double value, int k) {
        return (double) Math.round((Math.pow(10, k) * value)) / Math.pow(10, k);
    }

    /**
     * функции, которые получаются из системы
     */
    public static double f(double x, double y, double z) {
        return (Math.cos(3 * x) - 4 * y);
    }
    public static Vector scalarMultiplyAndAddVectors(Vector x, Vector y, double z) {
        if (x.size() != y.size()){
            throw new RuntimeException("Вектора должны быть одной размерности");
        }
        Vector res = new Vector(x.size());
        for (int i = 0; i<x.size(); i++){
            res.add(i, (double)y.get(i) + z * (double)x.get(i));
        }
        return res;
    }

    public static double getMaxElem(Vector x) {
        if (x.isEmpty()){
            throw new RuntimeException("Вектор пуст");
        }
        double max = (double)x.get(0);
        for (int i=1; i<x.size(); i++) {
            if ((double)x.get(i)> max)
                max = (double)x.get(i);
        }
        return max;
    }

    public static double g(double x, double y, double z) {
        return (z);
    }
    public static double getSquare(double x) {
        return x*x;
    }
    public static double getReverseDegree(double x) {
        return 1.0 / x;
    }
    public static double getThirdDegree(double x) {
        return x*x*x;
    }
    public static double convertDegreesToRadians(double x) {
        return x * Math.PI / 180.0;
    }
    public static double convertRadiansToDegrees(double x) {
        return x * 180.0 / Math.PI;
    }
    /**
     * Obtain an angle from a given number of degrees, minutes and seconds.
     *
     * @param degrees integer number of degrees, positive.
     * @param minutes integer number of minutes, positive only between 0 and 60.
     * @param seconds integer number of seconds, positive only between 0 and 60.
     *
     * @return a new angle whose size in degrees is given by <code>degrees</code>, <code>minutes</code> and
     *         <code>seconds</code>.
     *
     * @throws IllegalArgumentException if minutes or seconds are outside the 0-60 range.
     */
    public static double fromDMS(int degrees, int minutes, int seconds)
    {
        if (minutes < 0 || minutes >= 60)
        {
            String message = "generic.ArgumentOutOfRange";
            throw new IllegalArgumentException(message);
        }
        if (seconds < 0 || seconds >= 60)
        {
            String message = "generic.ArgumentOutOfRange";
            throw new IllegalArgumentException(message);
        }

        return Math.signum(degrees) * (Math.abs(degrees) + minutes / 60d + seconds / 3600d);
    }

    public static double fromDMdS(int degrees, double minutes)
    {
        if (minutes < 0 || minutes >= 60)
        {
            String message = "generic.ArgumentOutOfRange";
            throw new IllegalArgumentException(message);
        }

        return Math.signum(degrees) * (Math.abs(degrees) + minutes / 60d);
    }
    ///
    ///
    public static double getP(double A, double ex) {
        double p = A*(1.0 - BasicCalculationOperation.getSquare(ex));
        return p;
        //return A*(1.0 - BasicCalculationOperation.getSquare(ex));
    }
    public static double getNu(double eps, double ex) {
        double nu = 1.0 + ex*Math.cos(eps);
        return nu;
        //return 1.0 + ex*Math.cos(eps);
    }
    /**
     * @param A
     * @param eps
     * @param ex
     * @return Уравнение орбиты центра масс
     */
    public static Vector<Double> getRR_RZ(Vector<Double> A, Vector<Double> eps, Vector<Double> ex) {
        Vector<Double> r = new Vector<>();

        for (int i=0; i< A.size(); i++){
            r.add((getP(A.get(i), ex.get(i))/getNu(eps.get(i), ex.get(i)) - BasicConsts.RZ.getValue()));
        }
        return r;
    }
}
