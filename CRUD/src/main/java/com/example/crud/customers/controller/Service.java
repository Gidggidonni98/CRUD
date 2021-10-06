package com.example.crud.customers.controller;

import com.example.crud.customers.model.Customer;
import com.example.crud.customers.model.CustomerDAO;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import java.util.List;

@Path("/customer")
public class Service {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomer(){
        return new CustomerDAO().findAll();
    }

    @GET
    @Path("/{idCustomer}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomer(@PathParam("idCustomer") int customerNumber){
        return new CustomerDAO().findByCustomerNumber(customerNumber);
    }

    @POST
    @Path("/saveCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Customer saveCustomer(MultivaluedMap<String, String> formParams){
        int customerNumber = Integer.parseInt(formParams.get("customerNumber").get(0));
        if(new CustomerDAO().saveCustomer(getParams(customerNumber, formParams), true))
            return new CustomerDAO().findByCustomerNumber(customerNumber);
        return null;
    }

    @POST
    @Path("/saveCustomer/{idCustomer}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Customer save(@PathParam("idCustomer") int customerNumber, MultivaluedMap<String, String> formParams){
        if(new CustomerDAO().saveCustomer(getParams(customerNumber, formParams), false))
            return new CustomerDAO().findByCustomerNumber(customerNumber);
        return null;
    }

    @DELETE
    @Path("/deleteCustomer/{idCustomer}")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean deleteCustomer(@PathParam("idCustomer") int customerNumber){
        return new CustomerDAO().delete(customerNumber);
    }

    private Customer getParams(int customerNumber, MultivaluedMap<String, String> formParams){
        String customerName = formParams.get("customerName").get(0);
        String contactLastName = formParams.get("contactLastName").get(0);
        String contactFirstName = formParams.get("contactFirstName").get(0);
        String phone = formParams.get("phone").get(0);
        String addressLine1 = formParams.get("addressLine1").get(0);
        String addressLine2 = formParams.get("addressLine2").get(0);
        String city = formParams.get("city").get(0);
        String state = formParams.get("state").get(0);
        String postalCode = formParams.get("postalCode").get(0);
        String country = formParams.get("country").get(0);
        int salesRepEmployeeNumber = Integer.parseInt(formParams.get("salesRepEmployeeNumber").get(0));
        double creditLimit = Double.parseDouble(formParams.get("creditLimit").get(0));


        Customer customer = new Customer(customerNumber, customerName,contactLastName,contactFirstName, phone, addressLine1,addressLine2,city,state,postalCode,country,salesRepEmployeeNumber,creditLimit);
        System.out.println(customer);
        return customer;
    }
}

