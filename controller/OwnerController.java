package controller;

import db.DBException;
import db.DBOwner;
import model.Owner;

import java.util.ArrayList;

public class OwnerController {
    private DBOwner dbOwner;

    public OwnerController() {
        dbOwner =  new DBOwner();
    }

    public String getName(Owner owner){
        return owner.getFirstName();
    }

    public ArrayList<Owner> getAllOwnersFromDB() throws DBException {
        return dbOwner.retrieveAllOwners();
    }

    public boolean saveOwnerWithUserInputInDB(String ownerPersonalID, String ownerFirstName, String ownerLastName, String ownerRelation, ArrayList<String> ltdEmails) throws DBException {
        Owner owner = new Owner(ownerPersonalID, ownerFirstName, ownerLastName, ownerRelation);
        return dbOwner.saveOwner(owner, ltdEmails);
    }

    public boolean deleteOwnerByIDFromDB(String ownerPersonalID) throws DBException {
        return dbOwner.deleteOwnerByID(ownerPersonalID);
    }

    public Owner getOwnerByIDFromDB(String ownerPersonalID) throws DBException {
        return dbOwner.retrieveOwnerByID(ownerPersonalID);
    }

    public ArrayList<String> getOwnerLTDEmailsByIDFromDB(String ownerPersonalID) throws  DBException{
        return dbOwner.getOwnerLTDEmailsByID(ownerPersonalID);
    }

    public boolean updateOwnerWithUserInputInDB(Owner owner, String newOwnerPersonalID, String ownerFirstName, String ownerSurName, String newOwnerRelation, ArrayList<String> ltdEmails) throws DBException {
        String oldPersonalID = owner.getId();
        owner.setId(newOwnerPersonalID);
        owner.setFirstName(ownerFirstName);
        owner.setSurName(ownerSurName);
        owner.setRelation(newOwnerRelation);
        return dbOwner.updateOwner(oldPersonalID, owner, ltdEmails);
    }
}
