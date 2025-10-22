# GitHub Secrets Setup for Railway Deployment

To enable automatic deployment to Railway via GitHub Actions, you need to set up the following secrets in your GitHub repository.

## Required Secrets

### 1. RAILWAY_TOKEN
Your Railway API token for authentication.

**How to get it:**
1. Go to [Railway Dashboard](https://railway.app/dashboard)
2. Click on your profile (top right)
3. Go to "Account Settings"
4. Click on "Tokens" tab
5. Click "Create Token"
6. Copy the token

**How to add to GitHub:**
1. Go to your GitHub repository
2. Click "Settings" tab
3. Click "Secrets and variables" → "Actions"
4. Click "New repository secret"
5. Name: `RAILWAY_TOKEN`
6. Value: Your Railway token
7. Click "Add secret"

### 2. HF_API_KEY (Optional)
Your HuggingFace API key for AI features.

**How to get it:**
1. Go to [HuggingFace Settings](https://huggingface.co/settings/tokens)
2. Click "New token"
3. Give it a name (e.g., "QA Automation Hub")
4. Select "Read" permissions
5. Click "Generate"
6. Copy the token (starts with `hf_`)

**How to add to GitHub:**
1. Follow same steps as above
2. Name: `HF_API_KEY`
3. Value: Your HuggingFace token

## Automatic Deployment

Once these secrets are set up:

1. **Push to main branch** triggers automatic deployment
2. **Manual deployment** via "Actions" tab → "Deploy to Railway" → "Run workflow"
3. **Check deployment status** in Railway dashboard

## Deployment Process

The GitHub Action will:
1. ✅ Install Railway CLI
2. ✅ Authenticate with Railway
3. ✅ Deploy your application
4. ✅ Set environment variables
5. ✅ Provide deployment status

## Troubleshooting

### Common Issues

1. **RAILWAY_TOKEN not set**
   - Error: "No authentication token provided"
   - Solution: Add RAILWAY_TOKEN secret

2. **Railway project not linked**
   - Error: "No linked project found"
   - Solution: Deploy manually once via Railway dashboard

3. **Build failures**
   - Check "Actions" tab for detailed logs
   - Verify Dockerfile.backend is valid

### Getting Help

- **GitHub Actions logs**: Repository → Actions tab
- **Railway logs**: Railway dashboard → Your project → Deployments
- **Railway Discord**: [discord.gg/railway](https://discord.gg/railway)

## Manual Deployment Alternative

If GitHub Actions fails, you can always deploy manually:

```bash
# Install Railway CLI
npm install -g @railway/cli

# Login to Railway
railway login

# Deploy
railway up
```