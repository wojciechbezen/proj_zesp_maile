package pl.edu.pwr.s241223;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class DatabaseClient {
    private ObjectMapper objectMapper = new ObjectMapper();
    private DatabaseColumns columns = new DatabaseColumns();
    private DatabaseQuery queries = new DatabaseQuery();
    private Gson gson = new Gson();


    public Connection getConnectionToDB(){
        String connectionUrl =
                "jdbc:mysql://db4free.net:3306;"
                        + "database=testyprojektu;"
                        + "user=adminprojektu;"
                        + "password=testy123;"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        String oldConnectionUrl = "jdbc:mysql://db4free.net:3306/testyprojektu";

        try{
            Connection con = DriverManager.getConnection(oldConnectionUrl, "adminprojektu", "testy123");
            return con;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
//  Users Functions
    @RequestMapping(
            value = "/users",
            method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers() throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return null;
        }
        else{
            return queries.getUsers(testConnection);
        }
    }

    @RequestMapping(
            value = "/users",
            params = {"type", "data"},
            method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers(@RequestParam("type") String type, @RequestParam("data") String data) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return null;
        }
        else{
            return queries.getUsers(testConnection, type, data);
        }
    }

    @RequestMapping(
            value = "/checkUser",
            params = {"userCard"},
            method = RequestMethod.GET)
    @ResponseBody
    public User checkUser(@RequestParam("userCard") String userCard) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return null;
        }
        else{
            return queries.scanUser(testConnection, userCard);
        }
    }

    @RequestMapping(
            value = "/addUser",
            params = {"name", "lastName", "userCard"},
            method = RequestMethod.POST)
    @CrossOrigin
    @ResponseBody
    public String addUser(@RequestParam("name") String name, @RequestParam("lastName") String lastName, @RequestParam("userCard") String userCard) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return "No Connection";
        }
        else{
            queries.addUser(testConnection, name, lastName, userCard);
            return "Added";
        }
    }

    @RequestMapping(
            value = "/deleteUser",
            params = {"userCard"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@RequestParam("userCard") String userCard) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return "No Connection";
        }
        else{
            queries.deleteUser(testConnection,userCard);
            return "User deleted";
        }
    }

    //  Items Functions
    @RequestMapping(
            value = "/items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Item> getItems() throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return null;
        }
        else{
            return queries.getItems(testConnection);
        }
    }

    @RequestMapping(
            value = "/items",
            params = {"type", "data"},
            method = RequestMethod.GET)
    @ResponseBody
    public List<Item> getItems(@RequestParam("type") String type, @RequestParam("data") String data) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return null;
        }
        else{
            return queries.getItems(testConnection, type, data);
        }
    }

    @RequestMapping(
            value = "/scanItem",
            params = {"itemCode"},
            method = RequestMethod.GET)
    @ResponseBody
    public Item scanItem(@RequestParam("itemCode") String itemCode) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return null;
        }
        else{
            return queries.scanItem(testConnection, itemCode);
        }
    }

    @RequestMapping(
            value = "/addItem",
            produces = "application/json",
            method = RequestMethod.POST)
    @ResponseBody
    public String addItem(@RequestParam(value = "name", required = false) String itemCode, @RequestParam(value = "itemCode", required = false) String name) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return "No Connection";
        }
        else{
            System.out.println(name + " " + itemCode);
            queries.addItem(testConnection, name, itemCode);
            return "Added item";
        }
    }

    @RequestMapping(
            value = "/deleteItem",
            params = {"itemCode"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteItem(@RequestParam("itemCode") String itemCode) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return "No Connection";
        }
        else{
            queries.deleteItem(testConnection,itemCode);
            return "Item deleted";
        }
    }

    //  Rent Functions
    @RequestMapping(
            value = "/rents",
            method = RequestMethod.GET)
    @ResponseBody
    public String getRents() throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return "No Connection";
        }
        else{
            return gson.toJson(queries.getRents(testConnection));
        }
    }

    @RequestMapping(
            value = "/rent",
            params = {"itemCode"},
            method = RequestMethod.POST)
    @ResponseBody
    public String rentItem(@RequestParam("itemCode") String itemCode) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        int itemID = queries.scanItem(testConnection, itemCode).getId();
        if(testConnection == null){
            return "No Connection";
        }
        else{
            queries.rentItem(testConnection, 1, 1, itemID);
            return "Rent";
        }
    }

    @RequestMapping(
            value = "/return",
            params = {"itemCode"},
            method = RequestMethod.DELETE)
    @ResponseBody
    public String returnRent(@RequestParam("type") String type, @RequestParam("data") String data) throws JsonProcessingException {
        Connection testConnection = getConnectionToDB();
        if(testConnection == null){
            return "No Connection";
        }
        else{
            queries.returnItem(testConnection,1);
            return "Return";
        }
    }










    //*****************************************************
    // to będzie do jakiegoś podłączenia pod przycisk czy wyzwalacz czy cos innego


    @RequestMapping(
            value = "/remind",
            method = RequestMethod.GET)
    @ResponseBody
    public String RentRemind() throws JsonProcessingException
    {
        MailSender ms = new MailSender();

        Connection testConnection = getConnectionToDB();
        if(testConnection == null)
        {
            return "No Connection";
        }
        else
        {
            try
            {
                List<Integer> uID = queries.getUsersIDsFromRents(testConnection);
                System.out.println(uID);
                List<Integer> iID = queries.getItemsIDsFromRents(testConnection);
                System.out.println(iID);
                for(int i = 0; i < uID.size(); i++)
                {
                    ms.SendReminderMail(queries.getUserPersonalData(uID.get(i),testConnection),queries.getItemName(iID.get(i),testConnection));
                }
                return "Reminds have been sent.";
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return "Fail...";
    }

    //*****************************************************

}




