package com.spaceApplication.client.space.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.spaceApplication.client.space.html.UIConsts;
import com.spaceApplication.shared.calculation.BasicCalculationOperation;
import org.moxieapps.gwt.highcharts.client.Point;

import java.util.Vector;

/**
 * Created by Кристина on 28.02.2016.
 */
public class RungeKuttaResult implements IsSerializable {
    private Vector<Double> time ;
    private Vector<Double> tetta;
    private Vector<Double> omega;
    private Vector<Double> eps;
    private Vector<Double> A;
    private Vector<Double> ex;
    private Vector<Double> step;
    private Vector<Double> accuracy;
    private Vector<Integer> iter;

    /**
     * Мапа векторов расчитанных значений
     * 0 - время
     * 1 - тетта
     * 2 - омега
     * 3 - ипсилон
     * 4 - А полуось орбиты
     * 5 - эксцентриситет орбиты
     * 6 - шаг
     * 7 - значение точности
     * 8 - номер итерации
     */
    public RungeKuttaResult(Vector time, Vector tetta, Vector omega, Vector eps, Vector A, Vector ex, Vector step, Vector accuracy, Vector iter){
        this.time = (Vector) time.clone();
        this.tetta = (Vector) tetta.clone();
        this.omega = (Vector) omega.clone();
        this.eps = (Vector) eps.clone();
        this.A = (Vector) A.clone();
        this.ex = (Vector) ex.clone();
        this.step = (Vector) step.clone();
        this.accuracy = (Vector) accuracy.clone();
        this.iter = (Vector) iter.clone();
    }

    public RungeKuttaResult() {
    }

    public Vector getTime() {
        return time;
    }

    /**
     * in Radians
     * @return
     */
    public Vector getTetta() {
        return tetta;
    }

    public Vector getOmega() {
        return omega;
    }
    /**
     * in Radians
     * @return
     */
    public Vector getEps() {
        return eps;
    }

    public Vector getA() {
        return A;
    }

    public Vector getEx() {
        return ex;
    }
    public Vector getStep() {
        return step;
    }

    public Vector getAccuracy() {
        return accuracy;
    }

    public Vector getHeightUnderEarth(){
        return BasicCalculationOperation.getRR_RZ(getA(), getEps(), getEx());
    }

    public Point[] getHeightPoints() {
        Vector vector = getHeightUnderEarth();


        Point[] heightPoints = new Point[vector.size()];
        for (int i=0; i<vector.size(); i++){
            heightPoints[i] = new Point((double)vector.get(i) / UIConsts.toKilo);
        }
        return heightPoints;
    }

    public Point[] getTettaPoints() {
        Point[] tettaPoints = new Point[tetta.size()];
        for (int i=0; i<tetta.size(); i++){
            tettaPoints[i] = new Point((double)getTetta().get(i));
        }
        return tettaPoints;
    }
    public Point[] getOmegaPoints() {
        Point[] points = new Point[omega.size()];
        for (int i=0; i<omega.size(); i++){
            points[i] = new Point((double)getOmega().get(i));
        }
        return points;
    }
    public Point[] getEpsPoints() {
        Point[] points = new Point[eps.size()];
        for (int i=0; i<eps.size(); i++){
            points[i] = new Point((double)getEps().get(i));
        }
        return points;
    }
    public Point[] getAPoints() {
        Point[] points = new Point[A.size()];
        for (int i=0; i<A.size(); i++){
            points[i] = new Point((double)getA().get(i) / UIConsts.toKilo);
        }
        return points;
    }
    public Point[] getTimePoints() {
        Point[] points = new Point[time.size()];
        for (int i=0; i<time.size(); i++){
            points[i] = new Point((double)getTime().get(i));
        }
        return points;
    }
    public Point[] getExPoints() {
        Point[] points = new Point[ex.size()];
        for (int i=0; i<ex.size(); i++){
            points[i] = new Point((double)getEx().get(i));
        }
        return points;
    }

    public Point[] getStepPoints() {
        Point[] points = new Point[step.size()];
        for (int i=0; i<step.size(); i++){
            points[i] = new Point((double)getStep().get(i));
        }
        return points;
    }
    public Point[] getAccuracyPoints() {
        Point[] points = new Point[accuracy.size()];
        for (int i=0; i<accuracy.size(); i++){
            points[i] = new Point((double)getAccuracy().get(i));
        }
        return points;
    }
  }
