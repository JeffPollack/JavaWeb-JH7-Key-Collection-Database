package jdbc;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GlobalConnectionPool implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        ConnectionPool connectionPool = new ConnectionPool();

        String userid = servletContext.getInitParameter("db_userid");
        String password = servletContext.getInitParameter("db_password");

        // The following code be configurables in web.xml, but for simplicity I have them hard-coded
        int initialConnections = 3;
        int maxConnections = 20;
        boolean waitIfBusy = true;

        try {
            connectionPool.CreateConnectionPool(userid, password,
                    initialConnections, maxConnections, waitIfBusy);
        } catch (SQLException ex) {
            Logger.getLogger(GlobalConnectionPool.class.getName()).log(Level.SEVERE, null, ex);
        }
        servletContext.setAttribute("connectionPool", connectionPool);
        System.out.println("GlobalConnectionPool has setup the connection pool");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");
        if (connectionPool != null) {
            connectionPool.closeAllConnections();
            System.out.println("GlobalConnectionPool closed out the connection pool");
        }
    }
}
