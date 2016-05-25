package com.spaceApplication.client.stock;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StockPriceServiceAsync {
    void getPrices(String[] symbols, AsyncCallback<StockPrice[]> async);
}
