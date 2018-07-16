package services;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import domain.Card;
import java.sql.Types;

public class CardService implements Service<Card> {

    Connection connection;

    public CardService() {
        super();
    }

    public CardService(Connection connection) {
        super();
        this.connection = connection;
    }

    public boolean add(Card card) {
        try {
            //get order id
            CallableStatement getCardId = connection.prepareCall(
                    "{?=call AssignCreditCardId}");
            getCardId.registerOutParameter(1, Types.VARCHAR);
            getCardId.execute();
            String cardId = getCardId.getString(1);

            String userId = card.getUserId();
            String cardNumber = card.getCardNumber();
            Date expiryDate = card.getExpiryDate();
            String securityCode = card.getSecurityCode();

            CallableStatement oCSF = connection.prepareCall("{call sp_insert_card(?,?,?,?,?)}");
            oCSF.setString(1, cardId);
            oCSF.setString(2, userId);
            oCSF.setString(3, cardNumber);
            oCSF.setDate(4, expiryDate);
            oCSF.setString(5, securityCode);
            oCSF.executeQuery();
            oCSF.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
            return false;
        }
    }

    public void deleteById(String id) {
        try {
            // sql procedure for this exist
            Statement cardsSt = connection.createStatement();
            cardsSt.executeQuery("Delete from cards where card_id = " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    @Override
    public ArrayList<Card> getAll() {

        ArrayList<Card> cards = new ArrayList<>();

        try {
            Statement cardsSt = connection.createStatement();
            ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards");

            while (cardsRs.next()) {
                Card card = new Card(
                        cardsRs.getString(1),
                        cardsRs.getString(2),
                        cardsRs.getString(3),
                        cardsRs.getDate(4),
                        cardsRs.getString(5)
                );
                cards.add(card);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return cards;
    }

    @Override
    public Card getById(String id) {
        Card card = null;

        try {
            Statement cardsSt = connection.createStatement();
            ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards where card_id = " + id);

            cardsRs.next();
            card = new Card(
                    cardsRs.getString(1),
                    cardsRs.getString(2),
                    cardsRs.getString(3),
                    cardsRs.getDate(4),
                    cardsRs.getString(5)
            );
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }

        return card;
    }

    @Override
    public void update(Card card) {
        try {
            String cardId = card.getCardId();
            String userId = card.getUserId();
            String cardNumber = card.getCardNumber();
            Date expiryDate = card.getExpiryDate();
            String securityCode = card.getSecurityCode();

            try (CallableStatement oCSF = connection.prepareCall("{?=call sp_update_card(?,?,?,?,?)}")) {
                oCSF.setString(2, cardId);
                oCSF.setString(3, userId);
                oCSF.setString(4, cardNumber);
                oCSF.setDate(5, expiryDate);
                oCSF.setString(6, securityCode);
                oCSF.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
    }

    public ArrayList<Card> getUserCards(String userId) {

        ArrayList<Card> cards = new ArrayList<>();

        try {
            Statement cardsSt = connection.createStatement();
            ResultSet cardsRs = cardsSt.executeQuery("Select * from Cards where userId = '" + userId + "'");

            while (cardsRs.next()) {
                Card card = new Card(
                        cardsRs.getString(1),
                        cardsRs.getString(2),
                        cardsRs.getString(3),
                        cardsRs.getDate(4),
                        cardsRs.getString(5)
                );
                cards.add(card);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.err.println("Error executing query!");
        }
        return cards;
    }

}
