import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class Test1 {
    @BeforeEach
    public void closeModal() {
        open("https://avtolavka.net/");
        $(".fancybox-item").click();
        $("html").shouldHave(text("AVTOLAVKA.NET"));

    }

    @Test
    public void openBattery() {
        $(byText("АКБ")).click();
        Assertions.assertEquals(url(), "https://avtolavka.net/batteries_catalog");
    }

    @Test
    public void findFromField() {
        $("#pcode").setValue("грм").pressEnter();
        $("#searchResultsTable > tbody").shouldBe(text("Башмак цепи грм"));
    }

    @Test
    public void openAndClickFromMeny() {
        $$(".headCatalog.fr-dropdown-toggle").find(visible).click();
        $$(".fr-dropdown-menu")
                .find(visible)
                .$(byText("Компрессоры"))
                .click();
        $("html").shouldBe(text("Компрессор"));
    }

    @Test
    public void addBascet() {
        $$(".headCatalog.fr-dropdown-toggle").find(visible).click();
        $$(".fr-dropdown-menu")
                .find(visible)
                .$(byText("Ароматизаторы"))
                .click();
        $("html").shouldBe(text("Ароматизатор"));
        $$(".fr-icon2-basket-2").find(visible).click();
        $$(".wCart").find(visible).click();
        $("#formTrash").shouldBe(text("Ароматизатор"));

    }

    @Test
    public void formAuth() {
        $(".loginLink").click();
        String login = $("#login").getAttribute("placeholder");
        String pass = $("#pass").getAttribute("placeholder");
        String btnGo = $("#go").getAttribute("value");

        Assertions.assertEquals("Логин", login);
        Assertions.assertEquals("Пароль", pass);
        Assertions.assertEquals("Вход", btnGo);
    }

}
