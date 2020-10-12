package com.taotas.todomvctest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
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
        Configuration.fastSetValue = true;

        open("https://todomvc4tasj.herokuapp.com/");

        add("a", "b", "c");
        todosShouldBe("a", "b", "c");
        itemsLeftShouldBe(3);

        edit("b", "b edited");

        toggle("b edited");
        clearCompleted();
        todosShouldBe("a", "c");

        cancelEdit("c", "c to be canceled");

        delete("c");
        todosShouldBe("a");
        itemsLeftShouldBe(1);
    }

    private void open(String link) {
        Selenide.open(link);
        Wait().until(jsReturnsValue(
                "return $._data($('#clear-completed').get(0), 'events')" +
                        ".hasOwnProperty('click')"));
    }

    private void add(String... texts) {
        for (String text: texts) {
            element("#new-todo").append(text).pressEnter();
        }
    }

    private void todosShouldBe(String... texts) {
        $$("#todo-list>li").shouldHave(exactTexts(texts));
    }

    private void itemsLeftShouldBe(int number) {
        $("#todo-count>strong").shouldHave(exactText(
                Integer.toString(number)));
    }

    private SelenideElement edit(String oldText, String newText) {
        $$("#todo-list>li").findBy(exactText(oldText)).doubleClick();
        return $$("#todo-list>li").findBy(cssClass("editing")).
                find(".edit").setValue(newText).pressEnter();
    }

    private SelenideElement cancelEdit(String oldText, String newText) {
        $$("#todo-list>li").findBy(exactText(oldText)).doubleClick();
        return $$("#todo-list>li").findBy(cssClass("editing")).
                find(".edit").setValue(newText).pressEscape();
    }

    private void toggle(String text) {
        $$("#todo-list>li").findBy(exactText(text)).
                find(".toggle").click();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void delete(String text) {
        $$("#todo-list>li").findBy(exactText(text)).hover().
                find(".destroy").click();
    }
}


