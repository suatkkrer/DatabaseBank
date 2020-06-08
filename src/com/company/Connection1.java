package com.company;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Set;

public class Connection1 {
    private  String userName = "root";
    private  String password = "";

    private  String dbName = "bank";
    private  String host = "localhost";

    private  int port = 3306;

    private  Connection con  = null;
    private  Statement statement = null;
    private  Statement statement1 = null;
    private  Statement statement2 = null;



     Scanner scanner = new Scanner(System.in);
    public Connection1(){
        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex){
            System.out.println("cannot find driver");
        }
        try {
            con = (Connection) DriverManager.getConnection(url,userName,password);
            System.out.println("connection is successful");
        } catch (SQLException e) {
            System.out.println("connection is unsuccessful");
        }
    }
    public void deleteCustomer()  {
        Scanner scanner = new Scanner(System.in);
        try {
            statement = con.createStatement();
            int deleteId = scanner.nextInt();
            Integer.toString(deleteId);
            String query = "Delete from customer where Customer_id = " + deleteId ;
            int deleted = statement.executeUpdate(query);
            System.out.println(deleted + ". customer is deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCustomer() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        statement = con.createStatement();
        System.out.println("Please Enter Customer Name");
        String name = scanner.nextLine();
        System.out.println("Please Enter Customer Surname");
        String surname = scanner.nextLine();
        System.out.println("Please Enter Customer Phone Number");
        String phoneNumber = scanner.nextLine();

        String query = "Insert Into customer (name,surname,phoneNumber) VALUES(" + "'" + name + "'," +  "'" + surname + "',"
                + "'" + phoneNumber + "')";

        statement.executeUpdate(query);

    }
    public  void bringCustomer() throws SQLException {
        String query = "Select * From customer";

        statement = con.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            int id = rs.getInt("Customer_id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String phoneNumber = rs.getString("phoneNumber");

            System.out.println("ID: " + id + " Name: " +name + " Surname: " +surname + " PhoneNumber: " + phoneNumber);

        }
    }
//    public  void enterAccount() throws SQLException {
//        Scanner scanner = new Scanner(System.in);
//        statement = con.createStatement();
//        bringCustomer();
//        System.out.println("Please enter your id number");
//        String idNumber = scanner.nextLine();
//
//        String query1 = "Select * From customer where Customer_id = " + idNumber;
//        ResultSet rs1 = statement.executeQuery(query1);
//        while (rs1.next()){
//            String name = rs1.getString("name");
//            String surname = rs1.getString("surname");
//            System.out.println("Welcome " + name + " " + surname);
//        }
//        String query = "Select * From account where Customer_fk = " + idNumber;
//        ResultSet rs = statement.executeQuery(query);
//        while (rs.next()) {
//            int id = rs.getInt("Customer_fk");
//            double balance = rs.getInt("balance");
//            System.out.println("User ID " + id + " Your current balance is " + balance );
//        }
//    }
    public  void depositMoney() throws SQLException {
        statement = con.createStatement();
        System.out.println("Please enter your id number for making transaction");
        String idNumber = scanner.nextLine();
        System.out.println("Enter the amount of money you want to deposit");
        double deposit = scanner.nextDouble();
        scanner.nextLine();
        Double.toString(deposit);
        String query = "Update account Set balance = balance + " + deposit + " where Customer_fk = " + idNumber ;
        statement.executeUpdate(query);
    }

    public void moneyTransfer() throws SQLException {
        statement = con.createStatement();
        statement1 = con.createStatement();
        statement2 = con.createStatement();
        bringCustomer();
        System.out.println("Please enter your id number for making transaction");
        String idNumber = scanner.nextLine();
        System.out.println("Please enter the id number of the customer you want to send the money");
        String idNumber2 = scanner.nextLine();
        System.out.println("Enter the amount of money you want to Transfer");
        double transferMoney = scanner.nextDouble();
        scanner.nextLine();
        Double.toString(transferMoney);
        String query1 = "Select * From account where Customer_fk = " + idNumber;



        ResultSet rs = statement1.executeQuery(query1);
        while (rs.next()) {
            double balance = rs.getDouble("balance");
            if (balance < transferMoney) {
                System.out.println("You don't have enough money");
            } else if (balance >= transferMoney) {
                String query = "Update account Set balance = balance - " + transferMoney + " where Customer_fk = " + idNumber;
                String query2 = "Update account Set balance = balance + " + transferMoney + " where Customer_fk = " + idNumber2;
                statement.executeUpdate(query);
                statement2.executeUpdate(query2);
            }
        }
    }

    public void withdrawMoney() throws SQLException {
        statement = con.createStatement();
        statement1 = con.createStatement();
        System.out.println("Please enter your id number for making transaction");
        String idNumber = scanner.nextLine();
        System.out.println("Enter the amount of money you want to withdraw");
        double withdraw = scanner.nextDouble();
        scanner.nextLine();
        Double.toString(withdraw);
        String query1 = "Select * From account where Customer_fk = " + idNumber;
        ResultSet rs = statement1.executeQuery(query1);
        while (rs.next()) {
            double balance = rs.getDouble("balance");
            if (balance < withdraw) {
                System.out.println("You don't have enough money");
            } else if (balance >= withdraw) {
                String query = "Update account Set balance = balance - " + withdraw + " where Customer_fk = " + idNumber;
                statement.executeUpdate(query);
            }

        }
    }
    public void currentBalance() throws SQLException {
        bringCustomer();
        System.out.println("Please enter your customer ID");
        String balanceNumber = scanner.nextLine();
        String query = "Select * From account where Customer_fk = " + balanceNumber;
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()){
            double balance = rs.getDouble("balance");
            System.out.println("Your balance is " + balance);
        }
        statement.executeQuery(query);
    }
    public void adminPage() throws SQLException {
        statement = con.createStatement();
        System.out.println("Please enter your username");
        String user = scanner.nextLine();
        System.out.println("Please enter your password");
        String pass = scanner.nextLine();
        String query = "Select * From banker";
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()){
            int id = rs.getInt("Customer_fk");
            String username = rs.getString("username");
            String password = rs.getString("password");

            if (user.equals(username) && pass.equals(password)){
                System.out.println("You successfully logged in");
                String options = "1. Add Customer\n" +
                                 "2. Delete Customer\n" +
                                 "3. Show Customers\n" +
                                 "4. Quit";
                while (true){
                    System.out.println(options);
                    String opt = scanner.nextLine();
                    if (opt.equals("1")){
                        addCustomer();
                    } else if (opt.equals("2")){
                        bringCustomer();
                        deleteCustomer();
                    } else if (opt.equals("3")){
                        bringCustomer();
                    } else if (opt.equals("4")){
                        break;
                    }
                }
            } else {
                System.out.println("Your username or password is wrong");
            }
        }
    }
    public void showUserID() throws SQLException {
        String query = "Select * From customer";

            statement = con.createStatement();
            ResultSet rs  = statement.executeQuery(query);

            while (rs.next()){
                int id = rs.getInt("Customer_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String phoneNumber = rs.getString("phoneNumber");

                System.out.println("ID: " + id + " Name: " +name + " Surname: " +surname + " PhoneNumber: " + phoneNumber);
            }
            statement.executeQuery(query);
    }

    public static void main(String[] args) throws SQLException, InterruptedException {
        Connection1 connection1 = new Connection1();
        Scanner scanner = new Scanner(System.in);

        String options = "1. Show Customer ID\n" +
                "2. Current Balance\n" +
                "3. Deposit Money\n" +
                "4. Withdraw Money \n" +
                "5. Transfer Money \n" +
                "6. Admin Page\n" +
                "7. Quit\n";

        while (true){
            System.out.println("\n******************************\n");
            System.out.println(options);
            System.out.println("******************************");
            String option = scanner.nextLine();
            if (option.equals("1")){
                connection1.showUserID();
            } else if (option.equals("2")){
                connection1.currentBalance();
            } else if (option.equals("3")){
                connection1.depositMoney();
            } else if (option.equals("4")){
                connection1.withdrawMoney();
            } else if (option.equals("5")){
                connection1.moneyTransfer();
            } else if (option.equals("6")){
                connection1.adminPage();
            } else if (option.equals("7")){
                System.out.println("See you later");
                break;
            }
            Thread.sleep(1000);
        }
    }
}
