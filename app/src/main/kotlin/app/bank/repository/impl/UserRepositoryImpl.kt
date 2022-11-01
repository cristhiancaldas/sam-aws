package app.bank.repository.impl

import app.bank.bean.User
import app.bank.config.Configuration
import app.bank.config.LoggerDelegate
import app.bank.config.UserFilter
import app.bank.repository.SaveUserException
import app.bank.repository.UserNotFoundException
import app.bank.repository.UserRepository
import app.bank.repository.UsersNotFoundException
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression

class UserRepositoryImpl(private val ddbClient: AmazonDynamoDB) : UserRepository {

    private val ddbMapper = DynamoDBMapper(ddbClient)
    private val log by LoggerDelegate()

    companion object {
        val instance: UserRepositoryImpl = UserRepositoryImpl(
            Configuration.getDdbClient()
        )
    }

    override fun getUser(id: Long): User {
        try {
            return ddbMapper.load(User::class.java, id)
        } catch (ex: Exception) {
            throw UserNotFoundException(id)
        }
    }

    override fun save(user: User): User {
        try {
            log.info("fun saveUser")
            ddbMapper.save<User>(user)
            return user
        } catch (ex: Exception) {
            throw SaveUserException(ex.message)
        }
    }

    override fun getUsers(userFilter: UserFilter): List<User> {
        val request = DynamoDBScanExpression()
            .withFilterExpression(userFilter.buildCriteriaExpression())
            .withExpressionAttributeValues(userFilter.buildCriteriaValues())
            .withExpressionAttributeNames(userFilter.buildCriteriaExpressionNames())
        try {
            return ddbMapper.scan(User::class.java, request)
        } catch (ex: Exception) {
            throw UsersNotFoundException(ex.message)
        }
    }
}
