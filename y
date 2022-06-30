version = 0.1
[default]
[default.deploy]
[default.deploy.parameters]
stack_name = "mi-first-deploy"
s3_bucket = "aws-sam-cli-managed-default-samclisourcebucket-11iuqcoeeolsl"
s3_prefix = "mi-first-deploy"
region = "us-west-2"
confirm_changeset = true
capabilities = "CAPABILITY_IAM"
disable_rollback = true
parameter_overrides = "Stage=\"dev\" AppName=\"app-user\""
image_repositories = []
