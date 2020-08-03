package com.taotas.poctest;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

public class TodoMvcPocTest {

    @Test
    void checkingBasicFunctionality() {

        Configuration.timeout = 6000;

        open("https://todomvc4tasj.herokuapp.com/");

        Wait().until(jsReturnsValue
                ("return $._data($('#new-todo').get(0), 'events')" +
                        ".hasOwnProperty('keyup')"));
        $("#new-todo").append("a").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("a"));

        $("#todo-list>li:nth-of-type(1)").doubleClick();
        $("#todo-list>li:nth-of-type(1) .edit").append("b").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("ab"));

        $("#todo-list>li:nth-of-type(1) .destroy").click();
    }
}
