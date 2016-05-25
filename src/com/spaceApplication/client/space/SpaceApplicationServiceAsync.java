package com.spaceApplication.client.space;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.spaceApplication.client.space.model.CableSystemModel;
import com.spaceApplication.client.space.model.RungeKuttaResult;

public interface SpaceApplicationServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);

    void getSpaceModelType(AsyncCallback<String> async);

    void getTestCalcResult(AsyncCallback<RungeKuttaResult> async);

    void getCalculationResult(CableSystemModel baseModel, AsyncCallback<RungeKuttaResult> async);
}
