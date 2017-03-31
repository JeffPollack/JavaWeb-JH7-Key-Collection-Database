/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keys;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jeff
 */
public class RequestData {

    private String name;
    private String keyNumber;
    int index;
    private String room;
    private String bSelect;

    public RequestData(String name, String keyNumber, String room, String bSelect, int index) {
        this.name = name;
        this.keyNumber = keyNumber;
        this.index = index;
        this.room = room;
        this.bSelect = bSelect;
    }

    public RequestData(String name, String keyNumber, String room, String bSelect) {
        this(name, keyNumber, room, bSelect, -1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(String keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return bSelect;
    }

    public void setBuilding(String bSelect) {
        this.bSelect = bSelect;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        RequestData rData = (RequestData) other;
        if (name.equals(rData.name) && keyNumber.equals(rData.keyNumber)) {
            System.out.println("Person with name, " + name + " already has this key: " + keyNumber);
            return true;
        } else {
            return false;
        }
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

    public String insert(Statement statement) throws SQLException {
        String sql = "SELECT keyNumber FROM KeyCollection WHERE room=" + q_surround(room);

        ResultSet rs = statement.executeQuery(sql);
        if(rs.next()){
            String keyString = rs.getString("keyNumber");

            String finalSql = "INSERT INTO RequestData VALUES('Default Bob',"
                    + q_surround(keyString) + ", null)";
            return executeUpdate(finalSql, statement);
        }
        return executeUpdate(sql, statement);
    }

    public static String getRequests(Statement statement, ArrayList<RequestData> req) {
        String error = "";
        try {
            String sql = "SELECT * FROM RequestData";
            System.out.println("sql= " + sql);
            ResultSet rs = statement.executeQuery(sql);
            req.clear();
            while (rs.next()) {
                String n = rs.getString("name");
                String k = rs.getString("keyNumber");
                String r = null;
                String b = null;
                int ind = rs.getInt("id");
                RequestData reqs = new RequestData(n, k, r, b, ind);
                req.add(reqs);
            }
        } catch (SQLException e) {
            error = e.toString();
        }
        return error;
    }

    private String q_surround(String s) {
        return "\'" + s + "\'";
    }
}
