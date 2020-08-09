import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AvtolavkaTests {
    @BeforeEach
    void closeModal() {
        open("https://avtolavka.net/");
        $(".fancybox-item").click(); // закрываем модалку
        $("html").shouldNotHave(text("Выбор офиса обслуживания"));
    }

    @Test
    void openPageWithBatteryTest() { // открытие раздела с главной страницы
        $(byText("АКБ")).click();

        assertEquals(url(), "https://avtolavka.net/batteries_catalog");
    }

    @Test
    void findItemFromFieldTest() { // поиск из строки поиска
        $("#pcode").setValue("грм").pressEnter();

        $("#searchResultsTable > tbody").shouldBe(text("Башмак цепи грм"));
    }

    @Test
    void openItemFromMenuTest() { // переход в раздел из выпадающего меню
        $$(".headCatalog.fr-dropdown-toggle").find(visible).click();
        $$(".fr-dropdown-menu")
                .find(visible)
                .$(byText("Компрессоры"))
                .click();

        $(".item_ul").shouldBe(text("Компрессор"));
    }

    @Test
    void addToCartTest() {
        $$(".headCatalog.fr-dropdown-toggle").find(visible).click();
        $$(".fr-dropdown-menu")
                .find(visible)
                .$(byText("Ароматизаторы"))
                .click();
        $(".item_ul").shouldBe(text("Ароматизатор")); // Ищем товар и нажимаем на добавить в корзину

        $$(".fr-icon2-basket-2").find(visible).click(); // todo fix
        $$(".wCart").find(visible).click();

        //проверка добавления товара в корзину
        $("#formTrash").shouldBe(text("Ароматизатор"));
    }

    @Test
    void formAuthTest() {
        $(".loginLink").click();

        $("#login").shouldHave(attribute("placeholder", "Логин"));
        $("#pass").shouldHave(attribute("placeholder", "Пароль"));
        $("#go").shouldHave(attribute("value", "Вход"));
    }

}
