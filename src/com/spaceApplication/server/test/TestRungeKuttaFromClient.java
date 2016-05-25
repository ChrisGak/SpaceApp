package com.spaceApplication.server.test;

import com.spaceApplication.client.exception.SpaceModelException;
import com.spaceApplication.client.space.model.CableSystemModel;
import com.spaceApplication.server.space.SpaceApplicationServiceImpl;

/**
 * Created by Кристина on 17.05.2016.
 */
public class TestRungeKuttaFromClient {
    private static CableSystemModel defaultTestModel = new CableSystemModel(20, 40 , 1000, 1000000, 10, 0.1, 10, 0.0167, 5,  1000, 10.0, 20.0, 0.01);

    public TestRungeKuttaFromClient(){

    }

    public static void main(String[] args) throws SpaceModelException {
        SpaceApplicationServiceImpl impl = new SpaceApplicationServiceImpl();

        impl.testCalculationResult(defaultTestModel);



    }
}
