package com.taotas.todomvctest;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
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
        todos.shouldHave(exactTexts("a", "b", "c"));
        itemsLeftCounter.shouldHave(exactText("3"));

        // Edit
        startEdit("b", " edited").pressEnter();

        // Complete and Clear
        complete("b edited");
        $("#clear-completed").click();
        todos.shouldHave(exactTexts("a", "c"));

        // Cancel edit
        startEdit("c", " to be canceled").pressEscape();

        delete("c");
        todos.shouldHave(exactTexts("a"));
        itemsLeftCounter.shouldHave(exactText("1"));
    }

    private ElementsCollection todos = $$("#todo-list>li");
    private SelenideElement itemsLeftCounter = $("#todo-count>strong");

    private SelenideElement startEdit(String text, String textToAdd) {
        todos.findBy(exactText(text)).doubleClick();
        return todos.findBy(cssClass("editing")).find(".edit").append(textToAdd);
    }

    private void complete(String text) {
        todos.findBy(exactText(text)).find(".toggle").click();
    }

    private void delete(String text) {
        todos.findBy(exactText(text)).hover().find(".destroy").click();
    }
}


