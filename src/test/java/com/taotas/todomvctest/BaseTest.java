package com.taotas.todomvctest;

import com.codeborne.selenide.Configuration;

public class BaseTest {
    {
        Configuration.baseUrl = "https://todomvc4tasj.herokuapp.com";
        Configuration.fastSetValue = true;
    }
}
