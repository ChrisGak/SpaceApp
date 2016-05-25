package com.spaceApplication.server.sampleModel.differentiation;

import com.spaceApplication.server.sampleModel.model.BaseModel;
import com.spaceApplication.server.sampleModel.model.RungeKuttaResult;
import com.spaceApplication.shared.calculation.BasicCalculationOperation;

import java.util.Vector;

/**
 * Created by Кристина on 08.02.2016.
 */
public class RungeKuttaImpl {
    private RungeKuttaResult result;

    private Vector k1, k2, k3, k4;
    private Vector y = new Vector(6);
    private Vector ys = new Vector(6);
    private Vector DS = new Vector(6);
    private Vector innerDS = new Vector(6);
    /**
     * Мапа векторов расчитанных значений
     * 0 - время
     * 1 - тетта
     * 2 - омега
     * 3 - ипсилон
     * 4 - А полуось орбиты - extra
     * 5 - эксцентриситет орбиты - extra
     * 6 - шаг
     * 7 - значение точности
     * 8 - номер итерации
     */

    public void fullDiffCalc(BaseModel rope, int maxIter, double step, double stepMax, double D) {
        double t = 0, tt = 0;
        int i = 0;
        result = new RungeKuttaResult();

        y = rope.getStartVector();
        while (t < maxIter) {
            tt = t + step;

            k1 = rope.getDiffVector(y);
            k2 = rope.getDiffVector(BasicCalculationOperation.scalarMultiplyAndAddVectors(k1, y, step / 2.0));
            k3 = rope.getDiffVector(BasicCalculationOperation.scalarMultiplyAndAddVectors(k2, y, step / 2.0));
            k4 = rope.getDiffVector(BasicCalculationOperation.scalarMultiplyAndAddVectors(k3, y, step));
            /**
             * Return result vector
             */
            calculateResY(step);

            t = tt;
            y = (Vector) ys.clone();
            result.getTime().add(i, t);
            result.getTetta().add(i, y.get(0));
            result.getOmega().add(i, y.get(1));
            result.getEps().add(i, y.get(2));
            result.getA().add(i, y.get(3));
            result.getEx().add(i, y.get(4));
            result.getStep().add(i, step);

            calculateDS(step);

            double DSMax = calculateDSMax(D);
            result.getAccuracy().add(i ,DSMax);
            result.getIter().add(i, i);
            i++;

            step = calculateAcuraccy(step, DSMax, stepMax);

        }
        System.out.println("The end of RungeKutta method implementation.");
    }

    public void fullDiffCalcConstStep(BaseModel rope, int maxIter, double step, double D) {
        double t = 0, tt = 0;
        int i = 0;
        result = new RungeKuttaResult();
        y = rope.getStartVector();
        while (t < maxIter) {
            tt = t + step;

            k1 = rope.getDiffVector(y);
            k2 = rope.getDiffVector(BasicCalculationOperation.scalarMultiplyAndAddVectors(k1, y, step / 2.0));
            k3 = rope.getDiffVector(BasicCalculationOperation.scalarMultiplyAndAddVectors(k2, y, step / 2.0));
            k4 = rope.getDiffVector(BasicCalculationOperation.scalarMultiplyAndAddVectors(k3, y, step));
            /**
             * Return result vector
             */
            calculateResY(step);

            t = tt;
            y = (Vector) ys.clone();

            result.getTime().add(i, t);
            result.getTetta().add(i, y.get(0));
            result.getOmega().add(i, y.get(1));
            result.getEps().add(i, y.get(2));
            result.getA().add(i, y.get(3));
            result.getEx().add(i, y.get(4));
            result.getStep().add(i, step);

            calculateDS(step);

            double DSMax = calculateDSMax(D);

            result.getAccuracy().add(i ,DSMax);
            result.getIter().add(i, i);
            i++;
        }
        System.out.println("The end of RungeKutta method with const step implementation.");
    }

    private double calculateDSMax(double d) {
        if (DS.isEmpty()) {
            throw new RuntimeException("Вектор пуст");
        }

        innerDS.clear();
        for (int i = 0; i < DS.size(); i++) {
            innerDS.add(i, Math.abs((double) DS.get(i) / d));
        }

        return BasicCalculationOperation.getMaxElem(innerDS);
    }

    private void calculateDS(double step) {
        if (k1.size() != k2.size() && k2.size() != k3.size() && k3.size() != k4.size() && k4.size() != y.size()) {
            throw new RuntimeException("Вектора должны быть одной размерности");
        }
        DS.clear();
        for (int i = 0; i < k1.size(); i++) {
            DS.add(i, step * ((double) k1.get(i) - (double) k2.get(i) - (double) k3.get(i) + (double) k4.get(i)));
            //DS.add(i, BasicCalculationOperation.r(step * ((double) k1.get(i) - (double) k2.get(i) - (double) k3.get(i) + (double) k4.get(i)), 5));
        }
    }

    private void calculateResY(double step) {
        if (k1.size() != k2.size() && k2.size() != k3.size() && k3.size() != k4.size() && k4.size() != y.size()) {
            throw new RuntimeException("Вектора должны быть одной размерности");
        }
        ys.clear();
        for (int i = 0; i < k1.size(); i++) {
            ys.add(i, (double) y.get(i) + step / 6.0 * ((double) k1.get(i) + 2.0 * (double) k2.get(i) + 2.0 * (double) k3.get(i) + (double) k4.get(i)));
        }
    }

    private static double calculateAcuraccy(double step, double DSMax, double stepMax) {
        double step_ = 0;
        if (0.1 <= DSMax && DSMax <= 1) {
            step_ = step;
        } else if (DSMax < 0.1) {
            step_ = step * 2;
        } else if (DSMax > 1) {
            step_ = step / 2;
        }

        if (step_ > stepMax) {
            return stepMax;
        } else return step_;
    }

    public RungeKuttaResult getResult() {
        return result;
    }

    public void setResult(RungeKuttaResult result) {
        this.result = result;
    }
}
