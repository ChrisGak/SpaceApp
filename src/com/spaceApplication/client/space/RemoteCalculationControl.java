package com.spaceApplication.client.space;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.spaceApplication.client.space.html.UIConsts;
import com.spaceApplication.client.space.model.CableSystemModel;
import com.spaceApplication.client.space.model.RungeKuttaResult;
import org.moxieapps.gwt.highcharts.client.Chart;
import org.moxieapps.gwt.highcharts.client.Legend;
import org.moxieapps.gwt.highcharts.client.Series;

/**
 * Created by Кристина on 16.05.2016.
 */
public class RemoteCalculationControl {

    // create an instance of the service proxy class by calling GWT.create(Class).
    private SpaceApplicationServiceAsync spaceApplicationServiceAsync = GWT.create(SpaceApplicationService.class);
    private static VerticalPanel downloadImagePanel;
    //private static CableSystemModel defaultTestModel = new CableSystemModel(20, 40 , 1000, 1000000, 0, 0, 0, 0.0167, 0.5,  1000, 10.0, 20.0, 0.01);
    private static CableSystemModel defaultTestModel = new CableSystemModel(20, 20 , 1000, 1000000, 0.1, 0, 0, 0.0167, 0.5, 1000, 10.0, 20.0, 0.01);


    public RemoteCalculationControl(){
        spaceApplicationServiceAsync = GWT.create(SpaceApplicationService.class);
        initFileDownloadAnchor();
    }

    public void callRPCTest( AsyncCallback<RungeKuttaResult> calculationCallback){
        if (spaceApplicationServiceAsync==null)
            spaceApplicationServiceAsync = GWT.create(SpaceApplicationService.class);


        spaceApplicationServiceAsync.getCalculationResult(defaultTestModel, calculationCallback);
    }

    public void callRemoteCalculation(AsyncCallback<RungeKuttaResult> calculationCallback, CableSystemModel model){
        if (spaceApplicationServiceAsync==null)
            spaceApplicationServiceAsync = GWT.create(SpaceApplicationService.class);

        spaceApplicationServiceAsync.getCalculationResult(model, calculationCallback);
    }

    private void initFileDownloadAnchor() {
        downloadImagePanel = new VerticalPanel();
        Image downloadImage = new Image("images/box.jpg");
        downloadImage.setStyleName(UIConsts.imageStyle);
        downloadImage.setTitle("Скачать результаты моделирования");
        downloadImagePanel.add(downloadImage);
        Anchor downloadHref = new Anchor();
        downloadHref.setHref(UIConsts.fileName);
        downloadHref.setText("Скачать результаты моделирования в формате Excel-файла");
        downloadImagePanel.add(downloadHref);
    }

    public static Widget createAllResultPlots(RungeKuttaResult results, CableSystemModel model){
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(createResultCharts(results));
        verticalPanel.add(createBigChart(results, model));

        horizontalPanel.add(verticalPanel);
        horizontalPanel.add(downloadImagePanel);

        return horizontalPanel;
    }

    public static Widget createAllResultPlotsTest(RungeKuttaResult results){
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        VerticalPanel inputDataVPanel = new VerticalPanel();
        HTML h1 = new HTML("<h1 class="+  UIConsts.headerStyle +">"+ "Параметры тросовой системы" +"</h1>");
        inputDataVPanel.add(h1);

        inputDataVPanel.add(new Label("Масса первого космического аппарата " + defaultTestModel.getM1() + " кг"));
        inputDataVPanel.add(new Label("Масса второго космического аппарата " + defaultTestModel.getM2()+ " кг"));
        inputDataVPanel.add(new Label("Длина троса " + defaultTestModel.getL() / 1000 + " км"));
        inputDataVPanel.add(new Label("Высота центра масс " + defaultTestModel.getH() /1000+ " км"));

        HTML h2 = new HTML("<h1 class="+ UIConsts.headerStyle +">"+ "Начальные параметры" +"</h1>");
        inputDataVPanel.add(h2);

        inputDataVPanel.add(new Label("Угол отклонения троса от вертикали " + defaultTestModel.getTetta() + " градусов"));
        inputDataVPanel.add(new Label("Истинная аномалия Земли " + defaultTestModel.getTetta() + " градусов"));
        inputDataVPanel.add(new Label("Угловая скорость " + defaultTestModel.getOmega() + " рад/c"));
        inputDataVPanel.add(new Label("Эксцентриситет " + defaultTestModel.getEx()));
        inputDataVPanel.add(new Label("Значение тока " + defaultTestModel.getI() + " А"));

        HTML h3 = new HTML("<h1 class="+UIConsts.headerStyle +">"+ "Параметры моделирования" +"</h1>");
        inputDataVPanel.add(h3);

        inputDataVPanel.add(new Label("Время интегрирования " + defaultTestModel.getMaxIter()  + " с" ));
        inputDataVPanel.add(new Label("Начальный шаг интегрирования " + defaultTestModel.getStep()+ " с" ));
        inputDataVPanel.add(new Label("Максимальный шаг " + defaultTestModel.getStepMax()+ " с" ));
        inputDataVPanel.add(new Label("Погрешность интегрирования " + defaultTestModel.getD()));

        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(inputDataVPanel);
        verticalPanel.add(createResultCharts(results));
        verticalPanel.add(createBigChart(results, defaultTestModel));

        horizontalPanel.add(verticalPanel);
        horizontalPanel.add(downloadImagePanel);

        return horizontalPanel;
    }

    public static Widget createResultPlots(RungeKuttaResult results){
        HorizontalPanel horizontalPanel = new HorizontalPanel();
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(createResultCharts(results));

        horizontalPanel.add(verticalPanel);
        horizontalPanel.add(downloadImagePanel);

        return horizontalPanel;
    }

    public static Widget createBigChart(RungeKuttaResult results, CableSystemModel model){
        VerticalPanel contentPanel = new VerticalPanel();
        Series XNB1,  XNB2 ,  YNB1 , YNB2;

        Chart chart = new Chart()
                .setType(Series.Type.LINE)
                .setChartTitleText("Траектория")
                .setLegend(new Legend()
                        .setAlign(Legend.Align.RIGHT)
                        .setBackgroundColor("#CCCCCC")
                        .setShadow(true)
                );

        XNB1 = chart.createSeries().setName("XNB1").setPoints(model.getXNB1Points(results.getTetta()));
        XNB2 = chart.createSeries().setName("XNB2").setPoints(model.getXNB2Points(results.getTetta()));
        YNB1 = chart.createSeries().setName("YNB1").setPoints(model.getYNB1Points(results.getTetta()));
        YNB2 = chart.createSeries().setName("YNB2").setPoints(model.getYNB2Points(results.getTetta()));
        chart.addSeries(XNB1);
        chart.addSeries(XNB2);
        chart.addSeries(YNB1);
        chart.addSeries(YNB2);
        chart.getYAxis().setAxisTitleText("N");
        chart.setShowAxes(true);
        contentPanel.add(chart);

        return  contentPanel;
    }

    public static Widget createResultCharts(RungeKuttaResult results){
        VerticalPanel contentPanel = new VerticalPanel();

        HTML headerOfContent = new HTML("<h1 class="+ UIConsts.headerStyle+">"+ "Результаты моделирования"+"</h1>");
        contentPanel.add(headerOfContent);

        Chart chart = new Chart()
                .setType(Series.Type.LINE)
                .setChartTitleText("Изменение высоты центра масс над поверхностью Земли")
                .setLegend(new Legend()
                        .setAlign(Legend.Align.RIGHT)
                        .setBackgroundColor("#CCCCCC")
                        .setShadow(true)
                );
        Series pointsSeries = chart.createSeries().setName("R, км").setPoints(results.getHeightPoints());
        chart.addSeries(pointsSeries);
        chart.getYAxis().setAxisTitleText("N");
        contentPanel.add(chart);

        chart = new Chart()
                .setType(Series.Type.LINE)
                .setChartTitleText(UIConsts.tetts_descr)
                .setLegend(new Legend()
                        .setAlign(Legend.Align.RIGHT)
                        .setBackgroundColor("#CCCCCC")
                        .setShadow(true)
                );

        pointsSeries = chart.createSeries().setName(UIConsts.tetta).setPoints(results.getTettaPoints());
        chart.addSeries(pointsSeries);
        chart.getYAxis().setAxisTitleText("N");
        contentPanel.add(chart);

        chart = new Chart()
                .setType(Series.Type.LINE)
                .setChartTitleText(UIConsts.omega_descr)
                .setLegend(new Legend()
                        .setAlign(Legend.Align.RIGHT)
                        .setBackgroundColor("#CCCCCC")
                        .setShadow(true)
                );

        pointsSeries = chart.createSeries().setName(UIConsts.omega).setPoints(results.getOmegaPoints());
        chart.addSeries(pointsSeries);
        chart.getYAxis().setAxisTitleText("N");
        contentPanel.add(chart);

        chart = new Chart()
                .setType(Series.Type.LINE)
                .setChartTitleText(UIConsts.eps_desc)
                .setLegend(new Legend()
                        .setAlign(Legend.Align.RIGHT)
                        .setBackgroundColor("#CCCCCC")
                        .setShadow(true)
                );

        pointsSeries = chart.createSeries().setName(UIConsts.epsilon).setPoints(results.getEpsPoints());
        chart.addSeries(pointsSeries);
        chart.getYAxis().setAxisTitleText("N");
        contentPanel.add(chart);

        Chart chartAnother = new Chart()
                .setType(Series.Type.LINE)
                .setChartTitleText("Эксцентриситет")
                .setLegend(new Legend()
                        .setAlign(Legend.Align.RIGHT)
                        .setBackgroundColor("#CCCCCC")
                        .setShadow(true)
                );

        pointsSeries = chart.createSeries().setName("e").setPoints(results.getExPoints());
        chartAnother.addSeries(pointsSeries);
        chart.getYAxis().setAxisTitleText("N");
        contentPanel.add(chartAnother);

        chart = new Chart()
                .setType(Series.Type.LINE)
                .setChartTitleText(UIConsts.A_descr)
                .setLegend(new Legend()
                        .setAlign(Legend.Align.RIGHT)
                        .setBackgroundColor("#CCCCCC")
                        .setShadow(true)
                );

        pointsSeries = chart.createSeries().setName("A, км").setPoints(results.getAPoints());
        chart.addSeries(pointsSeries);
        chart.getYAxis().setAxisTitleText("N");
        contentPanel.add(chart);

        return  contentPanel;
    }
}
