package eu.ezpzcraft.pvpkit;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.service.sql.SqlService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Database
{
    private SqlService sql = null;
    private String host = "localhost";
    private String port = "3306";
    private String username = "root";
    private String password = "password";
    private String database = "ezpzPvpKit";

    /*
     * Create the database and the player table
     */
    public void createDB() throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/?user=" + username + "&password=" + password);

        String executeString = "CREATE DATABASE IF NOT EXISTS " + database;
        execute(executeString, datasource);

        datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        executeString = "CREATE TABLE IF NOT EXISTS Player("
                + "UUID VARCHAR(64) PRIMARY KEY,"
                + "name VARCHAR(32) NOT NULL,"
                + "first_join BIGINT NOT NULL,"
                + "last_seen BIGINT,"
                + "rank VARCHAR(32) NOT NULL,"
                + "remaining_ranked INT DEFAULT 0,"
                + "last_vote_1 TIMESTAMP,"
                + "last_vote_2 TIMESTAMP,"
                + "last_vote_3 TIMESTAMP,"
                + "last_vote_4 TIMESTAMP"
                + ")ENGINE=InnoDB;";
        execute(executeString, datasource);
    }

    /*
     * Add the three tables to the database for the new queue
     */
    public void addQueue(String name) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        // Stats
        String executeString = "CREATE TABLE IF NOT EXISTS StatsQueue" + name + " ("
                            + "player VARCHAR(64) PRIMARY KEY,"
                            + "score DOUBLE DEFAULT 0,"
                            + "kills INT DEFAULT 0,"
                            + "deaths INT DEFAULT 0,"
                            + "wins INT DEFAULT 0,"
                            + "loose INT DEFAULT 0,"
                            + "FOREIGN KEY(player)"
                            + "REFERENCES Player(UUID)"
                            + ")ENGINE=InnoDB;";
        execute(executeString, datasource);

        // Inventory
        executeString = "CREATE TABLE IF NOT EXISTS InventoryQueue" + name + " ("
                + "inventory_id BIGINT PRIMARY KEY,"
                + "item1 JSON,"
                + "item2 JSON,"
                + "item3 JSON,"
                + "item4 JSON,"
                + "item5 JSON,"
                + "item6 JSON,"
                + "item7 JSON,"
                + "item8 JSON,"
                + "item9 JSON,"
                + "item10 JSON,"
                + "item11 JSON,"
                + "item12 JSON,"
                + "item13 JSON,"
                + "item14 JSON,"
                + "item15 JSON,"
                + "item16 JSON,"
                + "item17 JSON,"
                + "item18 JSON,"
                + "item19 JSON,"
                + "item20 JSON,"
                + "item21 JSON,"
                + "item22 JSON,"
                + "item23 JSON,"
                + "item24 JSON,"
                + "item25 JSON,"
                + "item26 JSON,"
                + "item27 JSON,"
                + "item28 JSON,"
                + "item29 JSON,"
                + "item30 JSON,"
                + "item31 JSON,"
                + "item32 JSON,"
                + "item33 JSON,"
                + "item34 JSON,"
                + "item35 JSON,"
                + "item36 JSON,"
                + "item37 JSON,"
                + "item38 JSON,"
                + "item39 JSON,"
                + "item40 JSON"
                + ")ENGINE=InnoDB;";
        execute(executeString, datasource);

        // Matchs
        executeString = "CREATE TABLE IF NOT EXISTS MatchQueue" + name + " ("
                + "match_id BIGINT PRIMARY KEY,"
                + "player1 VARCHAR(64) NOT NULL,"
                + "player2 VARCHAR(64) NOT NULL,"
                + "winner VARCHAR(64),"
                + "map VARCHAR(32),"
                + "date BIGINT,"
                + "time INT,"
                + "inventory1 BIGINT,"
                + "inventory2 BIGINT,"
                + "heal1 INT,"
                + "heal2 INT,"
                + "hunger1 INT,"
                + "hunger2 INT,"
                + "FOREIGN KEY(player1)"
                + "REFERENCES Player(UUID),"
                + "FOREIGN KEY(player2)"
                + "REFERENCES Player(UUID),"
                + "FOREIGN KEY(inventory1)"
                + "REFERENCES InventoryQueue" + name + "(inventory_id),"
                + "FOREIGN KEY(inventory2)"
                + "REFERENCES InventoryQueue" + name + "(inventory_id)"
                + ")ENGINE=InnoDB;";
        execute(executeString, datasource);
    }

    /*
     * Update 'Player' table on playerJoin
     */
    public void updateJoinPlayer(Player player) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString =
          "INSERT INTO Player "
        + "(UUID, name, first_join, rank, remaining_ranked) "
        + "VALUES (?,?,?,?,?) "
        + "ON DUPLICATE KEY UPDATE name=?";

        String[] params = new String[6];
        params[0] = player.getIdentifier();
        params[1] = player.getName();
        params[2] = new Timestamp(new java.util.Date().getTime()).getTime() + "";
        params[3] = Config.DEFAULT_RANK;
        params[4] = Config.DEFAULT_NUMBER_RANKED;
        params[5] = params[1];

        executePrepared(datasource, executeString, params);
    }

    /*
     * Update 'Player' table on playerLeave
     */
    public void updateLeavePlayer(Player player) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString = "UPDATE Player SET last_seen=? WHERE UUID=?";

        String[] params = new String[2];
        params[0] = new Timestamp(new java.util.Date().getTime()).getTime() + "";
        params[1] = player.getIdentifier();

        executePrepared(datasource, executeString, params);
    }

    /*
     * Save match TODO
     */
    public void saveMatch(Duel duel) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString = "UPDATE Player SET last_seen=? WHERE UUID=?";

        String[] params = new String[2];
        params[0] = new Timestamp(new java.util.Date().getTime()).getTime() + "";

        executePrepared(datasource, executeString, params);
    }

    /*
     * Save inventory TODO
     */
    public void saveInventory(Inventory inventory) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString = "UPDATE Player SET last_seen=? WHERE UUID=?";

        String[] params = new String[2];
        params[0] = new Timestamp(new java.util.Date().getTime()).getTime() + "";

        executePrepared(datasource, executeString, params);
    }

    /*
     * Get player info TODO
     */
    public void getPlayer(String UUID) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString = "UPDATE Player SET last_seen=? WHERE UUID=?";

        String[] params = new String[2];
        params[0] = new Timestamp(new java.util.Date().getTime()).getTime() + "";

        executePrepared(datasource, executeString, params);
    }

    /* Utils */
    private javax.sql.DataSource getDataSource(String jdbcUrl) throws SQLException
    {
        if (sql == null)
        {
            sql = Sponge.getServiceManager().provide(SqlService.class).get();
        }
        return sql.getDataSource(jdbcUrl);
    }

    private static void execute(String sql, DataSource datasource)
    {
        try
        {
            Connection connection = datasource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static void executePrepared(DataSource datasource, String sql, String[] values)
    {
        try
        {
            Connection connection = datasource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < values.length; i++)
                preparedStatement.setObject(i + 1, values[i]);

            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
