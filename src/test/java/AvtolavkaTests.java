import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class AvtolavkaTests {
    @BeforeEach
    void closeModal() {
        open("https://avtolavka.net/");

        $(".fancybox-item").click();//закрываем модалку

        $("html").shouldNotHave(text("Выбор офиса обслуживания"));

    }

    @Test
    void openPageWithBatteryTest() {// открытие раздела с главной страницы
        $(byText("АКБ")).click();

        Assertions.assertEquals(url(), "https://avtolavka.net/batteries_catalog"); //проверяем url
    }

    @Test
    void findFromFieldTest() {//поиск из строки поиска
        $("#pcode").setValue("грм").pressEnter();

        $("#searchResultsTable > tbody").shouldBe(text("Башмак цепи грм"));
    }

    @Test
    void openAndClickFromMenyTest() {//переход в раздел из выпадающего меню
        $$(".headCatalog.fr-dropdown-toggle").find(visible).click();
        $$(".fr-dropdown-menu")
                .find(visible)
                .$(byText("Компрессоры"))
                .click();

        $("html").shouldBe(text("Компрессор"));
    }

    @Test
    void addInBasketTest() {
        $$(".headCatalog.fr-dropdown-toggle").find(visible).click();
        $$(".fr-dropdown-menu")
                .find(visible)
                .$(byText("Ароматизаторы"))
                .click();
        //Ищем товар и нажимаем на добавить в корзину
        $("html").shouldBe(text("Ароматизатор"));

        $$(".fr-icon2-basket-2").find(visible).click();
        $$(".wCart").find(visible).click();
        //проверка добавления товара в корзину
        $("#formTrash").shouldBe(text("Ароматизатор"));

    }

    @Test
    void formAuthTest() {
        $(".loginLink").click();

        String login = $("#login").getAttribute("placeholder");
        String pass = $("#pass").getAttribute("placeholder");
        String btnGo = $("#go").getAttribute("value");
        //проверяем наличие полей на поле Авторизация
        Assertions.assertEquals("Логин", login);
        Assertions.assertEquals("Пароль", pass);
        Assertions.assertEquals("Вход", btnGo);
    }

}
