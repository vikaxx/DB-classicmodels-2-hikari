package ua.alevel.jdbc;

import java.sql.*;
import java.util.Scanner;

public class Run {

    public static void main(String[] args) {
        getOfficesInfo();
        System.out.println();

        EMEATerritory();
        System.out.println();

        getOrderInfo();
        System.out.println();

        newAddress();
    }

    private static void EMEATerritory() {
        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "Select * from offices where territory = 'EMEA'";

            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int size = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 0; i < size; i++) {
                    System.out.print(resultSet.getString(i + 1) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void newAddress() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("Insert into address(country, adressLine) values (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);

            Scanner in = new Scanner(System.in);
            System.out.println("input country: ");
            String countryName = in.nextLine();

            System.out.println("input address line: ");
            String addressLine = in.nextLine();
            ps.setString(1, countryName);
            ps.setString(2, addressLine);

            ps.executeUpdate();
            ResultSet resultSet = ps.getGeneratedKeys();
            int addressId = 1;
            while (resultSet.next()) {
                addressId = resultSet.getInt(1);
            }

            Statement statement = connection.createStatement();
            String query = "Insert into person(firstName, lastName, addressId) values ('firstName', 'lastName', " + addressId + ")";
            statement.executeUpdate(query);

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void getOrderInfo() {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("Select * from orders where orderNumber = ?")) {

            System.out.println("input order's number: ");
            Scanner in = new Scanner(System.in);
            int inputOrdersNumber = in.nextInt();

            ps.setInt(1, inputOrdersNumber);
            ResultSet resultSet = ps.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int size = rsmd.getColumnCount();
            System.out.println("details of order # " + inputOrdersNumber);
            while (resultSet.next()) {
                for (int i = 0; i < size; i++) {
                    System.out.print(resultSet.getString(i + 1) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void getOfficesInfo() {
        try (Connection connection = DataSource.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "Select * from offices";

            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int size = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 0; i < size; i++) {
                    System.out.print(resultSet.getString(i + 1) + " ");
                }
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
