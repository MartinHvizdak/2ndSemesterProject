package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.LTD;
import model.Owner;

public class DBOwner implements IDBOwner{
    @Override
    public boolean saveOwner(Owner owner, ArrayList<LTD> ltds) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String insert1 = "insert into LTD_owners (id, first_name, second_name, relation) values (?, ?, ?, ?)";
        String insert2 = "insert into LTD_ownerships (LTD_email ,owner_id) values (?, ?)";

        try {
            String[] splited = owner.getName().split(" ");

            PreparedStatement stmt = con.prepareStatement(insert1);
            stmt.setString(1, owner.getId());
            stmt.setString(2, splited[0]);
            stmt.setString(3, splited[1]);
            stmt.setString(4, owner.getRelation());
            stmt.setQueryTimeout(5);
            stmt.executeUpdate();

            for(LTD ltd : ltds) {
                stmt = con.prepareStatement(insert2);
                stmt.setString(1, ltd.getEmail());
                stmt.setString(2, owner.getId());
                stmt.setQueryTimeout(5);
                stmt.executeUpdate();
            }

            stmt.close();

        } catch (SQLException ex) {
            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Owner with this id already exists");
            else
                de = new DBException("Error inserting data");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not inserted! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return false;
    }

    @Override
    public Owner retrieveOwnerByID(String ownerPersonalID) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();
        Owner owner;

        String select = "select * from LTD_owners where LTD_owners.id = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, ownerPersonalID);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();
            if(!rs.next())
                throw new DBException("Owner with this id does not exist");
            String ownerName =  rs.getString("first_name") + " " + rs.getString("second_name");
            String ownerRelation =  rs.getString("relation");

            owner =  new Owner(ownerPersonalID, ownerName, ownerRelation);

            stmt.close();

        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            DBException de = new DBException("Error retrieving data");
            de.setStackTrace(ex.getStackTrace());
            ex.printStackTrace();
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not retrieved! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return owner;
    }

    @Override
    public boolean updateOwner(String oldPersonalID, Owner owner, ArrayList<LTD> ltds) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String update1 = "update LTD_owners set id=?, first_name=?, second_name=?, relation=? where id=?";
        String delete = "delete from LTD_ownerships where owner_id = ?";
        String insert = "insert into LTD_ownerships (LTD_email ,owner_id) values (?, ?)";


        try {
            String[] splited = owner.getName().split(" ");

            PreparedStatement stmt = con.prepareStatement(update1);
            stmt.setString(1, owner.getId());
            stmt.setString(2, splited[0]);
            stmt.setString(3, splited[1]);
            stmt.setString(4, owner.getRelation());
            stmt.setString(5, oldPersonalID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Owner with with id does not exist");

            stmt = con.prepareStatement(delete);
            stmt.setString(1, owner.getId());
            stmt.setQueryTimeout(5);
            stmt.executeUpdate();

            for(LTD ltd : ltds) {
                stmt = con.prepareStatement(insert);
                stmt.setString(1, ltd.getEmail());
                stmt.setString(2, owner.getId());
                stmt.setQueryTimeout(5);
                stmt.executeUpdate();
            }

            stmt.close();

        } catch (SQLException ex) {
            DBException de = null;
            if(ex.getMessage().startsWith("Violation of PRIMARY KEY"))
                de = new DBException("Error: Owner with given new id already exists");
            else
                de = new DBException("Error retrieving data");
            de.setStackTrace(ex.getStackTrace());
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not updated! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return false;
    }

    @Override
    public boolean deleteOwnerByID(String ownerPersonalID) throws DBException {
        Connection con = DBConnection.getInstance().getDBcon();

        String select = "delete from LTD_owners where id = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, ownerPersonalID);
            stmt.setQueryTimeout(5);
            if(stmt.executeUpdate() == 0)
                throw new DBException("Owner with with id does not exist");
            stmt.close();
        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            DBException de = new DBException("Error deleting data");
            de.setStackTrace(ex.getStackTrace());
            ex.printStackTrace();
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not deleted! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return false;
    }

    public ArrayList<String> getOwnerLTDEmailsByID(String ownerPersonalID) throws  DBException{
        Connection con = DBConnection.getInstance().getDBcon();
       ArrayList<String> ltdEmails =  new ArrayList<>();

        String select = "select LTD_email \n" +
                "from LTD_owners \n" +
                "left join LTD_ownerships on LTD_ownerships.owner_id=LTD_owners.id\n" +
                "where LTD_owners.id = ?";

        try {
            PreparedStatement stmt = con.prepareStatement(select);
            stmt.setString(1, ownerPersonalID);
            stmt.setQueryTimeout(5);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String ownerLTDEmail = rs.getString("LTD_email");
                ltdEmails.add(ownerLTDEmail);
            }

            if(ltdEmails.isEmpty())
                throw new DBException("Owner does not have any LTDs assigned");

            stmt.close();

        } catch (DBException ex) {
            throw ex;
        }catch (SQLException ex) {
            DBException de = new DBException("Error retrieving data");
            de.setStackTrace(ex.getStackTrace());
            ex.printStackTrace();
            throw de;
        } catch (NullPointerException ex) {
            DBException de = new DBException("Null pointer exception - possibly Connection object");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } catch (Exception ex) {
            DBException de = new DBException("Data not retrieved! Technical error");
            de.setStackTrace(ex.getStackTrace());
            throw de;
        } finally {
            DBConnection.closeConnection();
        }
        return ltdEmails;
    }
}
