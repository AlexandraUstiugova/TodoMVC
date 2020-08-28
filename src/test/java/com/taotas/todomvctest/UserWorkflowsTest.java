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
        todosShouldBe("a", "b", "c");
        itemsLeftShouldBe(3);

        // Edit
        changeTodo("b", " edited").pressEnter();

        // Complete and Clear
        findTodoByText("b edited").find(".toggle").click();
        $("#clear-completed").click();
        todosShouldBe("a", "c");

        // Cancel edit
        changeTodo("c", " to be canceled").pressEscape();

        // Delete
        findTodoByText("c").hover().find(".destroy").click();
        todosShouldBe("a");
        itemsLeftShouldBe(1);
    }

    private ElementsCollection todos = $$("#todo-list>li");

    private void add(String... todoTexts) {
        for (String text: todoTexts) {
            element("#new-todo").append(text).pressEnter();
        }
    }

    private void todosShouldBe(String... todoTexts) {
        todos.shouldHave(exactTexts(todoTexts));
    }

    private void itemsLeftShouldBe(int number) {
        $("#todo-count>strong").shouldHave(exactText(
                Integer.toString(number)));
    }

    private SelenideElement findTodoByText(String text) {
        return todos.findBy(exactText(text));
    }

    private SelenideElement changeTodo(String oldText, String newText) {
        findTodoByText(oldText).doubleClick();
        return todos.findBy(cssClass("editing")).find(".edit").append(newText);
    }
}
