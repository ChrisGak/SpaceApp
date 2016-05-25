package com.spaceApplication.server.sampleModel.model;


import com.spaceApplication.shared.calculation.BasicCalculationOperation;
import com.spaceApplication.shared.calculation.BasicConsts;

import java.io.Serializable;

/**
 * Created by Кристина on 25.01.2016.
 */
public class CableSystemModel implements Serializable{

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

    public CableSystemModel(double m1, double m2, double L, double H, double tetta, double omega, double eps, double ex, double I, boolean isAccurate){
        this.m1 = m1;
        this.m2 = m2;
        this.m = m1 + m2;
        this.m_e = m1*m2/(m1+m2);
        this.L = L;
        this.H = H;
        this.ex = ex;
        if (isAccurate)
        {
            setInitialLengthParameters(L);
            setInitialRadiusParameters(H);
        }
        else {
            this.p = BasicConsts.RZ.getValue() + H;
        }
        this.tetta=BasicCalculationOperation.convertDegreesToRadians(tetta);
        this.eps = BasicCalculationOperation.convertDegreesToRadians(eps);

        this.omega =omega;
        this.I = I;
    }

    private void setInitialLengthParameters(double l) {
        this.L1 = L*m2/(m1+m2);
        this.L2 = L*m1/(m1+m2);
        this.L_p = L*(m2-m1)/(2.0*(m1+m2));
    }

    private void setInitialRadiusParameters(double h) {
        this.R_0 = BasicConsts.RZ.getValue() + h;
        this.r_p = R_0;
        this.r_a = r_p* (1.0+ ex) /(1.0- ex);
        this.A = (r_a+r_p)/2.0;
        this.p = A*(1.0- BasicCalculationOperation.getSquare(ex));
    }

    public double getM1() {
        return m1;
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
}
