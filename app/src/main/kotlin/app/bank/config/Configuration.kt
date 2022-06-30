package app.bank.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder

object Configuration {

    const val TABLE_NAME: String = "USER_OPS"
    private lateinit var ddbClient: AmazonDynamoDB

    init {
        initDdbClient()
    }

    fun getDdbClient(): AmazonDynamoDB {
        return ddbClient
    }

    private fun initDdbClient() {
        ddbClient = AmazonDynamoDBClientBuilder.standard().build()
    }
}
