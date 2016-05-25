package com.spaceApplication.server.sampleModel.model;


import com.spaceApplication.shared.calculation.BasicCalculationOperation;
import com.spaceApplication.shared.calculation.BasicConsts;

import java.util.Vector;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Кристина on 08.02.2016.
 */
public class AccurateRopeSystem extends RopeFunction implements BaseModel{
    /**
     * double tetta, double omega, double eps,double iter, double ... param
     * @param model
     * @param iter
     */
    public AccurateRopeSystem(CableSystemModel model, double iter) {
        super(model);
        setStartVector(model.getTetta(), model.getOmega(), model.getEps(), model.getEx(), model.getA(), iter);
    }

    private Vector startVector;
    /**
     * Добавим ДУ для возмущенной системы для движения центра масс системы в оскулирующих элементах
     */
    protected double getOmegat(double A, double ex, double tetta, double omega, double eps) {
        return -3.0/2.0 * BasicCalculationOperation.getSquare(getEpst_(A, ex, eps))
                * BasicCalculationOperation.getReverseDegree(getNu(eps, ex))
                *Math.sin(2.0 * tetta) -
                getEpstt(A, ex, eps)
                + getM(A, eps, ex)/(model.getM_e() * BasicCalculationOperation.getSquare(model.getL() ));
    }
    protected double getEpst_(double A, double ex, double eps) {
        return Math.sqrt(BasicConsts.K.getValue() / BasicCalculationOperation.getThirdDegree(getP(A, ex)))
                *
                BasicCalculationOperation.getSquare(getNu(eps, ex));
    }
    protected double getEpst(double A, double ex, double tetta, double eps) {
        return getKoefficient(A, ex) * ((BasicConsts.K.getValue() / BasicCalculationOperation.getSquare(getR(A, eps, ex)))
                + (getA_s(A, eps, ex, tetta) * cos(eps)/ ex) - getA_t(A, eps, ex, tetta) * sin(eps)/ ex * (1  + getR(A, eps, ex)/ getP(A, ex))) ;
    }
    protected double getAt(double A, double ex, double tetta, double eps) {
        return getKoefficient(A, ex) * ((2.0 * A
                / (1.0 - BasicCalculationOperation.getSquare(ex))) *
                (getA_s(A, eps, ex, tetta)* ex * sin(eps) + getA_t(A, eps, ex, tetta) * getP(A, ex) / getR(A, eps, ex)));
    }
    protected double getExt(double A, double ex, double tetta, double eps) {
        return getKoefficient(A, ex) * ((getA_s(A, eps, ex, tetta) *sin(eps) + getA_t(A, eps, ex, tetta) *
                ((1.0+getR(A, eps, ex)/ getP(A, ex))*cos(eps) + ex*getR(A, eps, ex)/getP(A, ex))));
    }
    protected double getEpstt(double A, double ex, double eps) {
        //return (-2.0)* BasicConsts.K.getValue() * ex * sin(eps)/ BasicCalculationOperation.getThirdDegree(getP(A, ex));
        return 0;//(-2.0)* BasicConsts.K.getValue() * ex * sin(eps)/ BasicCalculationOperation.getThirdDegree(getP(A, ex));
    }
    /**
     * Вектор производных
     * @param x вектор переменных
     * @return
     */
    public synchronized Vector getDiffVector(Vector x) {
        if (x.size() != 6) {
            throw new RuntimeException("Размер вектора должен быть равен 6.");
        }
        final double tetta = (double) x.get(0);
        final double omega = (double) x.get(1);
        final double eps = (double) x.get(2);
        final double A = (double) x.get(3);
        final double ex = (double) x.get(4);
        final double iter = (double)x.get(5);
        Vector vector = new Vector();
        vector.add(0, omega);
        vector.add(1, getOmegat(A, ex, tetta, omega, eps));
        vector.add(2, getEpst(A, ex, tetta, eps));
        vector.add(3, getAt(A, ex, tetta, eps));
        vector.add(4, getExt(A,ex,tetta,eps));
        vector.add(5, iter);
        return vector;
    }

    public Vector getStartVector() {
       return startVector;
    }
    public void setStartVector(double tetta, double omega, double eps, double ex, double A, double iter) {
        startVector = new Vector();
        startVector.add(0, tetta);
        startVector.add(1, omega);
        startVector.add(2, eps);
        // A
        startVector.add(3, A);
        // ex
        startVector.add(4, ex);
        startVector.add(5, iter);
    }
}
