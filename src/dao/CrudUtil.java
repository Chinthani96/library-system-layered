package dao;

import db.DBConnection;

import java.sql.PreparedStatement;

public class CrudUtil {
    public static <T> T execute(String sql,Object...params) throws Exception {
        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement(sql);
        int i = 0;
        for (Object param : params) {
            i++;
            pst.setObject(i,param);
        }
        if (sql.startsWith("SELECT")) {
            return (T) pst.executeQuery(sql);
        }
        return (T) (Boolean)(pst.executeUpdate()>0);

    }
}
