package com.spaceApplication.client.internationalization;

import com.google.gwt.i18n.client.Constants;

/**
 * Created by Кристина on 28.02.2016.
 */
public interface SpaceAppConstants extends Constants {
    @DefaultStringValue("Space Application")
    String spaceApp();

    @DefaultStringValue("Start calculation")
    String startCalc();

    @DefaultStringValue("Тестовый пример")
    String testCalculation();

    @DefaultStringValue("Интегрирование методом Рунге-Кутты 4 порядка")
    String testCalculationInfo();

    @DefaultStringValue("Моделирование движения ЭДКТС")
    String modeling();

    @DefaultStringValue("Скачать образец файла")
    String downloadSample();

    @DefaultStringValue("Необходимо ввести начальные параметры, либо из файла, либо вручную")
    String modelingInfo();

    @DefaultStringValue("Создать модель")
    String modelCreation();

    @DefaultStringValue("Ввод начальных данных")
    String modelCreationTitle();

    @DefaultStringValue("Загрузить модель")
    String modelDownloading();

    @DefaultStringValue("Загрузить модель из xls-файла")
    String modelDownloadingTitle();

    @DefaultStringValue("Сохраненные результаты")
    String saved();

    @DefaultStringValue("Show")
    String show();

    @DefaultStringValue("Name")
    String name();

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

    @DefaultStringValue("Home")
    String home();

    @DefaultStringValue("Test Example")
    String testExample();

    @DefaultStringValue("Contacts")
    String contacts();
}
