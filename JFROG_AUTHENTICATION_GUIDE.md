# JFrog Artifactory Authentication Best Practices

## 🔐 Authentication Methods (Ranked by Security)

### 1. **Access Tokens (Most Secure - Recommended for Production)**
```bash
# Set environment variables
export JFROG_USER="your-username"
export JFROG_TOKEN="your-access-token"

# In settings.xml, use:
<username>${env.JFROG_USER}</username>
<password>${env.JFROG_TOKEN}</password>
```

**How to get Access Token:**
1. Log into JFrog Artifactory
2. Go to User Profile → Edit Profile → Generate Access Token
3. Copy the generated token

### 2. **Environment Variables (Good for CI/CD)**
```bash
# Set these in your CI/CD pipeline or shell
export JFROG_USER="your-username"
export JFROG_TOKEN="your-access-token"
export JFROG_INSTANCE="artifactory.stage.0658b-techopscore.com"
```

### 3. **Encrypted Passwords (Good for Local Development)**
```bash
# Encrypt your password
mvn --encrypt-password

# Use the encrypted value in settings.xml
<password>{encrypted-password-here}</password>
```

## 🚀 Deployment Commands

### Deploy to JFrog Artifactory:
```bash
mvn clean deploy
```

**This will automatically deploy:**
- **Releases** → `maven-appcode-releases` repository
- **Snapshots** → `maven-appcode-snapshots` repository

## 🔧 Configuration Files

### settings.xml (Authentication)
- **Location**: `~/.m2/settings.xml` (user) or `$MAVEN_HOME/conf/settings.xml` (global)
- **Contains**: Server credentials, repository configurations
- **Security**: Never commit this file to version control

### pom.xml (Repository URLs)
- **Location**: Project root
- **Contains**: Repository URLs, distribution management
- **Security**: Safe to commit (no credentials)

## 🌍 Environment Variables Setup

### For Local Development:
```bash
# Add to ~/.bashrc or ~/.zshrc
export JFROG_USER="your-username"
export JFROG_TOKEN="your-access-token"
export JFROG_INSTANCE="artifactory.stage.0658b-techopscore.com"

# Reload shell
source ~/.bashrc
```

### For CI/CD (GitHub Actions):
```yaml
env:
  JFROG_USER: ${{ secrets.JFROG_USER }}
  JFROG_TOKEN: ${{ secrets.JFROG_TOKEN }}
  JFROG_INSTANCE: artifactory.stage.0658b-techopscore.com
```

### For CI/CD (Jenkins):
```groovy
environment {
    JFROG_USER = credentials('jfrog-user')
    JFROG_TOKEN = credentials('jfrog-token')
    JFROG_INSTANCE = 'artifactory.stage.0658b-techopscore.com'
}
```

## 📝 Required Updates

### 1. Update Repository URLs
✅ **Already configured with your instance:**
- **Instance**: `https://artifactory.stage.0658b-techopscore.com/`
- **Repositories**: `maven-appcode-releases`, `maven-appcode-snapshots`, `maven-central`

### 2. Set Credentials
✅ **GitHub Actions secrets configured:**
- `JFROG_USER` - Your JFrog username
- `JFROG_TOKEN` - Your JFrog access token

**For local development, set:**
```bash
export JFROG_USER="your-username"
export JFROG_TOKEN="your-access-token"
```

### 3. Test Authentication
```bash
# Test connection to JFrog
mvn help:effective-settings

# Validate POM
mvn validate
```

## 🛡️ Security Best Practices

1. **Never commit credentials** to version control
2. **Use API keys** instead of passwords when possible
3. **Rotate credentials** regularly
4. **Use environment variables** for CI/CD
5. **Encrypt passwords** for local development
6. **Limit permissions** - use read-only accounts when possible

## 🔍 Troubleshooting

### Common Issues:
- **Authentication failed**: Check username/password/API key
- **Repository not found**: Verify repository name and URL
- **Permission denied**: Check user permissions in JFrog
- **Connection timeout**: Verify network access to JFrog instance

### Debug Commands:
```bash
# Validate POM
mvn validate

# Check effective settings
mvn help:effective-settings

# Test deployment (dry run)
mvn clean deploy -DdryRun=true
```

## 📚 Additional Resources

- [JFrog Artifactory Documentation](https://www.jfrog.com/confluence/)
- [Maven Settings Reference](https://maven.apache.org/settings.html)
- [Maven Distribution Management](https://maven.apache.org/pom.html#Distribution_Management)
