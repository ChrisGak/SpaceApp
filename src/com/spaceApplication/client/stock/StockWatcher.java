package com.spaceApplication.client.stock;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.spaceApplication.client.exception.DelistedException;
import com.spaceApplication.client.internationalization.StockWatcherConstants;
import com.spaceApplication.client.internationalization.StockWatcherMessages;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class StockWatcher implements EntryPoint {
    private static final int REFRESH_INTERVAL = 5000; // ms
    private VerticalPanel mainPanel = new VerticalPanel();
    private FlexTable stocksFlexTable = new FlexTable();
    private HorizontalPanel addPanel = new HorizontalPanel();
    private VerticalPanel myMainPanel = new VerticalPanel();
    private TextBox newSymbolTextBox = new TextBox();
    private Button addStockButton = new Button("Add");
    private Label lastUpdatedLabel = new Label();
    private ArrayList<String> stocks = new ArrayList<String>();
    // create an instance of the service proxy class by calling GWT.create(Class).
    private StockPriceServiceAsync stockPriceSvc = GWT.create(StockPriceService.class);
    private Label errorMsgLabel = new Label();
    private static final String JSON_URL = GWT.getModuleBaseURL() + "stockPrices?q=";
    private StockWatcherConstants constants = GWT.create(StockWatcherConstants.class);
    private StockWatcherMessages messages = GWT.create(StockWatcherMessages.class);
    private Button showResultButton = new Button("Show");
    private FlexTable gridResult = new FlexTable();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // Set the window title, the header text, and the Add button text.
        Window.setTitle(constants.stockWatcher());
        RootPanel.get("appTitle").add(new Label(constants.stockWatcher()));
        addStockButton = new Button(constants.add());
        // Create table for stock data.
        stocksFlexTable.setText(0, 0, constants.symbol());
        stocksFlexTable.setText(0, 1, constants.price());
        stocksFlexTable.setText(0, 2, constants.change());
        stocksFlexTable.setText(0, 3, constants.remove());
        // Create table for stock data.
//        stocksFlexTable.setText(0, 0, "Symbol");
//        stocksFlexTable.setText(0, 1, "Price");
//        stocksFlexTable.setText(0, 2, "Change");
//        stocksFlexTable.setText(0, 3, "Remove");

        // Add styles to elements in the stock list table.
        stocksFlexTable.setCellPadding(6);

        // Add styles to elements in the stock list table.
        stocksFlexTable.getRowFormatter().addStyleName(0, "watchListHeader");
        stocksFlexTable.addStyleName("watchList");
        stocksFlexTable.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(0, 3, "watchListRemoveColumn");

        // Assemble the Add Stock panel
        addPanel.add(newSymbolTextBox);
        addPanel.add(addStockButton);
        addPanel.addStyleName("addPanel");

        // Assemble Add Stock panel.
        addPanel.add(newSymbolTextBox);
        addPanel.add(addStockButton);
        addPanel.addStyleName("addPanel");
        // Assemble Main panel.
        errorMsgLabel.setStyleName("errorMessage");
        errorMsgLabel.setVisible(false);

        mainPanel.add(errorMsgLabel);

        //
        myMainPanel.add(gridResult);
        myMainPanel.add(showResultButton);
        showResultButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showResult();
            }
        });
        // Assemble Main panel.
        mainPanel.add(stocksFlexTable);
        mainPanel.add(addPanel);
        mainPanel.add(lastUpdatedLabel);
        // Associate the Main panel with the HTML host page.
        RootPanel.get("tutorialExample").add(mainPanel);
        RootPanel.get("tutorialExample").add(myMainPanel);

        // Move cursor focus to the input box.
        newSymbolTextBox.setFocus(true);

        // Listen for mouse events on the Add button.
        addStockButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                addValueValidation();
            }
        });

        // Listen for keyboard events in the input box.
        newSymbolTextBox.addKeyDownHandler(new KeyDownHandler() {
            public void onKeyDown(KeyDownEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
                    addValueValidation();
                }
            }
        });
        // Setup timer to refresh list automatically.
        Timer refreshTimer = new Timer() {
            @Override
            public void run() {
                refreshWatchList();
            }
        };
        refreshTimer.scheduleRepeating(REFRESH_INTERVAL);

    }

    private void showResult() {
        /*
        RungeKuttaImpl method = RungeKuttaTest.testFullModel();
        gridResult.setText(0, 0, "Time");
        gridResult.setText(0, 1, "Tetta");
        gridResult.setText(0, 2, "Omega");
        gridResult.setText(0, 3, "Eps");

        // Add styles to elements in the stock list table.
        gridResult.setCellPadding(6);

        // Add styles to elements in the stock list table.
        gridResult.getRowFormatter().addStyleName(0, "watchListHeader");
        gridResult.addStyleName("watchList");
        gridResult.getCellFormatter().addStyleName(0, 1, "watchListNumericColumn");
        gridResult.getCellFormatter().addStyleName(0, 2, "watchListNumericColumn");
        gridResult.getCellFormatter().addStyleName(0, 3, "watchListNumericColumn");
        gridResult.getCellFormatter().addStyleName(0, 4, "watchListNumericColumn");

        System.out.println("\nTime");
        for (int i=0; i< method.Z.size(); i++){
            gridResult.setText(i, 1,  method.Z.get(0).get(i).toString());
            gridResult.setText(i, 2, String.valueOf(BasicCalculationOperation.convertRadiansToDegrees((double)method.Z.get(1).get(i))));
            gridResult.setText(i, 3,  method.Z.get(2).get(i).toString());
            gridResult.setText(i, 4, String.valueOf(BasicCalculationOperation.convertRadiansToDegrees((double)method.Z.get(3).get(i))));
        }
        */
    }

    private void refreshWatchListOld() {
        // TODO Auto-generated method stub
        final double MAX_PRICE = 100.0; // $100.00
        final double MAX_PRICE_CHANGE = 0.02; // +/- 2%

        StockPrice[] prices = new StockPrice[stocks.size()];
        for (int i = 0; i < stocks.size(); i++) {
            double price = Random.nextDouble() * MAX_PRICE;
            double change = price * MAX_PRICE_CHANGE
                    * (Random.nextDouble() * 2.0 - 1.0);

            prices[i] = new StockPrice(stocks.get(i), price, change);
        }

        updateTable_(prices);
    }
    private void refreshWatchList(){
        if (stocks.size() == 0) {
            return;
        }

        String url = JSON_URL;

        // Append watch list stock symbols to query URL.
        Iterator<String> iter = stocks.iterator();
        while (iter.hasNext()) {
            url += iter.next();
            if (iter.hasNext()) {
                url += "+";
            }
        }

        url = URL.encode(url);
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
        // TODO Send request to server and handle errors
        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    displayError("Couldn't retrieve JSON");
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        updateTable(JsonUtils.<JsArray<StockData>>safeEval(response.getText()));
                    } else {
                        displayError("Couldn't retrieve JSON (" + response.getStatusText()
                                + ")");
                    }
                }
            });
        } catch (RequestException e) {
            displayError("Couldn't retrieve JSON");
        }
    }
    private void refreshWatchList_() {
        // Initialize the service proxy.
        if (stockPriceSvc == null) {
            stockPriceSvc = GWT.create(StockPriceService.class);
        }

        // Set up the callback object.
        AsyncCallback<StockPrice[]> callback = new AsyncCallback<StockPrice[]>() {
            public void onFailure(Throwable caught) {
                // TODO: Do something with errors.
                // If the stock code is in the list of delisted codes, display an error message.
                String details = caught.getMessage();
                if (caught instanceof DelistedException) {
                    details = "Company '" + ((DelistedException)caught).getSymbol() + "' was delisted";
                }

                errorMsgLabel.setText("Error: " + details);
                errorMsgLabel.setVisible(true);
            }

            public void onSuccess(StockPrice[] result) {
                updateTable_(result);
            }
        };

        // Make the call to the stock price service.
        stockPriceSvc.getPrices(stocks.toArray(new String[0]), callback);
    }
    /**
     * Update the Price and Change fields all the rows in the stock table.
     *          Stock data for all rows.
     */
    private void updateTable_(StockPrice[] prices) {
        for (int i=0; i < prices.length; i++) {
            updateTable_(prices[i]);
        }

        // Display timestamp showing last refresh.
        lastUpdatedLabel.setText("Last update : " +
                DateTimeFormat.getMediumDateTimeFormat().format(new Date()));

        // Clear any errors.
        errorMsgLabel.setVisible(false);
    }
    private void updateTable(JsArray<StockData> prices) {
        for (int i=0; i < prices.length(); i++) {
            updateTable(prices.get(i));
        }
        // Display timestamp showing last refresh.
        lastUpdatedLabel.setText(messages.lastUpdate(new Date()));
//        lastUpdatedLabel.setText("Last update : " +
//                DateTimeFormat.getMediumDateTimeFormat().format(new Date()));

        // Clear any errors.
        errorMsgLabel.setVisible(false);
    }
    /**
     * Update a single row in the stock table.
     *
     * @param price Stock data for a single row.
     */
    private void updateTable_(StockPrice price) {
        // Make sure the stock is still in the stock table.
        if (!stocks.contains(price.getSymbol())) {
            return;
        }

        int row = stocks.indexOf(price.getSymbol()) + 1;

        // Format the data in the Price and Change fields.
        String priceText = NumberFormat.getFormat("#,##0.00").format(
                price.getPrice());
        NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
        String changeText = changeFormat.format(price.getChange());
        String changePercentText = changeFormat.format(price.getChangePercent());

        // Populate the Price and Change fields with new data.
        stocksFlexTable.setText(row, 1, priceText);
        Label changeWidget = (Label)stocksFlexTable.getWidget(row, 2);
        changeWidget.setText(changeText + " (" + changePercentText + "%)");
        // Change the color of text in the Change field based on its value.
        String changeStyleName = "noChange";
        if (price.getChangePercent() < -0.1f) {
            changeStyleName = "negativeChange";
        }
        else if (price.getChangePercent() > 0.1f) {
            changeStyleName = "positiveChange";
        }

        changeWidget.setStyleName(changeStyleName);
    }

    private void updateTable(StockData price) {
        // Make sure the stock is still in the stock table.
        if (!stocks.contains(price.getSymbol())) {
            return;
        }

        int row = stocks.indexOf(price.getSymbol()) + 1;

        // Format the data in the Price and Change fields.
        String priceText = NumberFormat.getFormat("#,##0.00").format(
                price.getPrice());
        NumberFormat changeFormat = NumberFormat.getFormat("+#,##0.00;-#,##0.00");
        String changeText = changeFormat.format(price.getChange());
        String changePercentText = changeFormat.format(price.getChangePercent());

        // Populate the Price and Change fields with new data.
        stocksFlexTable.setText(row, 1, priceText);
        Label changeWidget = (Label)stocksFlexTable.getWidget(row, 2);
        changeWidget.setText(changeText + " (" + changePercentText + "%)");
        // Change the color of text in the Change field based on its value.
        String changeStyleName = "noChange";
        if (price.getChangePercent() < -0.1f) {
            changeStyleName = "negativeChange";
        }
        else if (price.getChangePercent() > 0.1f) {
            changeStyleName = "positiveChange";
        }

        changeWidget.setStyleName(changeStyleName);
    }

    private void addValueValidation() {
        final String symbol = newSymbolTextBox.getText().toUpperCase().trim();
        newSymbolTextBox.setFocus(true);

        // Stock code must be between 1 and 10 chars that are numbers, letters, or dots.
        if (!symbol.matches("^[0-9A-Z\\.]{1,10}$")) {
            Window.alert("'" + symbol + "' is not a valid symbol.");
            newSymbolTextBox.selectAll();
            return;
        }

        newSymbolTextBox.setText("");

        // TODO Don't add the stock if it's already in the table.
        if (stocks.contains(symbol))
            return;
        // TODO Add the stock to the table
        // Add the stock to the table.
        int row = stocksFlexTable.getRowCount();
        stocks.add(symbol);
        stocksFlexTable.setText(row, 0, symbol);
        stocksFlexTable.setWidget(row, 2, new Label());
        stocksFlexTable.getCellFormatter().addStyleName(row, 1, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(row, 2, "watchListNumericColumn");
        stocksFlexTable.getCellFormatter().addStyleName(row, 3, "watchListRemoveColumn");
        // TODO Add a button to remove this stock from the table.
        Button removeStockButton = new Button("x");
        removeStockButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                int removedIndex = stocks.indexOf(symbol);
                stocks.remove(removedIndex);
                stocksFlexTable.removeRow(removedIndex + 1);
            }
        });
        stocksFlexTable.setWidget(row, 3, removeStockButton);
        // TODO Get the stock price.v
    }
    /**
     * If can't get JSON, display error message.
     * @param error
     */
    private void displayError(String error) {
        errorMsgLabel.setText("Error: " + error);
        errorMsgLabel.setVisible(true);
    }



    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
