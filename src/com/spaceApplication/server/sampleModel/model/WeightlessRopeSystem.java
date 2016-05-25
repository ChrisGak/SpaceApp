package com.spaceApplication.server.sampleModel.model;


import com.spaceApplication.shared.calculation.BasicCalculationOperation;
import com.spaceApplication.shared.calculation.BasicConsts;

import java.util.Vector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Кристина on 08.02.2016.
 */
public class WeightlessRopeSystem extends RopeFunction implements BaseModel{
    private Vector startVector;
    private double ex;

    public WeightlessRopeSystem(CableSystemModel model, double iter) {
        super(model);
        setStartVector(model.getTetta(), model.getOmega(), model.getEps(), model.getEx(), model.getA(), iter);
    }
    protected double getOmegat(double A, double ex, double tetta, double omega, double eps) {
        return -3.0/2.0 * BasicCalculationOperation.getSquare(getEpst(A, ex, tetta, eps))* BasicCalculationOperation.getReverseDegree(getNu(eps, ex))*Math.sin(2*tetta) -
                getEpstt(A, ex, eps);
    }
    protected double getEpst(double A, double ex, double tetta, double eps) {
        return (BasicCalculationOperation.getThirdDegree(BasicConsts.K.getValue()/ getP(A,ex)) / BasicCalculationOperation.getSquare(getNu(eps, ex)));
    }
    protected double getAt(double A, double ex, double tetta, double eps) {
        return getKoefficient(A, ex) * (2.0* A/ (1.0 - BasicCalculationOperation.getSquare(ex)) *
                (getA_s(A, ex, tetta, eps)* ex * sin(eps) + getA_t(A, ex, tetta, eps) * getP(A, ex) / getR(A, eps, ex)));
    }
    protected double getExt(double A, double ex, double tetta, double eps) {
        return getKoefficient(A, ex) * ((getA_s(A, ex, tetta, eps) *sin(eps) + getA_t(A, ex, tetta, eps) *
                ((1+getR(A, eps, ex)/ getP(A, ex))*cos(eps) + ex*getR(A, eps, ex)/getP(A, ex))));
    }
    protected double getEpstt(double A, double ex, double eps) {
        return (-2)* BasicConsts.K.getValue() * ex * sin(eps)/ BasicCalculationOperation.getThirdDegree(getP(A, ex));
    }
    protected double getEpstt(double ex, double eps) {
       return BasicCalculationOperation.r((-2.0)* BasicConsts.K.getValue() * ex * sin(eps)/ BasicCalculationOperation.getThirdDegree(model.getP()), 4);
    }

    /**
     * Вектор производных
     * @param x вектор переменных
     * @return
     */
    public synchronized Vector getDiffVector(Vector x) {
        if (x.size() != 4) {
            throw new RuntimeException("Размер вектора должен быть равен 4.");
        }
        final double tetta = (double) x.get(0);
        final double omega = (double) x.get(1);
        final double eps = (double) x.get(2);
        final double iter = (double)x.get(3);
        Vector vector = new Vector();
        vector.add(0, omega);
        vector.add(1, getOmegat(tetta, omega, eps));
        vector.add(2, getEpst(eps));
        vector.add(3, iter);
        return vector;
    }

    private double getEpst(double eps)
    {
        return getSqrKoefficient()* BasicCalculationOperation.getSquare(getNu(eps, ex));
    }

    private double getOmegat(double tetta, double omega, double eps) {
        return ((-3.0/2.0)*
                BasicCalculationOperation.getSquare(getEpst(eps))*
                BasicCalculationOperation.getReverseDegree(getNu(eps, ex))
                *sin(2*tetta))-
                getEpstt(ex, eps);
    }

    public Vector getStartVector() {
        return startVector;
    }
    public void setStartVector(double tetta, double omega, double eps, double ex, double A, double iter) {
        startVector = new Vector();
        startVector.add(0, tetta);
        startVector.add(1, omega);
        startVector.add(2, eps);
        this.ex =ex;
        startVector.add(3, iter);
    }
}
