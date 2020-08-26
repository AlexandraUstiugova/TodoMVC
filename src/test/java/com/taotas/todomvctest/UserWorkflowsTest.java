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

        add("a", "b", "c");
        todoList.shouldHave(exactTexts("a", "b", "c"));
        todoCount.shouldHave(exactText("3"));

        edit("b", " edited");

        complete("b edited");
        clear();
        todoList.shouldHave(exactTexts("a", "c"));

        cancelEdit("c", " to be canceled");

        delete("c");
        todoList.shouldHave(exactTexts("a"));
        todoCount.shouldHave(exactText("1"));
    }

    private ElementsCollection todoList = $$("#todo-list>li");
    private SelenideElement todoCount = $("#todo-count>strong");

    private void add(String... taskTexts) {
        for (String text: taskTexts) {
            element("#new-todo").append(text).pressEnter();
        }
    }

    private void edit(String taskToChange, String addChange) {
        todoList.findBy(exactText(taskToChange)).doubleClick();
        todoList.findBy(cssClass("editing")).find(".edit").append(addChange)
                .pressEnter();
    }

    private void complete(String taskText) {
        todoList.findBy(exactText(taskText)).find(".toggle").click();
    }

    private void clear() {
        $("#clear-completed").click();
    }

    private void cancelEdit(String taskToChange, String addChange) {
        todoList.findBy(exactText(taskToChange)).doubleClick();
        todoList.findBy(cssClass("editing")).find(".edit").append(addChange)
                .pressEscape();
    }

    private void delete(String taskText) {
        todoList.findBy(exactText(taskText)).hover().find(".destroy").click();
    }
}
