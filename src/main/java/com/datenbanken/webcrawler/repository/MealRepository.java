package com.datenbanken.webcrawler.repository;

import com.datenbanken.webcrawler.Meal;
import com.datenbanken.webcrawler.MealEntity;
import java.sql.SQLException;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ramin
 */
public class MealRepository extends BaseRepository {

    public void update(List<Meal> meals, int restaurantId) throws SQLException {

        List<MealEntity> existingMeals = existingMeals(restaurantId);

        List<Meal> toBeInserted = new ArrayList<>();
        List<MealEntity> toBeUpdated = new ArrayList<>();

        if (existingMeals.isEmpty()) {
            insert(meals);
            return;
        }
        for (int i = 0; i < meals.size(); i++) {
            for (int j = 0; j < existingMeals.size(); j++) {
                //checking if meals have not changed in the menue
                if ((meals.get(i).getName().equals(existingMeals.get(j).getName())) && (meals.get(i).getIngredient().equals(existingMeals.get(j).getIngredient()))
                        && (meals.get(i).getPrice() == existingMeals.get(j).getPrice())) {

                    //Do Nothing
                    break;
                } //checking if new meal added to the website menue
                else if (!meals.get(i).getName().equals(existingMeals.get(j).getName())
                        && !meals.get(i).getIngredient().equals(existingMeals.get(j).getIngredient()) && j == (existingMeals.size() - 1)) {
                    //Do Nothing
                    toBeInserted.add(meals.get(i));

                }//update if anymeal needs to be updated
                else if ((meals.get(i).getName().equals(existingMeals.get(j).getName())
                        && !meals.get(i).getIngredient().equals(existingMeals.get(j).getIngredient()))
                        || (!meals.get(i).getName().equals(existingMeals.get(j).getName())
                        && meals.get(i).getIngredient().equals(existingMeals.get(j).getIngredient()))
                        || (meals.get(i).getName().equals(existingMeals.get(j).getName())
                        && meals.get(i).getPrice() != existingMeals.get(j).getPrice())) {
                    toBeUpdated.add(new MealEntity(existingMeals.get(j).getMealId(),
                            meals.get(i).getRestaurantID(), meals.get(i).getName(), meals.get(i).getIngredient(), meals.get(i).getPrice()));
                    break;
                }

            }

        }

        if (!toBeInserted.isEmpty()) {
            insert(toBeInserted);
        }
        if (!toBeUpdated.isEmpty()) {
            batchUpdate(toBeUpdated);
        }

    }

    public List<MealEntity> existingMeals(int restaurantId) throws SQLException { //static can't be referenced
        String sql = "select * from meals where restaurant_id = " + restaurantId;
        List<MealEntity> existingMeals = new ArrayList<>();
        Statement statment = getConnection().createStatement();
        ResultSet resultSet = statment.executeQuery(sql);

        while (resultSet.next()) {
            MealEntity meal = new MealEntity(resultSet.getInt("id"), resultSet.getInt("restaurant_id"), resultSet.getString("name"),
                    resultSet.getString("ingredient"), resultSet.getFloat("price"));
            existingMeals.add(meal);
        }
        return existingMeals;
    }

    private void insert(List<Meal> meals) throws SQLException {
        String sql = "insert into meals(restaurant_id, name, ingredient, price) "
                + "values(?, ?, ?, ?)";

        PreparedStatement preparedStatement = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        meals.forEach(meal -> {

            try {

                preparedStatement.setInt(1, meal.getRestaurantID());
                preparedStatement.setString(2, meal.getName());
                preparedStatement.setString(3, meal.getIngredient());
                preparedStatement.setFloat(4, meal.getPrice());

                preparedStatement.addBatch();

            } catch (SQLException ex) {
                Logger.getLogger(MealRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        try {
            preparedStatement.executeBatch();
            getConnection().commit();
        } catch (SQLException ex) {
            getConnection().rollback();
        }

    }

    private void batchUpdate(List<MealEntity> updatedMeals) throws SQLException {
        String sql = "update meals "
                + "set restaurant_id = ?, name = ?, ingredient = ?, price = ? "
                + "where id = ?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

        updatedMeals.forEach(meal -> {

            try {

                preparedStatement.setInt(1, meal.getRestaurantID());
                preparedStatement.setString(2, meal.getName());
                preparedStatement.setString(3, meal.getIngredient());
                preparedStatement.setFloat(4, meal.getPrice());
                preparedStatement.setInt(5, meal.getMealId());

                preparedStatement.addBatch();

            } catch (SQLException ex) {
                Logger.getLogger(MealRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        try {
            preparedStatement.executeBatch();
            getConnection().commit();
        } catch (SQLException ex) {
            getConnection().rollback();
        }

    }

    private void delete(List<MealEntity> toBeDeletedMeals) throws SQLException {
        String sql = "delete from meals " + "where id = ?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);

        toBeDeletedMeals.forEach(meal -> {

            try {

                preparedStatement.setInt(1, meal.getMealId());

                preparedStatement.addBatch();

            } catch (SQLException ex) {
                Logger.getLogger(MealRepository.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        try {
            preparedStatement.executeBatch();
            getConnection().commit();
        } catch (SQLException ex) {
            getConnection().rollback();
        }

    }

}
