/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keys;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

public class KeyCollection {
    
    /**
     *
     * @param statement
     * @param request
     * @return
     */
    public static String update(Statement statement, HttpServletRequest request){
        String error = "";
        String action = request.getParameter("action");
        if (action != null){
            String building = request.getParameter("building");
            String room = request.getParameter("room");
            String description = request.getParameter("description");
            String keyNumber = request.getParameter("keyNumber");
            KeyEntry key = new KeyEntry(building, room, description, keyNumber);
            
            String bSelect = request.getParameter("valajax");
            String name = request.getParameter("name");
            RequestData rData = new RequestData(name, keyNumber, room, bSelect); 
            
            String sIndex;
            int index;
            
            switch(action){
                case "Clear List" : 
                    error = KeyEntry.remove(-1, statement);
                    break;
                case "add" : 
                    error = key.insert(statement);
                    break;
                case "remove" : 
                    sIndex = request.getParameter("index");
                    index = Integer.parseInt(sIndex);
                    error = KeyEntry.remove(index, statement);
                    break;
                case "update" : 
                    sIndex = request.getParameter("index");
                    index = Integer.parseInt(sIndex);
                    error = key.update(index, statement);
                    break;
                case "request" :
            {
                try {
                    error = rData.insert(statement);
                } catch (SQLException ex) {
                    Logger.getLogger(KeyCollection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                    break;
            }
        }
        
        ArrayList<RequestData> rd = new ArrayList<>();
            error += RequestData.getRequests(statement, rd);
            request.setAttribute("RequestData", rd);
        
        ArrayList<KeyEntry> kCollection = new ArrayList<>();
            error += KeyEntry.getKeys(statement, kCollection);
            request.setAttribute("KeyCollection", kCollection);
        
        return error;
    }
}
