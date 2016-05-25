package com.spaceApplication.server.sampleModel.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.spaceApplication.shared.calculation.BasicCalculationOperation;

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
    private int size;
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
        size = time.size();
    }

    public RungeKuttaResult() {
        time = new Vector<Double>();
        tetta = new Vector<Double>();
        omega = new Vector<Double>();
        eps = new Vector<Double>();
        A = new Vector<Double>();
        ex = new Vector<Double>();
        step = new Vector<Double>();
        accuracy = new Vector<Double>();
        iter = new Vector<Integer>();
        size = time.size();
    }

    public int getSize() {
        return size;
    }

    public Vector getTime() {
        return time;
    }

    public Vector getTetta() {

        return tetta;
    }

    public Vector getConvertedTetta() {
        for (int i=0; i<tetta.size(); i++){
            tetta.set(i, BasicCalculationOperation.convertRadiansToDegrees(tetta.get(i)));
        }
        return tetta;
    }

    public Vector getOmega() {
        return omega;
    }

    public Vector getEps() {

        return eps;
    }

    public Vector getConvertedEps() {
        for (int i=0; i<eps.size(); i++){
            eps.set(i, BasicCalculationOperation.convertRadiansToDegrees(eps.get(i)));
        }
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

    public Vector getIter() {
        return iter;
    }

    public static void printResultsToConsole(RungeKuttaResult result){
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

        System.out.println("\nTime");
        result.getTime().forEach(z -> System.out.println((double)z));
        System.out.println("\nTetta");
        result.getTetta().forEach(z -> System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) z)));
        System.out.println("\nOmega");
        result.getOmega().forEach(z -> System.out.println((double)z));
        System.out.println("\nEps");
        result.getEps().forEach(z -> System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) z)));
        System.out.println("\nA");
        result.getA().forEach(z ->  System.out.println((double)z));
        System.out.println("\nEx");
        result.getEx().forEach(z ->  System.out.println((double)z));
        System.out.println("\nStep");
        result.getStep().forEach(z ->  System.out.println((double)z));
        System.out.println("\nAcuraccy");
        result.getAccuracy().forEach(z -> System.out.println((double)z));
        System.out.println("\nIter");
        result.getIter().forEach(z -> System.out.println(z));

    }

    public static void printFirstAndLastResults(RungeKuttaResult result){
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
        int size = result.getTime().size()-1;
        System.out.println("\nTime");
        System.out.println((double)   result.getTime().get(0));
        System.out.println((double)   result.getTime().get(size));
        System.out.println("\nTetta");
        System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) result.getTetta().get(0)));
        System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) result.getTetta().get(size)));
        System.out.println("\nOmega");
        System.out.println((double)   result.getOmega().get(0));
        System.out.println((double)   result.getOmega().get(size));
        System.out.println("\nEps");
        System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) result.getEps().get(0)));
        System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) result.getEps().get(size)));
        System.out.println("\nA");
        System.out.println((double)   result.getA().get(0));
        System.out.println((double)   result.getA().get(size));
        System.out.println("\nEx");
        System.out.println((double)   result.getEx().get(0));
        System.out.println((double)   result.getEx().get(size));
        System.out.println("\nStep");
        System.out.println((double)   result.getStep().get(0));
        System.out.println((double)   result.getStep().get(size));
        System.out.println("\nAcuraccy");
        System.out.println((double)   result.getAccuracy().get(0));
        System.out.println((double)   result.getAccuracy().get(size));
        System.out.println("\nIter");
        System.out.println(result.getIter().get(0));
        System.out.println(result.getIter().get(size));

    }

    public static void printResultsWithSignsToConsole(RungeKuttaResult result, int signes){
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

        System.out.println("\nTime");
        result.getTime().forEach(z -> System.out.println(BasicCalculationOperation.r((double)z, signes)));
        System.out.println("\nTetta");
        result.getTetta().forEach(z -> System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) z)));
        System.out.println("\nOmega");
        result.getOmega().forEach(z -> System.out.println(BasicCalculationOperation.r((double)z, signes)));
        System.out.println("\nEps");
        result.getEps().forEach(z -> System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) z)));
        System.out.println("\nA");
        result.getA().forEach(z -> System.out.println(BasicCalculationOperation.r((double)z, signes)));
        System.out.println("\nEx");
        result.getEx().forEach(z -> System.out.println(BasicCalculationOperation.r((double)z, signes)));
        System.out.println("\nStep");
        result.getStep().forEach(z -> System.out.println(BasicCalculationOperation.r((double)z, signes)));
        System.out.println("\nAcuraccy");
        result.getAccuracy().forEach(z -> System.out.println(BasicCalculationOperation.r((double)z, signes)));
        System.out.println("\nIter");
        result.getIter().forEach(z -> System.out.println(z));

    }

    public static void printWeightlessResultsToConsole(RungeKuttaResult result){
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
        System.out.println("\nTime");
        result.getTime().forEach(z -> System.out.println(z));
        System.out.println("\nTetta");
        result.getTetta().forEach(z -> System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) z)));
        System.out.println("\nOmega");
        result.getOmega().forEach(z -> System.out.println(z));
        System.out.println("\nEps");
        result.getEps().forEach(z -> System.out.println(BasicCalculationOperation.convertRadiansToDegrees((double) z)));
        System.out.println("\nEx");
        result.getEx().forEach(z -> System.out.println(z));
        System.out.println("\nStep");
        result.getStep().forEach(z -> System.out.println(z));
        System.out.println("\nAcuraccy");
        result.getAccuracy().forEach(z -> System.out.println(z));
        System.out.println("\nIter");
        result.getIter().forEach(z -> System.out.println(z));
    }

}
