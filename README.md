# jfrog-maven-demo
MAVEN demo repo pulling and publishing to Jfrog Artifactory


🛠️ Configured JFrog Repositories
1️⃣ Maven (maven-appcode-dev)

✅ Pulls dependencies from maven-central (JFrog remote repo).
✅ Publishes builds to maven-appcode-dev (JFrog private repo).
✅ Uses OIDC for authentication in GitHub Actions.


Benefits of OIDC Authentication in JFrog
🚀 Eliminates hardcoded credentials (No API keys, No service users).
🔒 Improves security (Dynamic authentication, token-based access).
🔄 Enforces least privilege access (GitHub Actions can access only authorized JFrog services).
⚡ Simplifies automation & CI/CD workflows.
