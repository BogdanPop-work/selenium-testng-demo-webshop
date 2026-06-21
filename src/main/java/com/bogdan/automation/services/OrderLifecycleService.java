package com.bogdan.automation.services;

import com.bogdan.automation.repositories.OrderRepository;
import com.bogdan.automation.clients.WarehouseApiClient;
import com.bogdan.automation.clients.WarehouseApiResponse;

public class OrderLifecycleService {

	private final OrderRepository orderRepository;
	private final WarehouseApiClient warehouseApiClient;

	public OrderLifecycleService() {
		this.orderRepository = new OrderRepository();
		this.warehouseApiClient = new WarehouseApiClient();
	}

	public void saveCompletedOrder(String orderNumber, String firstName, String lastName, String email,
			String paymentMethod, String shippingMethod) {

		orderRepository.saveCompletedOrder(orderNumber, firstName, lastName, email, paymentMethod, shippingMethod);
	}

	public boolean orderExists(String orderNumber) {
		return orderRepository.orderExists(orderNumber);
	}

	public WarehouseApiResponse sendOrderToWarehouse(String orderNumber) {

		WarehouseApiResponse response = warehouseApiClient.sendOrderToWarehouse(orderNumber);

		orderRepository.markOrderAsSentToWarehouse(orderNumber, response.warehouseReference(), response.message());

		return response;
	}

	public boolean orderWasSentToWarehouse(String orderNumber) {
		return orderRepository.orderWasSentToWarehouse(orderNumber);
	}
}