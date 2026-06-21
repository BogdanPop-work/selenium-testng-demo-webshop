package com.bogdan.automation.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bogdan.automation.database.DatabaseManager;

public class OrderRepository {

	public void saveCompletedOrder(String orderNumber, String firstName, String lastName, String email,
			String paymentMethod, String shippingMethod) {

		String insertCustomerSql = """
				INSERT INTO customers (first_name, last_name, email)
				VALUES (?, ?, ?)
				""";

		String insertOrderSql = """
				INSERT INTO orders (order_number, customer_id, order_status)
				VALUES (?, ?, ?)
				""";

		String insertPaymentSql = """
				INSERT INTO payments (order_id, payment_method, payment_status)
				VALUES (?, ?, ?)
				""";

		String insertShipmentSql = """
				INSERT INTO shipments (order_id, shipping_method, shipment_status)
				VALUES (?, ?, ?)
				""";

		try (Connection connection = DatabaseManager.getConnection()) {

			connection.setAutoCommit(false);

			long customerId = insertCustomer(connection, insertCustomerSql, firstName, lastName, email);
			long orderId = insertOrder(connection, insertOrderSql, orderNumber, customerId);
			insertPayment(connection, insertPaymentSql, orderId, paymentMethod);
			insertShipment(connection, insertShipmentSql, orderId, shippingMethod);

			connection.commit();

		} catch (Exception exception) {
			throw new RuntimeException("Failed to save completed order", exception);
		}
	}

	public boolean orderExists(String orderNumber) {

		String sql = """
				SELECT COUNT(*)
				FROM orders
				WHERE order_number = ?
				""";

		try (Connection connection = DatabaseManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, orderNumber);

			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				return resultSet.getInt(1) == 1;
			}

		} catch (SQLException exception) {
			throw new RuntimeException("Failed to check if order exists", exception);
		}
	}

	private long insertCustomer(Connection connection, String sql, String firstName, String lastName, String email)
			throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, email);

			statement.executeUpdate();

			return getGeneratedId(statement);
		}
	}

	private long insertOrder(Connection connection, String sql, String orderNumber, long customerId)
			throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, orderNumber);
			statement.setLong(2, customerId);
			statement.setString(3, "COMPLETED");

			statement.executeUpdate();

			return getGeneratedId(statement);
		}
	}

	private void insertPayment(Connection connection, String sql, long orderId, String paymentMethod)
			throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setLong(1, orderId);
			statement.setString(2, paymentMethod);
			statement.setString(3, "CONFIRMED");

			statement.executeUpdate();
		}
	}

	private void insertShipment(Connection connection, String sql, long orderId, String shippingMethod)
			throws SQLException {

		try (PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setLong(1, orderId);
			statement.setString(2, shippingMethod);
			statement.setString(3, "READY_FOR_WAREHOUSE");

			statement.executeUpdate();
		}
	}

	private long getGeneratedId(PreparedStatement statement) throws SQLException {

		try (ResultSet generatedKeys = statement.getGeneratedKeys()) {

			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			}

			throw new SQLException("No generated ID returned");
		}
	}

//	public void printOrders() {
//
//		String sql = """
//				SELECT order_number, order_status
//				FROM orders
//				""";
//
//		try (Connection connection = DatabaseManager.getConnection();
//				PreparedStatement statement = connection.prepareStatement(sql);
//				ResultSet resultSet = statement.executeQuery()) {
//
//			while (resultSet.next()) {
//				System.out.printf("Order: %s | Status: %s%n", resultSet.getString("order_number"),
//						resultSet.getString("order_status"));
//			}
//
//		} catch (SQLException exception) {
//			throw new RuntimeException("Failed to print orders", exception);
//		}
//
//	}

	public void markOrderAsSentToWarehouse(String orderNumber, String warehouseReference, String eventMessage) {

		String updateOrderSql = """
				UPDATE orders
				SET order_status = ?
				WHERE order_number = ?
				""";

		String updateShipmentSql = """
				UPDATE shipments
				SET shipment_status = ?, warehouse_reference = ?
				WHERE order_id = (
				    SELECT id FROM orders WHERE order_number = ?
				)
				""";

		String insertWarehouseEventSql = """
				INSERT INTO warehouse_events (order_id, event_type, event_status, event_message)
				VALUES (
				    (SELECT id FROM orders WHERE order_number = ?),
				    ?, ?, ?
				)
				""";

		try (Connection connection = DatabaseManager.getConnection()) {

			connection.setAutoCommit(false);

			try (PreparedStatement statement = connection.prepareStatement(updateOrderSql)) {
				statement.setString(1, "SENT_TO_WAREHOUSE");
				statement.setString(2, orderNumber);
				statement.executeUpdate();
			}

			try (PreparedStatement statement = connection.prepareStatement(updateShipmentSql)) {
				statement.setString(1, "PROCESSING");
				statement.setString(2, warehouseReference);
				statement.setString(3, orderNumber);
				statement.executeUpdate();
			}

			try (PreparedStatement statement = connection.prepareStatement(insertWarehouseEventSql)) {
				statement.setString(1, orderNumber);
				statement.setString(2, "ORDER_SENT");
				statement.setString(3, "ACCEPTED");
				statement.setString(4, eventMessage);
				statement.executeUpdate();
			}

			connection.commit();

		} catch (SQLException exception) {
			throw new RuntimeException("Failed to mark order as sent to warehouse", exception);
		}
	}

	public boolean orderWasSentToWarehouse(String orderNumber) {

		String sql = """
				SELECT COUNT(*)
				FROM orders o
				JOIN shipments s ON s.order_id = o.id
				JOIN warehouse_events we ON we.order_id = o.id
				WHERE o.order_number = ?
				  AND o.order_status = 'SENT_TO_WAREHOUSE'
				  AND s.shipment_status = 'PROCESSING'
				  AND s.warehouse_reference IS NOT NULL
				  AND we.event_type = 'ORDER_SENT'
				  AND we.event_status = 'ACCEPTED'
				""";

		try (Connection connection = DatabaseManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, orderNumber);

			try (ResultSet resultSet = statement.executeQuery()) {
				resultSet.next();
				return resultSet.getInt(1) == 1;
			}

		} catch (SQLException exception) {
			throw new RuntimeException("Failed to validate warehouse order status", exception);
		}
	}
}