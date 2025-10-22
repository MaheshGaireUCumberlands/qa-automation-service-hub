# 🚀 Railway Deployment Guide

## Quick Deploy to Railway (Free Tier)

Railway offers an excellent free tier perfect for hosting the QA Automation Service Hub. Follow these steps for a complete deployment.

### 📋 Prerequisites

1. **GitHub Account** with your repository
2. **Railway Account** (sign up at [railway.app](https://railway.app) using GitHub)
3. **HuggingFace API Key** (free at [huggingface.co](https://huggingface.co/settings/tokens))

### 🚀 One-Click Deployment

#### Method 1: Deploy Button (Easiest)

Click this button to deploy instantly:

[![Deploy on Railway](https://railway.app/button.svg)](https://railway.app/template/your-template-id)

#### Method 2: Manual Deployment

1. **Fork/Clone the Repository**
   ```bash
   git clone https://github.com/MaheshGaireUCumberlands/qa-automation-service-hub
   ```

2. **Login to Railway**
   - Visit [railway.app](https://railway.app)
   - Sign in with GitHub
   - Go to your Dashboard

3. **Deploy Backend Service**
   - Click "New Project" → "Deploy from GitHub repo"
   - Select `qa-automation-service-hub`
   - Railway will auto-detect the Dockerfile and deploy

4. **Configure Environment Variables**
   In the Railway dashboard, set these variables:
   ```
   HF_API_KEY=your-huggingface-api-key-here
   SPRING_PROFILES_ACTIVE=production
   SERVER_PORT=8080
   RAILWAY_ENVIRONMENT=production
   ```

5. **Deploy Frontend Service**
   - Add a new service to your project
   - Connect the same GitHub repository
   - Set build command to use `Dockerfile.frontend`

### 🔧 Configuration Details

#### Backend Configuration
- **Build**: Dockerfile.backend (automatically detected)
- **Port**: 8080 (Railway auto-assigns public URL)
- **Health Check**: `/actuator/health`
- **Memory**: 512MB (within free tier limits)

#### Frontend Configuration  
- **Build**: Dockerfile.frontend
- **Port**: 80 (Railway handles proxy)
- **Static Assets**: Served via Nginx
- **API Proxy**: Configured to route `/api/*` to backend

#### Environment Variables Explained

| Variable | Description | Example Value |
|----------|-------------|---------------|
| `HF_API_KEY` | HuggingFace API key for AI features | `hf_xxxxxxxxxxxx` |
| `SPRING_PROFILES_ACTIVE` | Spring Boot profile | `production` |
| `SERVER_PORT` | Backend port (Railway auto-detects) | `8080` |
| `RAILWAY_ENVIRONMENT` | Deployment environment | `production` |

### 🌐 Service URLs

Once deployed, Railway provides URLs like:
- **Backend API**: `https://qa-automation-backend-production.up.railway.app`
- **Frontend**: `https://qa-automation-frontend-production.up.railway.app`
- **Health Check**: `https://qa-automation-backend-production.up.railway.app/actuator/health`

### 🧪 Testing the Deployment

1. **Check Backend Health**
   ```bash
   curl https://your-backend-url.railway.app/actuator/health
   ```

2. **Test MCP API**
   ```bash
   curl -X POST https://your-backend-url.railway.app/api/v1/mcp/tools/analyze_with_ai/call \
     -H "Content-Type: application/json" \
     -d '{"data":{"test_data":"sample"},"analysis_type":"summary","ai_provider":"mock"}'
   ```

3. **Visit Frontend**
   Open `https://your-frontend-url.railway.app` in browser

### 📊 Resource Usage (Free Tier)

Railway Free Tier Limits:
- ✅ **512MB RAM** - Perfect for our Spring Boot app
- ✅ **1GB Storage** - More than enough for our application
- ✅ **100GB Transfer** - Generous for testing and development
- ✅ **500 hours/month** - ~21 days uptime (great for demos)

### 🔄 Continuous Deployment

Railway automatically redeploys when you push to your main branch:
1. Push changes to GitHub
2. Railway detects changes
3. Automatically rebuilds and deploys
4. Zero-downtime deployment

### 🐛 Troubleshooting

#### Common Issues

1. **Build Failures**
   ```bash
   # Check Railway logs in dashboard
   # Ensure Dockerfile.backend and Dockerfile.frontend are valid
   ```

2. **Environment Variables**
   ```bash
   # Verify all required env vars are set in Railway dashboard
   # Check that HF_API_KEY is valid
   ```

3. **Health Check Failures**
   ```bash
   # Backend health check: /actuator/health
   # Frontend health check: /
   # Increase healthcheck timeout if needed
   ```

4. **Memory Issues**
   ```bash
   # Our app is configured for 512MB max
   # Monitor memory usage in Railway metrics
   ```

### 🔐 Security Best Practices

1. **Environment Variables**
   - Never commit API keys to code
   - Use Railway's encrypted environment variables
   - Rotate API keys regularly

2. **Domain Security**
   - Railway provides HTTPS by default
   - Consider custom domain for production use

3. **CORS Configuration**
   - Frontend configured to call backend API
   - CORS properly configured for Railway domains

### 💰 Cost Optimization

**Free Tier Strategy:**
- Deploy during development/testing phases
- Use sleep/wake features to conserve hours
- Monitor usage in Railway dashboard
- Upgrade to Pro ($5/month) for production

**Pro Tier Benefits:**
- Unlimited hours
- Custom domains
- Enhanced metrics
- Priority support

### 🔄 Updating Your Deployment

```bash
# Method 1: Git push (automatic)
git add .
git commit -m "Update deployment"
git push origin main

# Method 2: Railway CLI
npm install -g @railway/cli
railway login
railway deploy
```

### 📞 Support Resources

- **Railway Docs**: [docs.railway.app](https://docs.railway.app)
- **Railway Discord**: [discord.gg/railway](https://discord.gg/railway)
- **GitHub Issues**: [Your repository issues](https://github.com/MaheshGaireUCumberlands/qa-automation-service-hub/issues)

### ✅ Deployment Checklist

- [ ] Railway account created and linked to GitHub
- [ ] Repository connected to Railway
- [ ] Environment variables configured
- [ ] Backend service deployed and healthy
- [ ] Frontend service deployed and accessible
- [ ] MCP API endpoints tested
- [ ] Health checks passing
- [ ] Custom domain configured (optional)

---

## 🎉 **Success!**

Your QA Automation Service Hub is now live on Railway! 

**Next Steps:**
1. Test all MCP endpoints
2. Share the URLs with your team
3. Monitor usage in Railway dashboard
4. Consider upgrading to Pro for production use

**Demo URLs:**
- Frontend: `https://your-app.railway.app`
- API Health: `https://your-api.railway.app/actuator/health`
- Swagger UI: `https://your-api.railway.app/swagger-ui.html`