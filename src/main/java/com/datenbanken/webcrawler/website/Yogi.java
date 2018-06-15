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
public class Yogi {

    private static final int RESTAURANT_ID = 1;

    public static void crwal() throws IOException, SQLException {

        List<Meal> meals = new ArrayList<>();
        Document document = Jsoup.connect("http://lieferservice.yogi-restaurant.de/").get();
        Elements elements = document.select(".wppizza-article-vorspeisen-36");
        elements.forEach(element -> {
            Element e = element.selectFirst(".wppizza-article-info h2");
            e.child(0).remove();
            String mealName = e.text();
            String mealIngredient = element.selectFirst(".wppizza-article-info p").text();
            float mealPrice = Float.valueOf(element.select(".wppizza-article-price span").text().replace(",", "."));
            Meal meal = new Meal(RESTAURANT_ID, mealName, mealIngredient, mealPrice);
            meals.add(meal);
        });
//        MealRepository.update(meals, RESTAURANT_ID);
        MealRepository mealRepository = new MealRepository();
        mealRepository.update(meals, RESTAURANT_ID);
    }

}
