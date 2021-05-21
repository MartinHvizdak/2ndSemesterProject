package ui;

import model.Service;

import java.time.LocalDate;
import java.util.HashMap;

public class DataContainer {
    private String customerEmail;
    private LocalDate payday;
    private HashMap<Service, Integer> servicesAndQuantity;

    public DataContainer() {
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public LocalDate getPayday() {
        return payday;
    }

    public HashMap<Service, Integer> getServicesWithQuantity() {
        return servicesAndQuantity;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail=customerEmail;
    }

    public void getPayday(LocalDate payday) {
        this.payday=payday;
    }

    public void setServicesWithQuantity(HashMap<Service, Integer> servicesAndQuantity) {
        this.servicesAndQuantity=servicesAndQuantity;
    }

}
