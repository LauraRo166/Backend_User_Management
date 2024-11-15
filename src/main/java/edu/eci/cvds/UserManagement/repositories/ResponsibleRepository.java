package edu.eci.cvds.UserManagement.repositories;

import edu.eci.cvds.UserManagement.model.Responsible;
import edu.eci.cvds.UserManagement.repositories.connection.DBConnection;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.sql.SQLException;


/**
 * ResponsibleRepository is a data access class that manages database interactions for
 * the Responsible entity. It allows saving a new Responsible record and retrieving a
 * Responsible by document type and number.
 */
@Repository
public class ResponsibleRepository {

    /**
     * Saves a Responsible entity to the database.
     *
     * @param responsible The Responsible entity to be saved.
     * @throws SQLException if an SQL exception occurs during the save operation.
     */
    public void saveResponsible(Responsible responsible) throws SQLException {
        String sql = "INSERT INTO public.responsibles (document, siteDocument, name, phoneNumber, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, responsible.getDocument());
            statement.setString(2, responsible.getSiteDocument());
            statement.setString(3, responsible.getName());
            statement.setString(4, responsible.getPhoneNumber());
            statement.setString(5, responsible.getEmail());
            statement.executeUpdate();
        }
    }


    /**
     * Finds and retrieves a Responsible entity by its document type and number.
     *
     * @param responsibleDocNumber The document number of the Responsible.
     * @return The Responsible entity if found, otherwise null.
     * @throws SQLException if an SQL exception occurs during the retrieval.
     */
    public Responsible findResponsibleByDocument(Long responsibleDocNumber) throws SQLException {
        String sql = "SELECT * FROM public.responsibles WHERE document = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, responsibleDocNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Responsible(
                            resultSet.getLong("document"),
                            resultSet.getString("document_site"),
                            resultSet.getString("name"),
                            resultSet.getString("phone_number"),
                            resultSet.getString("email")
                    );
                } else {
                    return null;
                }
            }
        }
    }

}


