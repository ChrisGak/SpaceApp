package com.spaceApplication.client.space;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.spaceApplication.client.exception.SpaceModelException;
import com.spaceApplication.client.internationalization.SpaceAppConstants;
import com.spaceApplication.client.internationalization.SpaceAppMessages;
import com.spaceApplication.client.space.html.Slider;
import com.spaceApplication.client.space.html.UIConsts;
import com.spaceApplication.client.space.model.CableSystemModel;
import com.spaceApplication.client.space.model.RungeKuttaResult;

/**
 * Created by Кристина on 14.05.2016.
 */
public class ParameterCtrl {
    private VerticalPanel mainPanel = new VerticalPanel();
    private VerticalPanel vPanel = new VerticalPanel();
    private FlexTable stocksFlexTable = new FlexTable();
    private HorizontalPanel inputPanel = new HorizontalPanel();
    private TextBox newSymbolTextBox = new TextBox();
    private Button createModelButton = new Button("Создать модель");
    private Button startModelingButton = new Button("Начать моделирование");
    private Button executeCalculationButton = new Button("Приступить к расчету");
    private Slider m1, m2, I, L, H, tetta, omega, eps, ex;
    private TextBox m1_text, m2_text, I_text, L_text, H_text, tetta_text, omega_text, eps_text, ex_text;
    private Label m1_label, m2_label, I_label, L_label, H_label, tetta_label, omega_label, eps_label, ex_label,
            m1_label2, m2_label2, I_label2, L_label2, H_label2, tetta_label2, omega_label2, eps_label2, ex_label2,
            m1_max, m2_max, I_max, L_max, H_max, tetta_max, omega_max, eps_max, ex_max,
            maxIter_label, step_label, stepMax_label, D_label;

    private double toKilo = 1000;
    private IntegerBox maxIterCell;
    private DoubleBox stepCell, stepMaxCell, DCell;
    private  CableSystemModel cableSystemModel;
    private HTML headerOfContent;

    private SpaceAppConstants constants = GWT.create(SpaceAppConstants.class);
    private SpaceAppMessages messages = GWT.create(SpaceAppMessages.class);

    public ParameterCtrl(){
        initTextBoxed();
        initLabels();
        initSliders();
        createModelButton.setStyleName(UIConsts.buttonStyle);
        startModelingButton.addClickHandler(startModelingClickHandler);
        startModelingButton.setStyleName(UIConsts.buttonStyle);
        executeCalculationButton.addClickHandler(executeCalculationClickHandler);
        executeCalculationButton.setStyleName(UIConsts.buttonStyle);
    }

    public Widget initWidget(){

        HTML headerOfContent = new HTML("<h1 class="+ UIConsts.headerStyle+">"+ "Параметры тросовой системы" +"</h1>");
        vPanel.add(headerOfContent);

        int spacing = 300;
        m1.setMin(0);
        m1.setMax(200);
        m1.setValue(20);
        m1.setTitle("Масса первого космического аппарата");
        m1.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                m1_text.setText(String.valueOf(m1.getValue() + " кг"));
            }
        });
        HorizontalPanel m1_panel = new HorizontalPanel();
        m1_panel.setSpacing(spacing);
        m1_panel.add(m1_label);
        m1_label2.setText(String.valueOf(m1.getMin()));
        m1_panel.add(m1_label2);
        m1_panel.add(m1);
        m1_max.setText(String.valueOf(m1.getMax()));
        m1_panel.add(m1_max);
        m1_panel.add(m1_text);
        m1_text.setText(String.valueOf(m1.getValue() + " кг"));
        vPanel.add(m1_panel);

        m2.setMin(0);
        m2.setMax(200.0);
        m2.setValue(40);
        m2.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                m2_text.setText(String.valueOf(m2.getValue() + " кг"));
            }
        });
        m2.setTitle("Масса второго космического аппарата");
        HorizontalPanel m2_panel = new HorizontalPanel();
        m2_panel.setSpacing(spacing);
        m2_panel.add(m2_label);
        m2_label2.setText(String.valueOf(m2.getMin()));
        m2_panel.add(m2_label2);
        m2_panel.add(m2);
        m2_max.setText(String.valueOf(m2.getMax()));
        m2_panel.add(m2_max);
        m2_panel.add(m2_text);
        vPanel.add(m2_panel);
        m2_text.setText(String.valueOf(m2.getValue() + " кг"));

        L.setTitle("Длина троса");
        L.setMin(0.5);
        L.setMax(1);
        L.setValue(0.5);
        L.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                L_text.setText(String.valueOf(L.getValue() + " км"));
            }
        });
        HorizontalPanel L_panel = new HorizontalPanel();
        L_panel.setSpacing(spacing);
        L_panel.add(L_label);
        L_label2.setText(String.valueOf(L.getMin()));
        L_panel.add(L_label2);
        L_panel.add(L);
        L_max.setText(String.valueOf(L.getMax()));
        L_panel.add(L_max);
        L_panel.add(L_text);
        vPanel.add(L_panel);
        L_text.setText(String.valueOf(L.getValue() + " км"));

        H.setTitle("Высота центра масс системы");
        H.setMin(1000.0);
        H.setMax(1000.0);
        H.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                H_text.setText(String.valueOf(H.getValue() + " км"));
            }
        });
        HorizontalPanel H_panel = new HorizontalPanel();
        H_panel.setSpacing(spacing);
        H_panel.add(H_label);
        H_label2.setText(String.valueOf(H.getMin()));
        H_panel.add(H_label2);
        H_panel.add(H);
        H_max.setText(String.valueOf(H.getMax()));
        H_panel.add(H_max);
        H_panel.add(H_text);
        vPanel.add(H_panel);
        H_text.setText(String.valueOf(H.getValue() + " км"));

        HTML headerOfContent2 = new HTML("<h1 class="+ UIConsts.headerStyle+">"+ "Начальные параметры" +"</h1>");
        vPanel.add(headerOfContent2);

        tetta.setTitle(UIConsts.tetts_descr);
        tetta.setMin(0);
        tetta.setMax(360);
        tetta.setValue(0);
        tetta.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                tetta_text.setText(String.valueOf(tetta.getValue())+ UIConsts.degrees);
            }
        });
        HorizontalPanel tetta_panel = new HorizontalPanel();
        tetta_panel.setSpacing(spacing);
        tetta_panel.add(tetta_label);
        tetta_label2.setText(String.valueOf(tetta.getMin()));
        tetta_panel.add(tetta_label2);
        tetta_panel.add(tetta);
        tetta_max.setText(String.valueOf(tetta.getMax()));
        tetta_panel.add(tetta_max);
        tetta_panel.add(tetta_text);
        vPanel.add(tetta_panel);
        tetta_text.setText(String.valueOf(tetta.getValue())+ UIConsts.degrees);

        eps.setTitle(UIConsts.eps_desc);
        eps.setMin(0);
        eps.setMax(360);
        eps.setValue(0);
        eps.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                eps_text.setText(String.valueOf(eps.getValue())+ UIConsts.degrees);
            }
        });
        HorizontalPanel eps_panel = new HorizontalPanel();
        eps_panel.setSpacing(spacing);
        eps_panel.add(eps_label);
        eps_label2.setText(String.valueOf(eps.getMin()));
        eps_panel.add(eps_label2);
        eps_panel.add(eps);
        eps_max.setText(String.valueOf(eps.getMax()));
        eps_panel.add(eps_max);
        eps_panel.add(eps_text);
        vPanel.add(eps_panel);
        eps_text.setText(String.valueOf(eps.getValue())+ UIConsts.degrees);

        omega.setTitle("Угловая скорость");
        omega.setMin(0);
        omega.setMax(360);
        omega.setValue(0);
        omega.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                omega_text.setText(String.valueOf(omega.getValue()) + UIConsts.omega);
            }
        });
        HorizontalPanel omega_panel = new HorizontalPanel();
        omega_panel.setSpacing(spacing);
        omega_panel.add(omega_label);
        omega_label2.setText(String.valueOf(omega.getMin()));
        omega_panel.add(omega_label2);
        omega_panel.add(omega);
        omega_max.setText(String.valueOf(omega.getMax()));
        omega_panel.add(omega_max);
        omega_panel.add(omega_text);
        vPanel.add(omega_panel);
        omega_text.setText(String.valueOf(omega.getValue()));

        ex.setTitle("Эксцентриситет орбиты");
        ex.setMin(0.0167);
        ex.setMax(0.0167);
        ex.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                ex_text.setText(String.valueOf(ex.getValue()));
            }
        });
        HorizontalPanel ex_panel = new HorizontalPanel();
        ex_panel.setSpacing(spacing);
        ex_panel.add(ex_label);
        ex_label2.setText(String.valueOf(ex.getMin()));
        ex_panel.add(ex_label2);
        ex_panel.add(ex);
        ex_max.setText(String.valueOf(ex.getMax()));
        ex_panel.add(ex_max);
        ex_panel.add(ex_text);
        vPanel.add(ex_panel);
        ex_text.setText(String.valueOf(ex.getValue()));

        I.setTitle("Значение тока");
        I.setMin(-10);
        I.setMax(10);
        I.setValue(5);
        I.addChangeHandler(new ChangeHandler() {
            @Override
            public void onChange(ChangeEvent event) {
                I_text.setText(String.valueOf(I.getValue() + " А"));
            }
        });
        HorizontalPanel I_panel = new HorizontalPanel();
        I_panel.setSpacing(spacing);
        I_panel.add(I_label);
        I_label2.setText(String.valueOf(I.getMin()));
        I_panel.add(I_label2);
        I_panel.add(I);
        I_max.setText(String.valueOf(I.getMax()));
        I_panel.add(I_max);
        I_panel.add(I_text);
        vPanel.add(I_panel);
        I_text.setText(String.valueOf(I.getValue() + " А"));

        vPanel.add(createModelButton);
        createModelButton.addClickHandler(createModelButtonClickHandler);
        vPanel.setStyleName(UIConsts.text_muted);

        return vPanel;
    }

    private void initLabels() {
        int width = 400, height = 30;
        m1_label = new Label("Масса первого космического аппарата");
        m2_label = new Label("Масса второго космического аппарата");
        I_label = new Label("Значение тока");
        L_label = new Label("Длина троса");
        H_label = new Label("Высота центра масс системы");
        tetta_label = new Label("Угол отклонения троса от вертикали");
        omega_label = new Label("Угловая скорость");
        eps_label = new Label("Истинная аномалия Земли");
        ex_label = new Label("Эксцентриситет");

        maxIter_label = new Label("Время интегрирования, c");
        step_label = new Label("Начальный шаг интегрирования, c");
        stepMax_label = new Label("Максимальный шаг, c");
        D_label = new Label("Погрешность интегрирования");
        maxIter_label.setPixelSize(width, height);
        step_label.setPixelSize(width, height);
        stepMax_label.setPixelSize(width, height);
        D_label.setPixelSize(width, height);

        m1_label.setPixelSize(width, height);
        m2_label.setPixelSize(width, height);
        I_label.setPixelSize(width, height);
        L_label.setPixelSize(width, height);
        H_label.setPixelSize(width, height);
        tetta_label.setPixelSize(width,height);
        omega_label.setPixelSize(width, height);
        eps_label.setPixelSize(width, height);
        ex_label.setPixelSize(width, height);

        String label_width = "50px";
        m1_label2 = new Label();
        m1_label2.setWidth(label_width);
        m2_label2 = new Label();
        m2_label2.setWidth(label_width);
        I_label2 = new Label();
        I_label2.setWidth(label_width);
        L_label2 = new Label();
        L_label2.setWidth(label_width);
        H_label2 = new Label();
        H_label2.setWidth(label_width);
        tetta_label2 = new Label();
        tetta_label2.setWidth(label_width);
        omega_label2 = new Label();
        omega_label2.setWidth(label_width);
        eps_label2 = new Label();
        eps_label2.setWidth(label_width);
        ex_label2 = new Label();
        ex_label2.setWidth(label_width);

        m1_max = new Label();
        m1_max.setWidth(label_width);
        m2_max = new Label();
        m2_max.setWidth(label_width);
        I_max = new Label();
        I_max.setWidth(label_width);
        L_max = new Label();
        L_max.setWidth(label_width);
        H_max = new Label();
        H_max.setWidth(label_width);
        tetta_max = new Label();
        tetta_max.setWidth(label_width);
        omega_max = new Label();
        omega_max.setWidth(label_width);
        eps_max = new Label();
        eps_max.setWidth(label_width);
        ex_max = new Label();
        ex_max.setWidth(label_width);
    }

    private void initTextBoxed() {
        String width = "85px";
        m1_text = new TextBox();
        m1_text.setEnabled(false);
        m1_text.setWidth(width);
        m2_text = new TextBox();
        m2_text.setEnabled(false);
        m2_text.setWidth(width);
        L_text = new TextBox();
        L_text.setEnabled(false);
        L_text.setWidth(width);
        H_text = new TextBox();
        H_text.setEnabled(false);
        H_text.setWidth(width);
        I_text = new TextBox();
        I_text.setEnabled(false);
        I_text.setWidth(width);
        tetta_text = new TextBox();
        tetta_text.setEnabled(false);
        tetta_text.setWidth(width);
        omega_text = new TextBox();
        omega_text.setEnabled(false);
        omega_text.setWidth(width);
        eps_text = new TextBox();
        eps_text.setEnabled(false);
        eps_text.setWidth(width);
        ex_text = new TextBox();
        ex_text.setEnabled(false);
        ex_text.setWidth(width);
    }

    private void initSliders() {
        int width = 300, height = 30;

        m1 = new Slider();
        m1.setPixelSize(width, height);
        m2=new Slider();
        m2.setPixelSize(width, height);
        L=new Slider();
        L.setPixelSize(width, height);
        H= new Slider();
        H.setPixelSize(width, height);
        tetta = new Slider();
        tetta.setPixelSize(width, height);
        omega = new Slider();
        omega.setPixelSize(width, height);
        eps = new Slider();
        eps.setPixelSize(width, height);
        ex = new Slider();
        ex.setPixelSize(width, height);
        I = new Slider();
        I.setPixelSize(width, height);
    }

    ClickHandler createModelButtonClickHandler =  new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            clearVPanel();
            double m1_, m2_, L_, H_, tetta_, omega_, eps_, ex_, I_;
            m1_ = m1.getValue();
            m2_ = m2.getValue();
            L_ = L.getValue() * toKilo;
            H_=  H.getValue() * toKilo;
            tetta_ = tetta.getValue();
            omega_ = omega.getValue();
            eps_ = eps.getValue();
            ex_ = ex.getValue();
            I_=I.getValue();

            if (cableSystemModel != null){
                cableSystemModel = null;
            }
            cableSystemModel = new CableSystemModel(m1_, m2_, L_, H_, tetta_, omega_, eps_, ex_, I_);
            Label infoMessage;
            if (cableSystemModel != null)
            {
                vPanel.remove(createModelButton);
                headerOfContent = new HTML("<h2 class=" + UIConsts.headerStyle+">"+ "Модель создана успешно!" +"</h2>");
                vPanel.add(headerOfContent);
                vPanel.add(startModelingButton);

            }
            else{
                headerOfContent = new HTML("<h2 class=" +UIConsts.headerStyle+ ">"+ "Ошибка. Модель не создана!" +"</h2>");
                vPanel.add(headerOfContent);
            }
        }
    };
    ClickHandler startModelingClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            clearVPanel();

            RootPanel.get(UIConsts.introH2Id).getElement().setInnerText(constants.modeling());
            RootPanel.get(UIConsts.introParagrapgId).getElement().setInnerText(constants.testCalculationInfo());

            HTML headerOfContent = new HTML("<h1 class="+ UIConsts.headerStyle+">"+ "Параметры моделирования" +"</h1>");
            vPanel.add(headerOfContent);

            HorizontalPanel maxIterPanel = new HorizontalPanel();
            HorizontalPanel stepPanel = new HorizontalPanel();
            HorizontalPanel stepMaxPanel = new HorizontalPanel();
            HorizontalPanel DPanel = new HorizontalPanel();

            maxIterCell = new IntegerBox();
            maxIterCell.setValue(1000);
            maxIterPanel.add(maxIter_label);
            maxIterPanel.add(maxIterCell);

            stepCell = new DoubleBox();
            stepCell.setValue(10.0);
            stepPanel.add(step_label);
            stepPanel.add(stepCell);

            stepMaxCell = new DoubleBox();
            stepMaxCell.setValue(20.0);
            stepMaxPanel.add(stepMax_label);
            stepMaxPanel.add(stepMaxCell);

            DCell = new DoubleBox();
            DCell.setValue(0.001);
            DPanel.add(D_label);
            DPanel.add(DCell);

            vPanel.add(maxIterPanel);
            vPanel.add(stepPanel);
            vPanel.add(stepMaxPanel);
            vPanel.add(DPanel);
            vPanel.add(executeCalculationButton);
        }
    };

    // Set up the callback object.
    AsyncCallback<RungeKuttaResult> calculationCallback = new AsyncCallback<RungeKuttaResult>() {
        public void onFailure(Throwable caught) {
            String details = caught.getMessage();
            if (caught instanceof SpaceModelException) {
                details = "Model has exception:  '" + ((SpaceModelException)caught).getCause();
            }
            headerOfContent = new HTML("<h2 class=" +UIConsts.headerStyle+ ">"+ "Error: " + details +"</h2>");
            vPanel.add(headerOfContent);
            RootPanel.get(UIConsts.appDivId).add(vPanel);
        }

        @Override
        public void onSuccess(RungeKuttaResult result) {
            vPanel.clear();
            vPanel.add(RemoteCalculationControl.createAllResultPlots(result, cableSystemModel));
            RootPanel.get(UIConsts.appDivId).add(vPanel);
        }
    };

    ClickHandler executeCalculationClickHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            clearVPanel();
            RemoteCalculationControl calculationControl = new RemoteCalculationControl();

            int maxIter_;
            double step_, stepMax_, D_;
            maxIter_ = maxIterCell.getValue();
            step_ = stepCell.getValue();
            stepMax_ = stepMaxCell.getValue();
            D_ = DCell.getValue();

            if (cableSystemModel != null){
                cableSystemModel.setMaxIter(maxIter_);
                cableSystemModel.setStep(step_);
                cableSystemModel.setStepMax(stepMax_);
                cableSystemModel.setD(D_);
                calculationControl.callRemoteCalculation(calculationCallback, cableSystemModel);
            }
        }
    };

    private void clearVPanel() {
        vPanel.clear();
    }
}
