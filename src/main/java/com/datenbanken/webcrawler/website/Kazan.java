package com.datenbanken.webcrawler.website;

import com.datenbanken.webcrawler.Meal;
import com.datenbanken.webcrawler.repository.MealRepository;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author ramin
 */
public class Kazan {

    private static final int RESTAURANT_ID = 4;

    public static void crwal() throws IOException, SQLException {

        List<Meal> meals = new ArrayList<>();
        Document document = Jsoup.connect("https://www.kazan-restaurant.com/menu").get();
        Elements elements = document.select(".menu-box p");
        elements.forEach(element -> {
            String mealName = element.selectFirst("span.menu-level-2").text();
            String mealIngredient = element.selectFirst("span.menu-level-3").text();
            String price = element.selectFirst("span.price").text();
            float mealPrice = Float.valueOf(price.substring(1));
            Meal meal = new Meal(RESTAURANT_ID, mealName, mealIngredient, mealPrice);
            meals.add(meal);
        });
        MealRepository mealRepository = new MealRepository();
        mealRepository.update(meals, RESTAURANT_ID);
    }

}
