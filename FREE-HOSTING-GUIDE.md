# Deployment Guide - Free Hosting Platforms

This guide covers deploying the QA Automation Service Hub to various free hosting platforms.

## 🎯 **Recommended Free Platforms**

### 1. **Railway** (Recommended)
- ✅ **Free Tier**: $5/month credit (generous for small apps)
- ✅ **Docker Support**: Native Docker container deployment
- ✅ **Automatic HTTPS**: SSL certificates included
- ✅ **Easy Setup**: One-command deployment
- ✅ **Database Support**: PostgreSQL, MySQL, Redis included

### 2. **Render**
- ✅ **Free Tier**: 750 hours/month (sufficient for demos)
- ✅ **Docker Support**: Full Docker container support
- ✅ **Automatic HTTPS**: SSL certificates included
- ✅ **Easy Setup**: GitHub integration
- ⚠️ **Sleep Mode**: Services sleep after 15 minutes of inactivity

### 3. **Fly.io**
- ✅ **Free Tier**: 3 small VMs, 160GB bandwidth
- ✅ **Docker Support**: Native Docker deployment
- ✅ **Global Deployment**: Edge locations worldwide
- ⚠️ **Complex Setup**: Requires more configuration

## 🚀 **Railway Deployment (Recommended)**

### Prerequisites
- GitHub repository with your code
- Railway account (free)

### Step 1: Setup Railway Project
```bash
# Install Railway CLI
curl -fsSL https://railway.app/install.sh | sh

# Login to Railway
railway login

# Initialize project
railway new qa-automation-hub
cd qa-automation-hub
```

### Step 2: Deploy Backend Service
```bash
# Create backend service
railway add --name backend

# Set environment variables
railway variables set SPRING_PROFILES_ACTIVE=production
railway variables set HF_API_KEY=your-huggingface-api-key
railway variables set SERVER_PORT=8080

# Deploy backend
railway up --service backend --dockerfile Dockerfile.backend
```

### Step 3: Deploy Frontend Service
```bash
# Create frontend service
railway add --name frontend

# Set environment variables
railway variables set BACKEND_URL=https://backend-production.up.railway.app

# Deploy frontend
railway up --service frontend --dockerfile Dockerfile.frontend
```

### Step 4: Configure Custom Domains (Optional)
```bash
# Add custom domain
railway domain add yourdomain.com --service frontend
railway domain add api.yourdomain.com --service backend
```

## 🌟 **Render Deployment**

### Step 1: Connect GitHub Repository
1. Go to [Render Dashboard](https://render.com/dashboard)
2. Click "New +" → "Web Service"
3. Connect your GitHub repository

### Step 2: Configure Backend Service
- **Name**: `qa-automation-backend`
- **Environment**: `Docker`
- **Dockerfile Path**: `./Dockerfile.backend`
- **Health Check Path**: `/actuator/health`

**Environment Variables:**
```
SPRING_PROFILES_ACTIVE=production
HF_API_KEY=your-huggingface-api-key
SERVER_PORT=8080
```

### Step 3: Configure Frontend Service
- **Name**: `qa-automation-frontend`
- **Environment**: `Docker`
- **Dockerfile Path**: `./Dockerfile.frontend`
- **Health Check Path**: `/`

**Environment Variables:**
```
BACKEND_URL=https://qa-automation-backend.onrender.com
```

## ⚡ **GitHub Actions CI/CD**

### Setup Secrets
Add these secrets to your GitHub repository:

**For Railway:**
```
RAILWAY_TOKEN=your-railway-token
```

**For Docker Hub (optional):**
```
DOCKER_USERNAME=your-docker-username
DOCKER_PASSWORD=your-docker-password
```

### Automatic Deployment
The included GitHub Actions workflows will:

1. **Test**: Run backend tests on every push
2. **Build**: Create Docker images for main branch
3. **Deploy**: Automatically deploy to Railway/Render
4. **Health Check**: Verify deployment success

## 🔧 **Environment Configuration**

### Backend Environment Variables
```bash
# Required
SPRING_PROFILES_ACTIVE=production
HF_API_KEY=your-huggingface-api-key

# Optional
SERVER_PORT=8080
LOGGING_LEVEL_ROOT=INFO
MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info,metrics
```

### Frontend Environment Variables
```bash
# Backend API URL
BACKEND_URL=https://your-backend-url.com

# Optional
API_TIMEOUT=30000
```

## 🏥 **Health Checks & Monitoring**

### Health Check Endpoints
- **Backend**: `https://your-backend.com/actuator/health`
- **Frontend**: `https://your-frontend.com/`

### Monitoring Setup
```bash
# Check service status
curl https://your-backend.com/actuator/health

# Test MCP endpoint
curl -X POST https://your-backend.com/api/v1/mcp/tools/analyze_with_ai/call \
  -H "Content-Type: application/json" \
  -d '{"data":{"test_data":"sample"},"analysis_type":"summary","ai_provider":"mock"}'
```

## 💡 **Cost Optimization**

### Free Tier Limits
- **Railway**: $5/month credit (≈ 2-3 small services)
- **Render**: 750 hours/month per service
- **Fly.io**: 3 small VMs, 160GB bandwidth

### Optimization Tips
1. **Use Sleep Mode**: Let services sleep when inactive
2. **Optimize Images**: Use multi-stage builds (already implemented)
3. **Resource Limits**: Set appropriate memory/CPU limits
4. **Monitoring**: Use free monitoring tools

## 🚀 **Quick Start Commands**

### Railway One-Command Deploy
```bash
# Deploy everything
git clone https://github.com/yourusername/qa-automation-service-hub
cd qa-automation-service-hub
railway login
railway new qa-automation-hub
railway up --dockerfile Dockerfile.backend --service backend
railway up --dockerfile Dockerfile.frontend --service frontend
```

### Render One-Click Deploy
1. Fork the repository on GitHub
2. Connect to Render
3. Use the included `render-*.yaml` configuration files
4. Deploy with one click

## 🔍 **Troubleshooting**

### Common Issues

1. **Service Won't Start**
   ```bash
   # Check logs
   railway logs --service backend
   # or
   render logs --service qa-automation-backend
   ```

2. **Environment Variables Missing**
   ```bash
   # Verify environment variables
   railway variables --service backend
   ```

3. **Health Check Failing**
   ```bash
   # Test health endpoint locally
   curl http://localhost:8080/actuator/health
   ```

### Support Resources
- **Railway**: [Railway Docs](https://docs.railway.app/)
- **Render**: [Render Docs](https://render.com/docs)
- **GitHub Actions**: [Actions Documentation](https://docs.github.com/en/actions)

## 🎉 **Success Metrics**

After deployment, you should have:
- ✅ Backend API accessible via HTTPS
- ✅ Frontend UI accessible via HTTPS  
- ✅ MCP endpoints working
- ✅ Health checks passing
- ✅ Automatic deployments on git push
- ✅ SSL certificates configured
- ✅ Environment variables secured

Your QA Automation Service Hub is now production-ready and publicly accessible!