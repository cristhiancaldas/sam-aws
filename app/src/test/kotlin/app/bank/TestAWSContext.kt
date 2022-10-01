package app.bank

import com.amazonaws.services.lambda.runtime.ClientContext
import com.amazonaws.services.lambda.runtime.CognitoIdentity
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.LambdaLogger

class TestAWSContext(private val functionName: String) : Context {
    override fun getAwsRequestId(): String {
        return ""
    }

    override fun getLogGroupName(): String {
        return "/aws/lambda/my-function"
    }

    override fun getLogStreamName(): String {
        return ""
    }

    override fun getFunctionName(): String {
        return functionName
    }

    override fun getFunctionVersion(): String {
        return ""
    }

    override fun getInvokedFunctionArn(): String {
        return ""
    }

    override fun getIdentity(): CognitoIdentity {
        return TestCognitoIdentity()
    }

    override fun getClientContext(): ClientContext {
        TODO("Not yet implemented")
    }

    override fun getRemainingTimeInMillis(): Int {
        return 0
    }

    override fun getMemoryLimitInMB(): Int {
        return 0
    }

    override fun getLogger(): LambdaLogger {
        return TestLambdaLogger()
    }
    class TestLambdaLogger : LambdaLogger {
        override fun log(message: String?) {}

        override fun log(message: ByteArray?) {}
    }
    class TestCognitoIdentity : CognitoIdentity {
        override fun getIdentityId(): String {
            TODO("Not yet implemented")
        }

        override fun getIdentityPoolId(): String {
            TODO("Not yet implemented")
        }
    }
}
