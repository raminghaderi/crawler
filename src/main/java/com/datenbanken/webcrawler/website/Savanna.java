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
public class Savanna {

    private static final int RESTAURANT_ID = 3;

    public static void crwal() throws IOException, SQLException {

        List<Meal> meals = new ArrayList<>();
        Document document = Jsoup.connect("https://www.savanna-munich.com/speisekarte/").get();
        Elements elements = document.select("#speisekarte > ul:nth-child(4) li");
        elements.forEach(element -> {
            String mealName = element.selectFirst(".karte-title").text();
            String mealIngredient = element.selectFirst(".karte-subtitle").text();
            String price = element.selectFirst(".karte-preis").text();
            String removeEuroSign = price.replace("€ ", "");
            String removeComa = removeEuroSign.replace(",", ".");
            float mealPrice = Float.valueOf(removeComa);
            Meal meal = new Meal(RESTAURANT_ID, mealName, mealIngredient, mealPrice);
            meals.add(meal);
        });
        MealRepository mealRepository = new MealRepository();
        mealRepository.update(meals, RESTAURANT_ID);
    }

}
