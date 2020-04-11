package pl.edu.pwr.s241223;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQuery {
    private DatabaseColumns columns = new DatabaseColumns();
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public List<User> getUsers(Connection testConnection){
        Statement stat = null;
        List<User> users = new ArrayList<User>();
        String query = String.format("SELECT * FROM %s", columns.getUsersDatabase());
        try{
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()){
                User user = new User(
                        Integer.parseInt(rs.getString(columns.getUserColumn1())),
                        rs.getString(columns.getUserColumn2()),
                        rs.getString(columns.getUserColumn3()),
                        rs.getString(columns.getUserColumn4())
                );
                users.add(user);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

    public List<User> getUsers(Connection testConnection, String type, String data){
        Statement stat = null;
        List<User> users = new ArrayList<User>();
        String query = String.format("SELECT * FROM %s WHERE %s = %s", columns.getUsersDatabase(), type, data);
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                User user = new User(
                        Integer.parseInt(rs.getString(columns.getUserColumn1())),
                        rs.getString(columns.getUserColumn2()),
                        rs.getString(columns.getUserColumn3()),
                        rs.getString(columns.getUserColumn4())
                );
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public User scanUser(Connection testConnection, String userCard){
        Statement stat = null;
        User user = null;
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", columns.getUsersDatabase(), columns.getUserColumn4(), userCard);
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                user = new User(
                        Integer.parseInt(rs.getString(columns.getUserColumn1())),
                        rs.getString(columns.getUserColumn2()),
                        rs.getString(columns.getUserColumn3()),
                        rs.getString(columns.getUserColumn4())
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void addUser(Connection testConnection, String name, String lastName, String userCard){
        Statement stat = null;
        String query = String.format("INSERT INTO %s(%s, %s, %s)"
                        + "VALUES('%s', '%s', '%s');",
                columns.getUsersDatabase(), columns.getUserColumn2(),
                columns.getUserColumn3(), columns.getUserColumn4(),
                name, lastName, userCard);
        try {
            stat = testConnection.createStatement();
            stat.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Connection testConnection, String userCard){
        Statement stat = null;
        String query = String.format("DELETE FROM %s WHERE %s ='%s'", columns.getUsersDatabase(), columns.getUserColumn4(), userCard);
        try {
            stat = testConnection.createStatement();
            stat.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Item> getItems(Connection testConnection){
        Statement stat = null;
        List<Item> items = new ArrayList<Item>();
        String query = String.format("SELECT * FROM %s", columns.getItemsDatabase());
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Item item = new Item(
                        Integer.parseInt(rs.getString(columns.getItemColumn1())),
                        rs.getString(columns.getItemColumn2()),
                        rs.getString(columns.getItemColumn3()),
                        Integer.parseInt(rs.getString(columns.getItemColumn4()))
                );
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    public List<Item> getItems(Connection testConnection, String type, String data){
        Statement stat = null;
        List<Item> items = new ArrayList<Item>();
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", columns.getItemsDatabase(), type, data);
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Item item = new Item(
                        Integer.parseInt(rs.getString(columns.getItemColumn1())),
                        rs.getString(columns.getItemColumn2()),
                        rs.getString(columns.getItemColumn3()),
                        Integer.parseInt(rs.getString(columns.getItemColumn4()))
                );

                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public Item scanItem(Connection testConnection, String itemCode){
        Statement stat = null;
        Item item = null;
        String query = String.format("SELECT * FROM %s WHERE %s = '%s'", columns.getItemsDatabase(), columns.getItemColumn3(), itemCode);
        System.out.println(query);
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                item = new Item(
                        Integer.parseInt(rs.getString(columns.getItemColumn1())),
                        rs.getString(columns.getItemColumn2()),
                        rs.getString(columns.getItemColumn3()),
                        Integer.parseInt(rs.getString(columns.getItemColumn4()))
                );
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public void addItem(Connection testConnection, String name, String itemCode){
        Statement stat = null;
        String query = String.format("INSERT INTO %s(%s, %s, %s)"
                        + "VALUES('%s', '%s', %s);",
                columns.getItemsDatabase(), columns.getItemColumn2(),
                columns.getItemColumn3(), columns.getItemColumn4(),
                name, itemCode, 0);
        System.out.println(query);
        try {
            stat = testConnection.createStatement();
            stat.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItem(Connection testConnection, String itemCode){
        Statement stat = null;
        String query = String.format("DELETE FROM %s WHERE %s ='%s'", columns.getItemsDatabase(), columns.getItemColumn3(), itemCode);
        try {
            stat = testConnection.createStatement();
            stat.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Rent> getRents(Connection testConnection){
        Statement stat = null;
        List<Rent> rents = new ArrayList<Rent>();
        String query = String.format("SELECT * FROM %s", columns.getRentsDatabase());
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                Rent rent = new Rent(
                        Integer.parseInt(rs.getString(columns.getRentColumn1())),
                        Integer.parseInt(rs.getString(columns.getRentColumn2())),
                        Integer.parseInt(rs.getString(columns.getRentColumn3())),
                        Date.valueOf(rs.getString(columns.getRentColumn4())),
                        Date.valueOf(rs.getString(columns.getRentColumn5()))
                );
                rents.add(rent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rents;
    }

    public void rentItem(Connection testConnection,int rentID, int userID, int itemID){
        Statement stat = null;
        String query = String.format("INSERT INTO %s(%s, %s, %s, %s)"
                        + "VALUES('%s', '%s', '%s', '%s');",
                columns.getRentsDatabase(), columns.getRentColumn2(),
                columns.getRentColumn3(), columns.getRentColumn4(), columns.getRentColumn5(),
                userID, itemID, dateTimeFormatter.format(LocalDateTime.now()), dateTimeFormatter.format(LocalDateTime.now()));
        try {
            stat = testConnection.createStatement();
            stat.executeUpdate(query);


            //*****************************
            // Dorobienie maila przy pozyczeniu, by Wojtek Bezen
            MailSender ms = new MailSender();
            ms.SendNewBorrowMail(getUserPersonalData(userID, testConnection), getItemName(itemID,testConnection));
            //*****************************


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnItem(Connection testConnection, int itemID){
        Statement stat = null;





        //*****************************
        String query ="";
        int userID=0;
        try
        {
            //najpierw odczytac usera który ma dany przedmiot u siebie zeby wiedziec do kogo maila wyslac
            query = String.format("SELECT * FROM %s WHERE %s = %s", columns.getRentsDatabase(), columns.getRentColumn3(), itemID);
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next())
            {
                userID = Integer.parseInt(rs.getString(columns.getRentColumn2()));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //*****************************




        try
        {
            query = String.format("DELETE FROM %s WHERE %s ='%s'", columns.getRentsDatabase(), columns.getRentColumn3(), itemID);
            stat = testConnection.createStatement();
            stat.executeUpdate(query);






            //*****************************
            MailSender ms = new MailSender();
            ms.SendReturnMail(getUserPersonalData(userID, testConnection), getItemName(itemID,testConnection));
            //*****************************





        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }







    //********************************** Wojtek Bezen - nowe funkcje pomocnicze
    public String getUserPersonalData(int userID, Connection testConnection)
    {
        String userData="";

        Statement stat = null;
        String query = String.format("SELECT * FROM %s WHERE %s = %s", columns.getUsersDatabase(), columns.getUserColumn1(), userID);
        try{
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()){
                userData = rs.getString(columns.getUserColumn2()) + " " +rs.getString(columns.getUserColumn3());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return userData;
    }

    public String getItemName(int itemID, Connection testConnection)
    {
        String itemName="";

        Statement stat = null;
        String query = String.format("SELECT * FROM %s WHERE %s = %s", columns.getItemsDatabase(), columns.getItemColumn1(), itemID);
        try{
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()){
                itemName = rs.getString(columns.getItemColumn2());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return itemName;
    }


    public List<Integer> getUsersIDsFromRents(Connection testConnection){
        Statement stat = null;
        List<Integer> usersID = new ArrayList<Integer>();
        String query = String.format("SELECT %s FROM %s", columns.getRentColumn2() ,columns.getRentsDatabase());
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next())
            {
                usersID.add(Integer.parseInt(rs.getString(columns.getRentColumn2())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersID;
    }

    public List<Integer> getItemsIDsFromRents(Connection testConnection){
        Statement stat = null;
        List<Integer> itemsID = new ArrayList<Integer>();
        String query = String.format("SELECT %s FROM %s", columns.getRentColumn3() ,columns.getRentsDatabase());
        try {
            stat = testConnection.createStatement();
            ResultSet rs = stat.executeQuery(query);
            while (rs.next())
            {
                itemsID.add(Integer.parseInt(rs.getString(columns.getRentColumn3())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemsID;
    }
    //**********************************






}
