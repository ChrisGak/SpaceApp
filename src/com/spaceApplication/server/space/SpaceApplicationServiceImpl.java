package com.spaceApplication.server.space;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.spaceApplication.client.exception.SpaceModelException;
import com.spaceApplication.client.space.SpaceApplicationService;
import com.spaceApplication.client.space.html.UIConsts;
import com.spaceApplication.client.space.model.RungeKuttaResult;
import com.spaceApplication.server.export.WriteExcel;
import com.spaceApplication.server.sampleModel.differentiation.RungeKuttaImpl;
import com.spaceApplication.server.sampleModel.model.AccurateRopeSystem;
import com.spaceApplication.server.sampleModel.model.CableSystemModel;
import jxl.write.WriteException;

import java.io.IOException;

public class SpaceApplicationServiceImpl extends RemoteServiceServlet implements SpaceApplicationService {

    private  WriteExcel test = new WriteExcel();
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    @Override
    public String getSpaceModelType() throws SpaceModelException {
        return "Accurate model";
    }

    public RungeKuttaResult getTestCalcResult() throws SpaceModelException{

        boolean isAccurate = true;
        /**
         * double m1, double m2, double L, double H, double tetta, double omega, double eps, boolean isAccurate
         */
        CableSystemModel testModel = new CableSystemModel(20, 40 , 1000, 1000000, 0, 0, 0, 0.0167, 5, isAccurate);
        /**
         * CableSystemModel model, double iter, double A, double ex
         */
        AccurateRopeSystem accurateRope = new AccurateRopeSystem(testModel, 0);

        RungeKuttaImpl method = new RungeKuttaImpl();
        /**
         * BaseModel rope, int maxIter, double step, double stepMax, double D
         */
        method.fullDiffCalc(accurateRope, 100, 10.0, 20.0, 0.01);
       // method.fullDiffCalcConstStep(accurateRope, 100, 10.0, 10.0);
        /**
         * (Vector time, Vector tetta, Vector omega, Vector eps, Vector A, Vector ex, Vector step, Vector accuracy, Vector iter){
         */
        RungeKuttaResult clientResult = new RungeKuttaResult(
                method.getResult().getTime(),
                method.getResult().getConvertedTetta(),
                method.getResult().getOmega(),
                method.getResult().getConvertedEps(),
                method.getResult().getA(),
                method.getResult().getEx(),
                method.getResult().getStep(),
                method.getResult().getAccuracy(),
                method.getResult().getIter());

        return  clientResult;
    }

    public RungeKuttaResult getCalculationResult(com.spaceApplication.client.space.model.CableSystemModel baseModel) throws SpaceModelException {
        boolean isAccurate = true;
        /**
         * double m1, double m2, double L, double H, double tetta, double omega, double eps, boolean isAccurate
         */
        CableSystemModel testModel = new CableSystemModel(baseModel.getM1(), baseModel.getM2(), baseModel.getL(),
                baseModel.getH(), baseModel.getTetta(), baseModel.getOmega(), baseModel.getEps(), baseModel.getEx(), baseModel.getI(), isAccurate);

        /**
         * CableSystemModel model, double iter, double A, double ex
         */
        AccurateRopeSystem accurateRope = new AccurateRopeSystem(testModel, 0);

        RungeKuttaImpl method = new RungeKuttaImpl();
        /**
         * BaseModel rope, int maxIter, double step, double stepMax, double D
         */
        method.fullDiffCalc(accurateRope, baseModel.getMaxIter(), baseModel.getStep(), baseModel.getStepMax(), baseModel.getD());
        /**
         * (Vector time, Vector tetta, Vector omega, Vector eps, Vector A, Vector ex, Vector step, Vector accuracy, Vector iter){
         */
        RungeKuttaResult clientResult = new RungeKuttaResult(
                method.getResult().getTime(),
                method.getResult().getConvertedTetta(),
                method.getResult().getOmega(),
                method.getResult().getConvertedEps(),
                method.getResult().getA(),
                method.getResult().getEx(),
                method.getResult().getStep(),
                method.getResult().getAccuracy(),
                method.getResult().getIter());


        test.createFileIfNotExists(UIConsts.fileName);
        try {
            test.writeRungeKuttaResult(method.getResult());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }

        return  clientResult;
    }

    public RungeKuttaResult testCalculationResult(com.spaceApplication.client.space.model.CableSystemModel baseModel) throws SpaceModelException {
        boolean isAccurate = true;
        /**
         * double m1, double m2, double L, double H, double tetta, double omega, double eps, boolean isAccurate
         */
        CableSystemModel testModel = new CableSystemModel(baseModel.getM1(), baseModel.getM2(), baseModel.getL(),
                baseModel.getH(), baseModel.getTetta(), baseModel.getOmega(), baseModel.getEps(), baseModel.getEx(), baseModel.getI(), isAccurate);

        /**
         * CableSystemModel model, double iter, double A, double ex
         */
        AccurateRopeSystem accurateRope = new AccurateRopeSystem(testModel, 0);

        RungeKuttaImpl method = new RungeKuttaImpl();
        /**
         * BaseModel rope, int maxIter, double step, double stepMax, double D
         */
        method.fullDiffCalc(accurateRope, baseModel.getMaxIter(), baseModel.getStep(), baseModel.getStepMax(), baseModel.getD());
        /**
         * (Vector time, Vector tetta, Vector omega, Vector eps, Vector A, Vector ex, Vector step, Vector accuracy, Vector iter){
         */
        RungeKuttaResult clientResult = new RungeKuttaResult(
                method.getResult().getTime(),
                method.getResult().getConvertedTetta(),
                method.getResult().getOmega(),
                method.getResult().getConvertedEps(),
                method.getResult().getA(),
                method.getResult().getEx(),
                method.getResult().getStep(),
                method.getResult().getAccuracy(),
                method.getResult().getIter());

        return  clientResult;
    }
}