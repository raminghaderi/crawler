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
public class StreetKitchen {

    private static final int RESTAURANT_ID = 1;

    public static void crwal() throws IOException, SQLException {

        List<Meal> meals = new ArrayList<>();
        Document document = Jsoup.connect("http://streetkitchen-viet-cuisine.de/").get();
        Elements elements = document.select(".media .media-body");
        elements.forEach(element -> {
            String name = element.selectFirst("h3").text();
            String mealName = name.substring(name.lastIndexOf(".") + 2);
            String mealIngredient = element.selectFirst("p:nth-child(3)").text();
            String price = element.selectFirst("p.price").text();
            float mealPrice = Float.valueOf(price.substring(0, 4));
            Meal meal = new Meal(RESTAURANT_ID, mealName, mealIngredient, mealPrice);
            meals.add(meal);
        });
        MealRepository mealRepository = new MealRepository();
        mealRepository.update(meals, RESTAURANT_ID);
    }

}
