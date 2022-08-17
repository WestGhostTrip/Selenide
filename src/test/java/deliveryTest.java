import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class deliveryTest {

    @BeforeEach
    void openBrows() {
        open("http://localhost:9999");
    }

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldTestOrderAdminCenter() {
        String date = generateDate(3);

        $("[data-test-id = 'city'] input").setValue("Казань");
        $("[data-test-id = 'date'] input").doubleClick();
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'name'] input").setValue("Иванов Андрей");
        $("[data-test-id = 'phone'] input").setValue("+72223334455");
        $("[data-test-id = 'agreement']").click();
        $$("button.button").last().click();

        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }
}