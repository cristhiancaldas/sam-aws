package app.bank.config

import com.amazonaws.services.dynamodbv2.model.AttributeValue

data class UserFilter(
    private val id: Long? = null,
    private val firstName: String? = null,
    private val lastName: String? = null,
    private val email: String? = null,
    private val phone: String? = null,
    private val country: String? = null,
    private val region: String? = null,
    private val active: String? = null,
    private val separator: DynamoSeparator = DynamoSeparator.AND
) : DynamoDBPredicateBuilder {
    companion object {
        val instance = UserFilter()
    }
    override fun buildCriteriaExpression(): String? {
        val criteria = mutableListOf<String>()
        id?.apply { criteria.add("#ID_USER_PK = :id_user_pk") }
        firstName?.apply { criteria.add("#FIRST_NAME = :first_name") }
        lastName?.apply { criteria.add("#LAST_NAME = :last_name") }
        email?.apply { criteria.add("#EMAIL = :email") }
        phone?.apply { criteria.add("#PHONE = :phone") }
        country?.apply { criteria.add("#COUNTRY = :country") }
        region?.apply { criteria.add("#REGION = :region") }
        active?.apply { criteria.add("#ACTIVE = :active") }
        return criteria.joinToString(separator = "${separator.value}")
    }

    override fun buildCriteriaValues(): Map<String, AttributeValue>? {
        val criteria = mutableMapOf<String, AttributeValue>()
        id?.apply { criteria[":id_user_pk"] = AttributeValue().withN(id.toString()) }
        firstName?.apply { criteria[":first_name"] = AttributeValue().withS(firstName) }
        lastName?.apply { criteria[":last_name"] = AttributeValue().withS(lastName) }
        email?.apply { criteria[":email"] = AttributeValue().withS(email) }
        phone?.apply { criteria[":phone"] = AttributeValue().withS(phone) }
        country?.apply { criteria[":country"] = AttributeValue().withS(country) }
        region?.apply { criteria[":region"] = AttributeValue().withS(region) }
        active?.apply { criteria[":active"] = AttributeValue().withS(active) }
        return criteria
    }

    override fun buildCriteriaExpressionNames(): Map<String, String> {
        val expressionNames = mutableMapOf<String, String>()
        id?.apply { expressionNames["#ID_USER_PK"] = "ID_USER_PK" }
        firstName?.apply { expressionNames["#FIRST_NAME"] = "FIRST_NAME" }
        lastName?.apply { expressionNames["#LAST_NAME"] = "LAST_NAME" }
        email?.apply { expressionNames["#EMAIL"] = "EMAIL" }
        phone?.apply { expressionNames["#PHONE"] = "PHONE" }
        country?.apply { expressionNames["#COUNTRY"] = "COUNTRY" }
        region?.apply { expressionNames["#REGION"] = "REGION" }
        active?.apply { expressionNames["#ACTIVE"] = "ACTIVE" }
        return expressionNames
    }
}
