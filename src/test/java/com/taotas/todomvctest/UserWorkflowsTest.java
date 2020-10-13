package com.taotas.todomvctest;

import com.codeborne.selenide.Configuration;
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
        Configuration.fastSetValue = true;

        openTodoMvc();

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

    private ElementsCollection todos = $$("#todo-list>li");

    private void openTodoMvc() {
        String clearCompletedIsClickableScript = "return $._data(" +
                "$('#clear-completed').get(0), 'events')" +
                ".hasOwnProperty('click')";
        open("https://todomvc4tasj.herokuapp.com/");
        Wait().until(jsReturnsValue(clearCompletedIsClickableScript));
    }

    private void add(String... texts) {
        for (String text: texts) {
            element("#new-todo").append(text).pressEnter();
        }
    }

    private void todosShouldBe(String... texts) {
        todos.shouldHave(exactTexts(texts));
    }

    private void itemsLeftShouldBe(int number) {
        $("#todo-count>strong").shouldHave(exactText(
                Integer.toString(number)));
    }

    private SelenideElement startEdit(String oldText, String newText) {
        todos.findBy(exactText(oldText)).doubleClick();
        return todos.findBy(cssClass("editing")).find(".edit").setValue(newText);
    }

    private void edit(String oldText, String newText) {
        startEdit(oldText, newText).pressEnter();
    }

    private void cancelEdit(String oldText, String newText) {
        startEdit(oldText, newText).pressEscape();
    }

    private void toggle(String text) {
        todos.findBy(exactText(text)).find(".toggle").click();
    }

    private void clearCompleted() {
        $("#clear-completed").click();
    }

    private void delete(String text) {
        todos.findBy(exactText(text)).hover().find(".destroy").click();
    }
}


