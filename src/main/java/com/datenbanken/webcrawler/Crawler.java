package com.datenbanken.webcrawler;

import com.datenbanken.webcrawler.website.Kazan;
import com.datenbanken.webcrawler.website.PizzeriaAdria;
import com.datenbanken.webcrawler.website.Schlossgarten;
import com.datenbanken.webcrawler.website.StreetKitchen;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author ramin
 */
public class Crawler {

    public static void main(String[] args) throws IOException, SQLException {

        StreetKitchen.crwal();
        Schlossgarten.crwal();
        PizzeriaAdria.crwal();

    }

}
