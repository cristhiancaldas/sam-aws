package app.bank.shared

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder

class TestConfig {
    private var ddbHost: String? = null
    private var ddbRegion: String? = null

    fun withDdbHost(ddbHost: String): TestConfig {
        this.ddbHost = ddbHost
        return this
    }

    fun withDdbRegion(ddbRegion: String): TestConfig {
        this.ddbRegion = ddbRegion
        return this
    }

    fun getDdbClient(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder
            .standard()
            .withEndpointConfiguration(AwsClientBuilder.EndpointConfiguration(ddbHost, ddbRegion))
            .build()
    }
}