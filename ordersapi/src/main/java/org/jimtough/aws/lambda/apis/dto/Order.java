package org.jimtough.aws.lambda.apis.dto;

public class Order {

	public int id;
	public String itemName;
	public int quantity;

	public Order() {
	}

	public Order(final int id, final String itemName, final int quantity) {
		this.id = id;
		this.itemName = itemName;
		this.quantity = quantity;
	}

}
