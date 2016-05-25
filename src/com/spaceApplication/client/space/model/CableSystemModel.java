package com.spaceApplication.client.space.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.spaceApplication.shared.calculation.BasicCalculationOperation;
import com.spaceApplication.shared.calculation.BasicConsts;
import org.moxieapps.gwt.highcharts.client.Point;

import java.util.Vector;

/**
 * Created by Кристина on 15.05.2016.
 */
public class CableSystemModel implements IsSerializable {
    /**
     * Массы космических аппаратов а приведенная масса
     */
    private  double m1;
    private  double m2;
    private  double m_e;
    private  double m;

    /**
     * Параметр орбиты
     */
    private  double p;
    /**
     * Длина троса,
     * плечо первого и второго тел
     */
    private  double L;
    private  double L_p;
    private  double L1;
    private  double L2;
    /**
     * Высота центра масс системы
     */
    private  double H;
    /**
     * Расстояние центра масс системы до центра Земли
     */
    private  double R_0;
    /**
     * Радиусы апоцентра и перицентра
     */
    private  double r_a;
    private  double r_p;
    /**
     * Большая полуось орбиты
     */
    private  double A;
    /**
     Эксцентриситет орбиты
     */
    private double ex;
    /**
     * Угол отклонения троса от вертикали
     */
    private  double tetta;
    /**
     *
     */
    private  double omega;
    /**
     * Истинная аномалия Земли
     */
    private  double eps;
    /**
     * Сила тока, протекающего по тросу
     */
    private  double I;

    private  int maxIter;
    private  double step;
    private  double stepMax;
    private  double D;

    /**
     * tetta and eps in radians
     */
    public CableSystemModel(double m1, double m2, double L, double H, double tetta, double omega, double eps, double ex, double I, int maxIter, double step, double stepMax, double D){
        this.m1 = m1;
        this.m2 = m2;
        this.m = m1 + m2;
        this.m_e = m1*m2/(m1+m2);
        this.L = L;
        this.H = H;
        this.omega =omega;

        this.tetta = tetta;
        this.eps = eps;

        this.I = I;
        this.ex = ex;
        setInitialLengthParameters(L);
        setInitialRadiusParameters(H);

        this.maxIter = maxIter;
        this.step = step;
        this.stepMax = stepMax;
        this.D = D;
    }

    public CableSystemModel(double m1, double m2, double L, double H, double tetta, double omega, double eps, double ex, double I){
        this.m1 = m1;
        this.m2 = m2;
        this.m = m1 + m2;
        this.m_e = m1*m2/(m1+m2);
        this.L = L;
        this.H = H;
        this.omega =omega;

//        this.tetta= BasicCalculationOperation.convertRadiansToDegrees(tetta);
//        this.eps = BasicCalculationOperation.convertRadiansToDegrees(eps);

        this.tetta = tetta;
        this.eps = eps;

        this.I = I;
        this.ex = ex;

        setInitialLengthParameters(L);
        setInitialRadiusParameters(H);
    }

    public CableSystemModel() {
    }

    private void setInitialLengthParameters(double l) {
        this.L1 = L*m2/(m1+m2);
        this.L2 = L*m1/(m1+m2);
        this.L_p = L*(m2-m1)/(2.0*(m1+m2));
    }

    private void setInitialRadiusParameters(double h) {
        this.R_0 = BasicConsts.RZ.getValue() + h;
        this.r_p = R_0;
        this.r_a = r_p*(1+ex)/(1-ex);
        this.A = (r_a+r_p)/2;
        this.p = A*(1- BasicCalculationOperation.getSquare(ex));
    }

    public double getM1() {
        return m1;
    }
    public double getM1_e() {
        return m1 /(m1+m2);
    }

    public double getM2_e() {
        return m2 /(m1+m2);
    }
    public double getA() {
        return A;
    }
    public double getM2() {
        return m2;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public double getL() {
        return L;
    }

    public void setL(double l) {
        L = l;
    }

    public double getM() {
        return m;
    }
    public double getI() {
        return I;
    }

    public double getTetta() {
        return tetta;
       // return BasicCalculationOperation.convertRadiansToDegrees(tetta);

    }

    public void setTetta(double tetta) {

        this.tetta = tetta;
    }

    /**
     * Приведенная масса
     * @return
     */
    public double getM_e() {
        return m_e;
    }

    public double getL1() {
        return L1;
    }

    public double getL2() {
        return L2;
    }

    public double getOmega() {
        return omega;
    }

    public double getEps() {
        return eps;
        //return BasicCalculationOperation.convertRadiansToDegrees(eps);
    }

    public double getL_p() {
        return L_p;
    }

    public void setI(double i) {
        I = i;
    }

    public double getEx() {
        return ex;
    }

    public void setEx(double ex) {
        this.ex = ex;
    }

    public double getH() {
        return H;
    }

    public int getMaxIter() {
        return maxIter;
    }

    public double getStep() {
        return step;
    }

    public double getStepMax() {
        return stepMax;
    }

    public double getD() {
        return D;
    }
    public void setMaxIter(int maxIter) {
        this.maxIter = maxIter;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public void setStepMax(double stepMax) {
        this.stepMax = stepMax;
    }

    public void setD(double D) {
        this.D = D;
    }


    public Vector getXNB1(Vector<Double> tetta){
        Vector xnb1 = new Vector();
        double m2_e = getM2_e();
        for (double value: tetta){
              xnb1.add(m2_e * L * Math.sin(value));
        }
        return  xnb1;
    }
    public Point[] getXNB1Points(Vector tetta) {
        Vector vector = getXNB1(tetta);

        Point[] points = new Point[vector.size()];
        for (int i=0; i<vector.size(); i++){
            points[i] = new Point((double)vector.get(i));
        }
        return points;
    }
    public Vector getXNB2(Vector<Double> tetta){
        Vector xnb2 = new Vector();
        double m1_e =  - getM1_e();
        for (double value: tetta){
            xnb2.add(m1_e * L * Math.sin(value));
        }
        return xnb2;

    }
    public Point[] getXNB2Points(Vector tetta) {
        Vector vector = getXNB2(tetta);

        Point[] points = new Point[vector.size()];
        for (int i=0; i<vector.size(); i++){
            points[i] = new Point((double)vector.get(i));
        }
        return points;
    }

    public Vector getYNB1(Vector<Double> tetta){
        Vector ynb1 = new Vector();
        double m2_e =  - getM2_e();
        for (double value: tetta){
            ynb1.add(m2_e * L * Math.cos(value));
        }
        return ynb1;
    }
    public Point[] getYNB1Points(Vector tetta) {
        Vector vector = getYNB1(tetta);

        Point[] points = new Point[vector.size()];
        for (int i=0; i<vector.size(); i++){
            points[i] = new Point((double)vector.get(i));
        }
        return points;
    }
    public Vector getYNB2(Vector<Double> tetta){
        Vector ynb2 = new Vector();
        double m1_e =  getM1_e();
        for (double value: tetta){
            ynb2.add(m1_e * L * Math.cos(value));
        }
        return ynb2;
    }
    public Point[] getYNB2Points(Vector tetta) {
        Vector vector = getYNB2(tetta);

        Point[] points = new Point[vector.size()];
        for (int i=0; i<vector.size(); i++){
            points[i] = new Point((double)vector.get(i));
        }
        return points;
    }

}
