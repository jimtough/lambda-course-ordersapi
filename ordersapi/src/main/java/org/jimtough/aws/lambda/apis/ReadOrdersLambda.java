package org.jimtough.aws.lambda.apis;

import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jimtough.aws.lambda.apis.dto.Order;

public class ReadOrdersLambda {

	public APIGatewayProxyResponseEvent getOrders(
			APIGatewayProxyRequestEvent request)
	throws JsonProcessingException {
		AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		ScanResult scanResult = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("ORDERS_TABLE")));
		List<Order> orders =
			scanResult.getItems().stream()
				.map(item->new Order(
					Integer.parseInt(item.get("id").getN()),
				    item.get("itemName").getS(),
				    Integer.parseInt(item.get("quantity").getN())))
				.collect(Collectors.toList());
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonOutput = objectMapper.writeValueAsString(orders);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);
	}

}
