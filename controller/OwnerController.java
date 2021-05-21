package controller;

import db.DBException;
import db.DBOwner;
import model.LTD;
import model.Owner;

import java.util.ArrayList;

public class OwnerController {
    private DBOwner dbOwner;
    private CustomerController customerController;

    public OwnerController() {
        dbOwner =  new DBOwner();
        customerController =  new CustomerController();
    }

    public String getName(Owner owner){
        return owner.getName();
    }

    public String getRelation(Owner owner){
        return owner.getRelation();
    }

    public boolean saveOwnerWithUserInputInDB(String ownerPersonalID, String ownerName, String ownerRelation, ArrayList<String> ltdEmails) throws DBException {
        Owner owner = new Owner(ownerPersonalID, ownerName, ownerRelation);
        ArrayList<LTD> ltds =  new ArrayList<>();
        for(String email : ltdEmails)
            ltds.add(customerController.getLTDByEmailFromDB(email));
        return dbOwner.saveOwner(owner, ltds);
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

    public boolean updateOwnerWithUserInputInDB(Owner owner, String newOwnerPersonalID, String newOwnerName, String newOwnerRelation, ArrayList<String> ltdEmails) throws DBException {
        String oldPersonalID = owner.getId();
        owner.setId(newOwnerPersonalID);
        owner.setName(newOwnerName);
        owner.setRelation(newOwnerRelation);
        ArrayList<LTD> ltds =  new ArrayList<>();
        for(String email : ltdEmails)
            ltds.add(customerController.getLTDByEmailFromDB(email));
        return dbOwner.updateOwner(oldPersonalID, owner, ltds);
    }
}
