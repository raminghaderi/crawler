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
public class PizzeriaAdria {

    private static final int RESTAURANT_ID = 3;

    public static void crwal() throws IOException, SQLException {

        List<Meal> meals = new ArrayList<>();
        Document document = Jsoup.connect("https://pizzeria-kassberg.de/pasta/").get();
        Elements elements = document.select(".cherry-list li");
        elements.forEach(element -> {
            String name = element.selectFirst("a").text();
            String mealName = name.substring(3);
            String ingredient = element.text();
            String mealIngredient = ingredient.substring(ingredient.lastIndexOf("("));
            String price = element.selectFirst("strong").text();
            String removeEuroSign = price.replace("€", "");
            String removeComa = removeEuroSign.replace(",", ".");
            float mealPrice = Float.valueOf(removeComa);
            Meal meal = new Meal(RESTAURANT_ID, mealName, mealIngredient, mealPrice);
            meals.add(meal);
        });
        MealRepository mealRepository = new MealRepository();
        mealRepository.update(meals, RESTAURANT_ID);
    }
}
