package com.example.crud.customers.model;

import com.example.crud.employee.model.Employee;
import com.example.crud.service.ConnectionMySQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private Connection con;
    private CallableStatement cstm;
    private ResultSet rs;
    public List<Customer> findAll(){
        List<Customer> listCustomer = new ArrayList<>();

        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("SELECT * FROM customers;");
            rs = cstm.executeQuery();

            while(rs.next()){
                Customer customer = new Customer();

                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getDouble("creditLimit"));
                listCustomer.add(customer);
            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return listCustomer;
    }

    public Customer findByCustomerNumber(int customerNumber){
        Customer customer = null;

        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("SELECT * FROM `customers` WHERE customerNumber = ?;");
            cstm.setInt(1, customerNumber);
            rs = cstm.executeQuery();

            if(rs.next()){
                customer = new Customer();
                customer.setCustomerNumber(rs.getInt("customerNumber"));
                customer.setCustomerName(rs.getString("customerName"));
                customer.setContactLastName(rs.getString("contactLastName"));
                customer.setContactFirstName(rs.getString("contactFirstName"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddressLine1(rs.getString("addressLine1"));
                customer.setAddressLine2(rs.getString("addressLine2"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setPostalCode(rs.getString("postalCode"));
                customer.setCountry(rs.getString("country"));
                customer.setSalesRepEmployeeNumber(rs.getInt("salesRepEmployeeNumber"));
                customer.setCreditLimit(rs.getDouble("creditLimit"));

            }
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return customer;
    }

    public boolean saveCustomer(Customer customer, boolean isCreate){
        boolean flag = false;

        try{
            con = ConnectionMySQL.getConnection();
            if(isCreate){
                cstm = con.prepareCall("INSERT INTO `customers`(`customerNumber`, `customerName`, `contactLastName`, `contactFirstName`, `phone`, `addressLine1`, `addressLine2`, `city`, `state`, `postalCode`, `country`, `salesRepEmployeeNumber`, `creditLimit`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");

                cstm.setInt(1, customer.getCustomerNumber());
                cstm.setString(2, customer.getCustomerName());
                cstm.setString(3, customer.getContactLastName());
                cstm.setString(4, customer.getContactFirstName());
                cstm.setString(5, customer.getPhone());
                cstm.setString(6, customer.getAddressLine1());
                cstm.setString(7, customer.getAddressLine2());
                cstm.setString(8, customer.getCity());
                cstm.setString(9, customer.getState());
                cstm.setString(10,customer.getPostalCode());
                cstm.setString(11, customer.getCountry());
                cstm.setInt(12, customer.getSalesRepEmployeeNumber());
                cstm.setDouble(13, customer.getCreditLimit());
            } else {
                cstm = con.prepareCall("UPDATE `customers` SET `customerNumber`=?,`customerName`=?,`contactLastName`=?,`contactFirstName`=?,`phone`=?,`addressLine1`=?,`addressLine2`=?,`city`=?,`state`=?,`postalCode`=?,`country`=?,`salesRepEmployeeNumber`=?,`creditLimit`=? WHERE customerNumber = ?;");


                cstm.setString(1, customer.getCustomerName());
                cstm.setString(2, customer.getContactLastName());
                cstm.setString(3, customer.getContactFirstName());
                cstm.setString(4, customer.getPhone());
                cstm.setString(5, customer.getAddressLine1());
                cstm.setString(6, customer.getAddressLine2());
                cstm.setString(7, customer.getCity());
                cstm.setString(8, customer.getState());
                cstm.setString(9,customer.getPostalCode());
                cstm.setString(10, customer.getCountry());
                cstm.setInt(11, customer.getSalesRepEmployeeNumber());
                cstm.setDouble(12, customer.getCreditLimit());
                cstm.setInt(13, customer.getCustomerNumber());
            }

            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return flag;
    }

    public boolean delete(int customerNumber){
        boolean flag = false;

        try{
            con = ConnectionMySQL.getConnection();
            cstm = con.prepareCall("DELETE FROM `customers` WHERE customerNumber = ?");
            cstm.setInt(1, customerNumber);
            flag = cstm.executeUpdate() == 1;
        }catch(SQLException e){
            System.out.printf("Ha sucedido algún error: %s", e.getMessage());
        }finally{
            ConnectionMySQL.closeConnections(con, cstm, rs);
        }
        return flag;
    }
}
