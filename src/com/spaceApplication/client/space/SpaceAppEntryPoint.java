package com.spaceApplication.client.space;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.spaceApplication.client.exception.SpaceModelException;
import com.spaceApplication.client.internationalization.SpaceAppConstants;
import com.spaceApplication.client.internationalization.SpaceAppMessages;
import com.spaceApplication.client.space.html.UIConsts;
import com.spaceApplication.client.space.model.RungeKuttaResult;

import java.util.logging.Logger;

/**
 * Created by Кристина on 28.02.2016.
 */
public class SpaceAppEntryPoint implements EntryPoint {

    private static final Logger log = Logger.getLogger(String.valueOf(SpaceAppEntryPoint.class));

    // create an instance of the service proxy class by calling GWT.create(Class).
    private SpaceApplicationServiceAsync spaceApplicationServiceAsync = GWT.create(SpaceApplicationService.class);
    private HorizontalPanel mainPanel = new HorizontalPanel();
    private VerticalPanel contentPanel = new VerticalPanel();
    private MenuBar modelCreationMenu;

    private SpaceAppConstants constants = GWT.create(SpaceAppConstants.class);
    private SpaceAppMessages messages = GWT.create(SpaceAppMessages.class);
    //
    private Button testButton = new Button(constants.testCalculation());
    private Button modelingButton = new Button(constants.modeling());
    private Button downloadSampleButton = new Button(constants.downloadSample());

    private Label errorMsgLabel = new Label(constants.testExample());
    String appDivId = UIConsts.appDivId;
    String introDivId = "intro";
    String introH2Id = UIConsts.introH2Id;
    String introParagrapgId = UIConsts.introParagrapgId;

    public void onModuleLoad() {
        Window.setTitle(constants.modeling());

        testButton = Button.wrap(Document.get().getElementById("testExample"));
        modelingButton = Button.wrap(Document.get().getElementById("modeling"));

        testButton.addClickHandler(testExampleHandler);
        modelingButton.addClickHandler(modelingHandler);
        downloadSampleButton.setStyleName(UIConsts.buttonStyle);
        downloadSampleButton.addClickHandler(downloadSampleHandler);
        initParameterCtrl();
    }

    private void clearContentDiv() {
        RootPanel.get(appDivId).clear(true);
        RootPanel.get(appDivId).clear();
        mainPanel.clear();
    }

    // Set up the callback object.
    AsyncCallback<RungeKuttaResult> calculationCallback = new AsyncCallback<RungeKuttaResult>() {
        public void onFailure(Throwable caught) {
            String details = caught.getMessage();
            if (caught instanceof SpaceModelException) {
                details = "Model has exception:  '" + ((SpaceModelException)caught).getCause();
            }

            errorMsgLabel.setText("Error: " + details);
            errorMsgLabel.setText(messages.getErrorMessage(caught.getMessage()));
            errorMsgLabel.setVisible(true);

            mainPanel.add(errorMsgLabel);
            RootPanel.get(appDivId).add(mainPanel);
        }

        @Override
        public void onSuccess(RungeKuttaResult result) {
            mainPanel.add(RemoteCalculationControl.createAllResultPlotsTest(result));
            RootPanel.get(appDivId).add(mainPanel);
        }
    };

    private void callRPCTest(){
        RemoteCalculationControl remoteCalculationControl = new RemoteCalculationControl();
        remoteCalculationControl.callRPCTest(calculationCallback);
    }

    private void initParameterCtrl(){
        final ParameterCtrl parameterCtrl = new ParameterCtrl();

        modelCreationMenu = new MenuBar();
        Command createParameterCtrlCommand = new Command() {
            @Override
            public void execute() {
                mainPanel.clear();
                RootPanel.get(appDivId).clear(true);
                mainPanel.add(parameterCtrl.initWidget());
                RootPanel.get(appDivId).add(mainPanel);
            }
        };

        Command downloadModelCommand = new Command() {
            @Override
            public void execute() {
                mainPanel.clear();

                final FormPanel form = new FormPanel();
                form.setAction("/myFormHandler");
                form.setEncoding(FormPanel.ENCODING_MULTIPART);
                form.setMethod(FormPanel.METHOD_POST);
                form.setTitle("Загрузите файл");

                Image xlsImage = new Image("images/excel-xls-icon.png");
                xlsImage.setStyleName(UIConsts.imageStyle);
                xlsImage.setTitle("Microsoft Office Excel");

                VerticalPanel vpanel = new VerticalPanel();
                form.setWidget(vpanel);

                final FileUpload upload = new FileUpload();
                upload.setStyleName(UIConsts.buttonStyle);
                upload.setName("Файл");
                Button submit=new Button("Загрузить");
                submit.setStyleName(UIConsts.buttonStyle);
                vpanel.add(upload);
                vpanel.add(submit);
                final InfoPopup popup = new InfoPopup();
                popup.setStyleName(UIConsts.info_popup);

                popup.setPopupPositionAndShow(new PopupPanel.PositionCallback(){
                    public void setPosition(int offsetWidth, int offsetHeight) {
                        int left = (Window.getClientWidth() - offsetWidth) / 3;
                        int top = (Window.getClientHeight() - offsetHeight) / 3;
                        popup.setPopupPosition(left, top);
                    }
                });
                submit.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        popup.setInnerText("Файл загружается...");
                        form.submit();
                    }});

                form.addSubmitHandler(new FormPanel.SubmitHandler() {
                    public void onSubmit(FormPanel.SubmitEvent event) {
                        if (upload.getFilename().equals("")) {
                            popup.setInnerText("Выберите файл!!!");
                            event.cancel();
                        } else if (! upload.getFilename().contains(".xls")) {
                            popup.setInnerText("Поддерживаются файлы только формата xls");
                            event.cancel();
                        }
                    }});
                form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
                    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                        popup.setInnerText(event.getResults());
                    }});

                HorizontalPanel hPanel = new HorizontalPanel();
                hPanel.add(new VerticalPanel());
                hPanel.add(form);
                hPanel.add(new VerticalPanel());
                hPanel.add(xlsImage);

                RootPanel.get(appDivId).add(hPanel);
            }
        };

        MenuItem modelCreationDialog = new MenuItem(constants.modelCreation(), createParameterCtrlCommand);
        modelCreationDialog.setTitle(constants.modelCreationTitle());
        modelCreationDialog.setStyleName(UIConsts.squareButtonStyle);

        MenuItem modelDownloadingDialog = new MenuItem(constants.modelDownloading(), downloadModelCommand);
        modelDownloadingDialog.setTitle(constants.modelDownloadingTitle());
        modelDownloadingDialog.setStyleName(UIConsts.squareButtonStyle);

        modelCreationMenu.addItem(modelCreationDialog);
        modelCreationMenu.addSeparator();
        modelCreationMenu.addItem(modelDownloadingDialog);
    }

    ClickHandler testExampleHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            clearContentDiv();
            RootPanel.get(introH2Id).getElement().setInnerText(constants.testCalculation());
            RootPanel.get(introParagrapgId).getElement().setInnerText(constants.testCalculationInfo());
            callRPCTest();
        }
    };

    ClickHandler downloadSampleHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            clearContentDiv();
            //TODO add some code
        }
    };

    ClickHandler modelingHandler = new ClickHandler() {
        @Override
        public void onClick(ClickEvent event) {
            clearContentDiv();
            RootPanel.get(introH2Id).getElement().setInnerText(constants.modeling());
            RootPanel.get(introParagrapgId).getElement().setInnerText(constants.modelingInfo());

            VerticalPanel imgPanel = new VerticalPanel();
            Image image = new Image("images/Space-Elevator.jpg");
            imgPanel.add(image);

            mainPanel.add(imgPanel);
            mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            mainPanel.add(new InlineHTML("  "));
            mainPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
            mainPanel.add(modelCreationMenu);

            RootPanel.get(appDivId).add(mainPanel);
        }
    };

    private static class InfoPopup extends PopupPanel {
        private Label innerText = new Label();
        public InfoPopup() {
            // PopupPanel's constructor takes 'auto-hide' as its boolean
            // parameter. If this is set, the panel closes itself
            // automatically when the user clicks outside of it.
            super(true);

            // PopupPanel is a SimplePanel, so you have to set it's widget
            // property to whatever you want its contents to be.
            innerText = new Label("Click outside of this popup to close it");
            innerText.setStyleName(UIConsts.text_muted);
            setWidget(innerText);
        }

        public void setInnerText(String text){
            innerText.setText(text);
            int width = 300;
            int len = (((text.length()) < width) ? 200 : ((text.length() / width) *2) );
            this.setSize( width + "px", len + "px");
            this.setAnimationEnabled(true);
            this.setGlassEnabled(true);
            this.setVisible(true);
            this.center();
            this.show();
        }
    }

}
