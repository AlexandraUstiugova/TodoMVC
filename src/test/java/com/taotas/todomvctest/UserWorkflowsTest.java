package com.taotas.todomvctest;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.jsReturnsValue;

public class UserWorkflowsTest {

    @Test
    void todosCommonManagement() {
        open("https://todomvc4tasj.herokuapp.com/");
        Wait().until(jsReturnsValue(
                "return $._data($('#clear-completed').get(0), 'events')" +
                        ".hasOwnProperty('click')"));

        // Create
        $("#new-todo").append("a").pressEnter();
        $("#new-todo").append("b").pressEnter();
        $("#new-todo").append("c").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("a", "b", "c"));
        $("#todo-count>strong").shouldHave(exactText("3"));

        // Edit
        $$("#todo-list>li").findBy(exactText("b")).doubleClick();
        $$("#todo-list>li").findBy(cssClass("editing"))
                .find(".edit").append(" edited").pressEnter();

        // Complete and Clear
        $$("#todo-list>li").findBy(exactText("b edited"))
                .find(".toggle").click();
        $("#clear-completed").click();
        $$("#todo-list>li").shouldHave(exactTexts("a", "c"));

        // Cancel edit
        $$("#todo-list>li").findBy(exactText("c")).doubleClick();
        $$("#todo-list>li").findBy(cssClass("editing"))
                .find(".edit").append(" to be canceled").pressEscape();

        // Delete
        $$("#todo-list>li").findBy(exactText("c")).hover()
                .find(".destroy").click();
        $$("#todo-list>li").shouldHave(exactTexts("a"));
        $("#todo-count>strong").shouldHave(exactText("1"));
    }
}
