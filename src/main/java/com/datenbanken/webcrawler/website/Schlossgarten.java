package com.datenbanken.webcrawler.website;

import com.datenbanken.webcrawler.Meal;
import com.datenbanken.webcrawler.repository.MealRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author ramin
 */
public class Schlossgarten {

    private static final int RESTAURANT_ID = 2;

    public static void crwal() throws IOException, SQLException {

        List<Meal> meals = new ArrayList<>();
        Document document = Jsoup.connect("http://www.restaurant-schlossgarten-berlin.de/speisekarte").get();
        Elements elements = document.select(".menu1 .menu-item");
        elements.forEach(element -> {
            String mealName = element.selectFirst(".menu-title2").text();
            String mealIngredient = element.selectFirst(".menu-description2").text();
            String price = element.selectFirst(".price2").text();
            String cents = element.selectFirst(".cents2").text();
            float mealPrice = Float.valueOf(price + "." + cents);
            Meal meal = new Meal(RESTAURANT_ID, mealName, mealIngredient, mealPrice);
            meals.add(meal);
        });
        MealRepository mealRepository = new MealRepository();
        mealRepository.update(meals, RESTAURANT_ID);
    }
}
