package com.spaceApplication.client.internationalization;

/**
 * Created by Кристина on 25.01.2016.
 */
public class StringConsts {
    public String[] paramsNames() {
        return new String[]{"L", "V", "θ", "θ˙", "β", "β˙"};
    }

    public String[] paramsExtNames() {
        return new String[]{"м", "м/с", "рад", "рад/с", "рад", "рад/с"};
    }

    public String[] initialParamsNames() {
        return new String[]{"m1, кг", "m2, кг", "ρ, кг/м3", "a", "b", "Lk, м", "H, км"};
    }


    public String[] customParamsNames() {
        return new String[]{"t", "x", "y", "T"};
    }


    public String[] customParamsExtNames() {
        return new String[]{"с", "м", "м", "H"};
    }


}
