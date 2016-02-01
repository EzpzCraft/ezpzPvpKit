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
    private String host = "192.168.1.52";
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
        
        executeString = "CREATE TABLE IF NOT EXISTS Arenas("
                + "name VARCHAR(32) PRIMARY KEY,"
        		+ "world VARCHAR(32) NOT NULL,"
        		+ "pos1_x DOUBLE NOT NULL, "
        		+ "pos1_y DOUBLE NOT NULL, "
        		+ "pos1_z DOUBLE NOT NULL, "
        		+ "pos2_x DOUBLE NOT NULL, "
        		+ "pos2_y DOUBLE NOT NULL, "
        		+ "pos2_z DOUBLE NOT NULL, "
        		+ "orientation1_x DOUBLE NOT NULL, "
        		+ "orientation1_y DOUBLE NOT NULL, "
        		+ "orientation1_z DOUBLE NOT NULL, "
        		+ "orientation2_x DOUBLE NOT NULL, "
        		+ "orientation2_y DOUBLE NOT NULL, "
        		+ "orientation2_z DOUBLE NOT NULL, "
        		+ "score DOUBLE NOT NULL DEFAULT 0, "
        		+ "type VARCHAR(32) NOT NULL "
                + ")ENGINE=InnoDB;";
        execute(executeString, datasource);  
        
        executeString = "CREATE TABLE IF NOT EXISTS Queues("
                + "name VARCHAR(32) PRIMARY KEY,"
        		+ "isRanked BOOLEAN NOT NULL DEFAULT false,"
        		+ "type VARCHAR(32) NOT NULL"
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
    
    public void saveArena(Arena arena) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString =
          "INSERT INTO Arenas "
        + "(name, world, pos1_x, pos1_y, pos1_z, pos2_x, pos2_y, pos2_z, "
        + " orientation1_x, orientation1_y, orientation1_z, orientation2_x, orientation2_y, orientation2_z,"
        + " score, type) "
        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "
        + "ON DUPLICATE KEY UPDATE world=?, pos1_x=?, pos1_y=?, pos1_z=?, pos2_x=?, pos2_y=?, pos2_z=?,"
        + "orientation1_x=?, orientation1_y=?, orientation1_z=?, orientation2_x=?, orientation2_y=?, orientation2_z=?,"
        + "score=?, type=?";

        String[] params = new String[31];
        params[0] = arena.getName();
        params[1] = arena.getPos1().getExtent().getName();
        params[2] = arena.getPos1().getX() + "";
        params[3] = arena.getPos1().getY() + "";
        params[4] = arena.getPos1().getZ() + "";
        params[5] = arena.getPos2().getX() + "";
        params[6] = arena.getPos2().getY() + "";
        params[7] = arena.getPos2().getZ() + "";
		params[8] = arena.getRotation1().getX() + "";
		params[9] = arena.getRotation1().getY() + "";
		params[10] = arena.getRotation1().getZ() + "";
		params[11] = arena.getRotation2().getX() + "";
        params[12] = arena.getRotation2().getY() + "";
        params[13] = arena.getRotation2().getZ() + "";
        params[14] = arena.getVoteScore() + "";
        params[15] = arena.getType();
        params[16] = arena.getPos1().getExtent().getName();
        params[17] = arena.getPos1().getBlockX() + ""; // Update
		params[18] = arena.getPos1().getBlockY() + "";
		params[19] = arena.getPos1().getBlockZ() + "";        
        params[20] = arena.getPos2().getBlockX() + "";
        params[21] = arena.getPos2().getBlockY() + "";
        params[22] = arena.getPos2().getBlockZ() + "";
        params[23] = arena.getRotation1().getX() + "";
        params[24] = arena.getRotation1().getY() + "";
        params[25] = arena.getRotation1().getZ() + "";
        params[26] = arena.getRotation2().getX() + "";
        params[27] = arena.getRotation2().getY() + "";
		params[28] = arena.getRotation2().getZ() + "";
		params[29] = arena.getVoteScore() + "";
		params[30] = arena.getType();
        executePrepared(datasource, executeString, params);
    }

    
    public void deleteArena(Arena arena) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString =
          "DELETE FROM Arenas "
        + "WHERE name=?";

        String[] params = new String[1];
        params[0] = arena.getName();

        executePrepared(datasource, executeString, params);
    }
    
    public void saveQueue(DuelQueue queue) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString =
            "INSERT INTO Queues "
                    + "(name, isRanked, type) "
                    + "VALUES (?,?,?) "
                    + "ON DUPLICATE KEY UPDATE isRanked=?, type=?";

        String[] params = new String[5];
        params[0] = queue.getName();
        params[1] = queue.isRanked() + "";
        params[2] = queue.getType();
        params[3] = queue.isRanked() + "";
        params[4] = queue.getType();

        executePrepared(datasource, executeString, params);
    }   
    
    public void deleteQueue(DuelQueue queue) throws SQLException
    {
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);

        String executeString =
          "DELETE FROM Queues "
        + "WHERE name=?";

        String[] params = new String[1];
        params[0] = queue.getName();

        executePrepared(datasource, executeString, params);
    }
    
    public ArrayList<DuelQueue> loadQueues() throws SQLException
    {
    	ArrayList<DuelQueue> queues = new ArrayList<DuelQueue>();
    	
        DataSource datasource = getDataSource("jdbc:mysql://" + host + ":" + port + "/" + database + "?user=" + username + "&password=" + password);
        String executeString = "SELECT * FROM Queues";
        ResultSet result = execute(executeString, datasource);
    	    	
        String name;
        Boolean isRanked;
        String type;
        while( result.next() )
        {
        	name = result.getNString("name");
        	isRanked = result.getBoolean("isRanked");
        	type = result.getString("type");
        	
        	//EzpzPvpKit.getInstance().addDuelQueue( new DuelQueue(name, isRanked, type) );
        }
        
    	return queues;
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

    private static ResultSet execute(String sql, DataSource datasource)
    {
        try
        {
            Connection connection = datasource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            statement.close();
            connection.close();
            return result;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private static ResultSet executePrepared(DataSource datasource, String sql, String[] values)
    {
        try
        {
            Connection connection = datasource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < values.length; i++)
                preparedStatement.setObject(i + 1, values[i]);

            ResultSet result = preparedStatement.executeQuery();
            preparedStatement.close();
            connection.close();
            
            return result;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}