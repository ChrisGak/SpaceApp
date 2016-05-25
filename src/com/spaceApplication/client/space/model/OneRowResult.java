package com.spaceApplication.client.space.model;

/**
 * Created by Кристина on 29.02.2016.
 */
public class OneRowResult {
    private double time;
    private double tetta;
    private double omega;
    private double eps;
    private double A;
    private double ex ;
    private double step;
    private double accuracy;
    private double iter;

    public OneRowResult(Double time, Double tetta, Double omega, Double eps,
                        Double a, Double ex, Double step, Double accuracy, Double iter)
    {
        this.time = time.doubleValue();
        this.tetta = tetta.doubleValue();
        this.omega = omega.doubleValue();
        this.eps = eps.doubleValue();
        this.A = a.doubleValue();
        this.ex = ex.doubleValue();
        this.step = step.doubleValue();
        this.accuracy = accuracy.doubleValue();
        this.iter = iter.doubleValue();
    }

    public double getTime() {
        return time;
    }

    public double getTetta() {
        return tetta;
    }

    public double getOmega() {
        return omega;
    }

    public double getEps() {
        return eps;
    }

    public double getA() {
        return A;
    }

    public double getEx() {
        return ex;
    }

    public double getStep() {
        return step;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public double getIter() {
        return iter;
    }
}
