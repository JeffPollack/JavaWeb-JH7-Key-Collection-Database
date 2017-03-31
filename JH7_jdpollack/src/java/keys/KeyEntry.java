package keys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

public class KeyEntry {

    private String building;
    private String room;
    private String description;
    private String keyNumber;
    int index;

    public KeyEntry(String building, String room, String description, String keyNumber, int index) {
        this.building = building;
        this.room = room;
        this.description = description;
        this.keyNumber = keyNumber;
        this.index = index;
    }

    public KeyEntry(String building, String room, String description, String keyNumber) {
        this(building, room, description, keyNumber, -1);
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean equals(Object other) {
        if (other==null || other.getClass() != getClass()){
            return false;
        }
        KeyEntry key = (KeyEntry) other;
        if (building.equals(key.building) && room.equals(key.room)){
            System.out.println(building+" "+room+": This room already has an entry.");
            return true;
        }else{
            return false;
        }
    }

    public String update(int index, Statement statement) {
        String sql = "UPDATE KeyCollection SET building=" + q_surround(building)
                + ", room=" + q_surround(room)
                + ", description=" + q_surround(description)
                + ", keyNumber=" + q_surround(keyNumber)
                + " WHERE id=" + index;                
        return executeUpdate(sql, statement);
    }

    public String insert(Statement statement) {
        String sql = "SELECT room FROM KeyCollection WHERE building=" + q_surround(building)
                + " AND keyNumber=" + q_surround(keyNumber);
        try{
            ResultSet rs = statement.executeQuery(sql);
            if(rs.next())
                return "Key already has an entry";
        }catch(SQLException e){
            return e.toString();
        }
        sql = "INSERT INTO KeyCollection VALUES(" + q_surround(building) + ","
                + q_surround(room) + "," +q_surround(description) + ","
                +q_surround(keyNumber) + ", null)";        
        return executeUpdate(sql, statement);
    }

    public static String remove(int index, Statement statement) {
        String sql = "DELETE FROM KeyCollection ";
        if(index>0)
            sql += "WHERE id=" + index;
        return executeUpdate(sql, statement);
    }

    private static String executeUpdate(String sql, Statement statement) {
        String error = "";
        try {
            System.out.println("sql=" + sql);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            error = e.toString();
        }
        return error;
    }
    
    public static String getKeys(Statement statement, ArrayList<KeyEntry> keys){
        String error = "";
        try{
            String sql = "SELECT * FROM KeyCollection";
            System.out.println("sql= "+sql);
            ResultSet rs = statement.executeQuery(sql);
            keys.clear();
            while(rs.next()){
                String b = rs.getString("building");
                String r = rs.getString("room");
                String d = rs.getString("description");
                String k = rs.getString("keyNumber");
                int ind = rs.getInt("id");
                KeyEntry key = new KeyEntry(b, r, d, k, ind);
                keys.add(key);
            }
        }catch(SQLException e){
            error = e.toString();
        }
        return error;
    }
    private String q_surround(String s) {
        return "\'" + s + "\'";
    }
}
