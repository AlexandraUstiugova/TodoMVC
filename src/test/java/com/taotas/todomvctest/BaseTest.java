package com.taotas.todomvctest;

import com.codeborne.selenide.Configuration;

public class BaseTest {
    {
        Configuration.baseUrl = System.getProperty(
                "selenide.baseUrl", "https://todomvc4tasj.herokuapp.com");
        Configuration.timeout = Long.parseLong(System.getProperty(
                "Selenide.timeout", "6000"));

        Configuration.fastSetValue = true;
    }
}
