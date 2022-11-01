package app.bank.config

import com.amazonaws.services.dynamodbv2.model.AttributeValue

interface DynamoDBPredicateBuilder {
    fun buildCriteriaExpression(): String?
    fun buildCriteriaValues(): Map<String, AttributeValue>?
    fun buildCriteriaExpressionNames(): Map<String, String>
}
