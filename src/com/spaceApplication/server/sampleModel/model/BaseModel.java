package com.spaceApplication.server.sampleModel.model;

import java.util.Vector;

/**
 * Created by Кристина on 25.01.2016.
 */
public interface BaseModel {
      Vector getDiffVector(Vector x);
      Vector getStartVector();

    /** void setStartVector(double tetta, double omega, double eps, double iter, double A, double ex);
     *
     * @param tetta
     * @param omega
     * @param eps
     * @param iter
     */
     void setStartVector(double tetta, double omega, double eps, double ex, double A, double iter);
}
