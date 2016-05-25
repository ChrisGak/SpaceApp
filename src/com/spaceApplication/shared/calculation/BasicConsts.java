package com.spaceApplication.shared.calculation;

/**
 * Created by Кристина on 06.02.2016.
 */
public enum BasicConsts {
        RZ (6371.02E3, "Радиус Земли", "Earth's radius"),
        ex_0 (0.0167, "Эксцентриситет орбиты", ""),
        K (398600E9, "Гравитационная постоянная Земли", ""),
        Mu (8E15, "Магнитный момент земного диполя", "");

        private final double value;   // в системе СИ
        private final String representation;
        private final String englishRepresentation;
        BasicConsts(double value, String representation, String englishRepresentation) {
                this.value = value;
                this.representation = representation;
                this.englishRepresentation = englishRepresentation;
        }

        public String getRepresentation() {
                return representation;
        }

        public double getValue() {
                return value;
        }
}
