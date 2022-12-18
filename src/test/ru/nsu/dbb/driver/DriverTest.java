package ru.nsu.dbb.driver;

import org.junit.jupiter.api.Test;
import ru.nsu.dbb.driver.Driver;
import ru.nsu.dbb.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
class DriverTest {

    @Test
    void getDatabaseMeta() throws SQLException {
        Driver driver = new Driver();
        driver.openConnection("jdbc:sqlite:src/test/resources/test.db", "", "");
        Database actualDatabase = driver.getDatabaseMeta();
        var expected = new Database();
        ArrayList<Column> columns = new ArrayList<>(Arrays.asList(
                new Column(
                        "AlbumId",
                        "4",
                        "2000000000",
                        "NO"
                ),
                new Column(
                        "title1",
                        "12",
                        "160",
                        "NO"
                ),
                new Column(
                        "ArtistId",
                        "4",
                        "2000000000",
                        "NO"
                ),
                new Column(
                        "test",
                        "12",
                        "2000000000",
                        "YES"
                )
        ));
        PrimaryKey primaryKey = new PrimaryKey();
        primaryKey.addColumnToPrimaryKey("AlbumId");
        ArrayList<PrimaryKey> primaryKeys = new ArrayList<>(List.of(
                primaryKey
        ));
        ArrayList<Index> indices = new ArrayList<>(List.of(
                new Index(
                        "IFK_AlbumArtistId",
                        true,
                        "ArtistId"
                )
        ));
        Table table = new Table("albums");
        table.setColumns(columns);
        table.setIndexes(indices);
        table.setPrimaryKeys(primaryKeys);
        table.setForeignKeys(new ArrayList<>());
        ArrayList<Table> tables = new ArrayList<>();
        tables.add(table);
        Schema schema = new Schema();
        schema.setTables(tables);
        ArrayList<Schema> schemas = new ArrayList<>();
        schemas.add(schema);
        Catalog catalog = new Catalog();
        catalog.setSchemas(schemas);
        ArrayList<Catalog> catalogs = new ArrayList<>();
        catalogs.add(catalog);
        Database database = new Database();
        database.setCatalogs(catalogs);
        assertThat(actualDatabase).usingRecursiveComparison().isEqualTo(database);
    }
}