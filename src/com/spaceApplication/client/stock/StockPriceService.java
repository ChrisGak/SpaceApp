package com.spaceApplication.client.stock;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.spaceApplication.client.exception.DelistedException;

/**
 * Created by Кристина on 20.02.2016.
 */
@RemoteServiceRelativePath("tutorialExample")
public interface StockPriceService extends RemoteService{
    StockPrice[] getPrices(String[] symbols) throws DelistedException;
}
