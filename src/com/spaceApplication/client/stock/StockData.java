package com.spaceApplication.client.stock;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by Кристина on 24.02.2016.
 */
public class StockData extends JavaScriptObject {
    // Overlay types always have protected, zero argument constructors.
    protected StockData() {}                                              // <span style="color:black;">**(2)**</span>

    // JSNI methods to get stock data.
    public final native String getSymbol() /*-{ return this.symbol; }-*/; // <span style="color:black;">**(3)**</span>
    public final native double getPrice() /*-{ return this.price; }-*/;
    public final native double getChange() /*-{ return this.change; }-*/;

    // Non-JSNI method to return change percentage.                       // <span style="color:black;">**(4)**</span>
    public final double getChangePercent() {
        return 100.0 * getChange() / getPrice();
    }
}
