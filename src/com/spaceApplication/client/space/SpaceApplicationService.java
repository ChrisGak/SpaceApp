package com.spaceApplication.client.space;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.spaceApplication.client.exception.SpaceModelException;
import com.spaceApplication.client.space.model.CableSystemModel;
import com.spaceApplication.client.space.model.RungeKuttaResult;

@RemoteServiceRelativePath("SpaceApplicationService")
public interface SpaceApplicationService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);
    String getSpaceModelType() throws SpaceModelException;
    RungeKuttaResult getTestCalcResult() throws SpaceModelException;

    RungeKuttaResult getCalculationResult(CableSystemModel baseModel) throws SpaceModelException;

    /**
     * Utility/Convenience class.
     * Use SpaceApplicationService.App.getInstance() to access static instance of SpaceApplicationServiceAsync
     */
    public static class App {
        private static SpaceApplicationServiceAsync ourInstance = GWT.create(SpaceApplicationService.class);

        public static synchronized SpaceApplicationServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
