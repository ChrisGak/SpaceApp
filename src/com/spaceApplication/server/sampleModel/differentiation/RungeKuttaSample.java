package com.spaceApplication.server.sampleModel.differentiation;

import static com.spaceApplication.shared.calculation.BasicCalculationOperation.f;
import static com.spaceApplication.shared.calculation.BasicCalculationOperation.g;
import static com.spaceApplication.shared.calculation.BasicCalculationOperation.r;

/**
 * Created by Кристина on 06.02.2016.
 */
public class RungeKuttaSample {
    public static void main(String[] args) {
        int k = 2;
        double Xo, Yo, Y1, Zo, Z1;
        double k1, k2, k4, k3, h;
        double q1, q2, q4, q3;
                /*
                 *Начальные условия
                 */
        Xo = 0;
        Yo = 0.8;
        Zo = 2;

        h = 0.1; // шаг

        System.out.println("\tX\t\tY\t\tZ");
        for (; r(Xo, 2) < 1.0; Xo += h) {

            k1 = h * f(Xo, Yo, Zo);
            q1 = h * g(Xo, Yo, Zo);

            k2 = h * f(Xo + h / 2.0, Yo + q1 / 2.0, Zo + k1 / 2.0);
            q2 = h * g(Xo + h / 2.0, Yo + q1 / 2.0, Zo + k1 / 2.0);

            k3 = h * f(Xo + h / 2.0, Yo + q2 / 2.0, Zo + k2 / 2.0);
            q3 = h * g(Xo + h / 2.0, Yo + q2 / 2.0, Zo + k2 / 2.0);

            k4 = h * f(Xo + h, Yo + q3, Zo + k3);
            q4 = h * g(Xo + h, Yo + q3, Zo + k3);

            Z1 = Zo + (k1 + 2.0 * k2 + 2.0 * k3 + k4) / 6.0;
            Y1 = Yo + (q1 + 2.0 * q2 + 2.0 * q3 + q4) / 6.0;
            System.out.println("\t" + r(Xo + h, k) + "\t\t" + r(Y1, k) + "\t\t" + r(Z1, k));
            Yo = Y1;
            Zo = Z1;
        }

    }

}
