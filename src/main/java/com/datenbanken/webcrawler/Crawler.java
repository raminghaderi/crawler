package com.datenbanken.webcrawler;

import com.datenbanken.webcrawler.website.Savanna;
import com.datenbanken.webcrawler.website.Schlossgarten;
import com.datenbanken.webcrawler.website.Yogi;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author ramin
 */
public class Crawler {

    public static void main(String[] args) throws IOException, SQLException {

        Yogi.crwal();
        Schlossgarten.crwal();
        Savanna.crwal();

    }

}
