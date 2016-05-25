package com.spaceApplication.client.space.html;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;

/**
 * Created by Кристина on 16.05.2016.
 */
public class UIConsts {
    public static final String headerStyle = "section-header";
    public static final String testDataStyle = "test-data";
    public static final String imageStyle = "img-responsive center-block img_pdng";
    public static final String buttonStyle = "btn btn-primary btn-lg";
    public static final String squareButtonStyle = "square-btn btn-primary btn-lg";
    public static final String text_muted= "lead text-muted";
    public static final String info_popup= "info-popup";
    public static final String appDivId = "content_container";
    public static String introH2Id = "intro_h2";
    public static String introParagrapgId = "intro_p";
    public static String fileName = "report_result.xls";

    public static String degrees = " градусов";
    public static String A_descr = "Большая полуось орбиты";
    public static String eps_desc = "Истинная аномалия Земли";
    public static String tetts_descr = "Угол отклонения троса от вертикали";
    public static String omega_descr = "Угловая скорость";

    public static String epsilon = SafeHtmlUtils.htmlEscape("υ, градусов");
    public static String tetta = SafeHtmlUtils.htmlEscape("θ, градусов");
    public static String omega = SafeHtmlUtils.htmlEscape("ω, рад/c");

    public static double toKilo = 1000;

}
