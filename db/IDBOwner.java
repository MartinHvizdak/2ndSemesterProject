package db;

import java.util.ArrayList;

import model.LTD;
import model.Owner;


public interface IDBOwner {

    boolean saveOwner(Owner owner, ArrayList<String> ltdEmails) throws DBException;

    ArrayList<Owner> retrieveAllOwners() throws DBException;

    Owner retrieveOwnerByID(String ownerPersonalID) throws DBException;

    boolean updateOwner( String oldPersonalID, Owner owner, ArrayList<String> ltdEmails) throws DBException;

    boolean deleteOwnerByID(String ownerPersonalID) throws DBException;

    ArrayList<String> getOwnerLTDEmailsByID(String ownerPersonalID) throws  DBException;
}
