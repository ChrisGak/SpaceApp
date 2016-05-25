package com.spaceApplication.client.internationalization;

import com.google.gwt.i18n.client.Constants;

/**
 * Created by Кристина on 24.02.2016.
 */
public interface StockWatcherConstants extends Constants{
    @DefaultStringValue("StockWatcher")
    String stockWatcher();

    @DefaultStringValue("Symbol")
    String symbol();

    @DefaultStringValue("Price")
    String price();

    @DefaultStringValue("Change")
    String change();

    @DefaultStringValue("Remove")
    String remove();

    @DefaultStringValue("Add")
    String add();
}
