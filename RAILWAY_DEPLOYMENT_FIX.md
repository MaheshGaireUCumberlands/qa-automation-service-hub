# Railway Deployment Fix Guide

## Issues Found

1. **Frontend hardcoded to localhost** - Fixed ✅
2. **Missing CORS configuration for Railway domains** - Needs backend update
3. **Backend and Frontend need separate deployments** - Configuration needed
4. **Environment-specific URLs not configured** - Partially fixed

## Deployment Steps

### 1. Deploy Backend Service

1. **Create Railway Backend Service:**
   ```bash
   railway login
   railway init
   railway add --service backend
   ```

2. **Configure Backend Service:**
   - Service Name: `qa-automation-backend`
   - Build Command: Use `Dockerfile.backend`
   - Environment Variables:
     ```
     SPRING_PROFILES_ACTIVE=production
     HF_API_KEY=demo-mode
     SERVER_PORT=8080
     ALLOWED_ORIGINS=https://your-frontend-domain.railway.app
     ```

3. **Deploy Backend:**
   ```bash
   railway deploy --service backend
   ```

### 2. Deploy Frontend Service

1. **Create Railway Frontend Service:**
   ```bash
   railway add --service frontend
   ```

2. **Configure Frontend Service:**
   - Service Name: `qa-automation-frontend`
   - Build Command: Use `Dockerfile.frontend`
   - Environment Variables:
     ```
     RAILWAY_BACKEND_URL=https://your-backend-service.railway.app
     ```

3. **Deploy Frontend:**
   ```bash
   railway deploy --service frontend
   ```

### 3. Update Frontend Configuration

Once you have both services deployed, update the frontend with the actual backend URL:

1. Get your backend Railway URL
2. Replace `YOUR-BACKEND-SERVICE-NAME` in `test-dashboard.html` with actual service name
3. Redeploy frontend

### 4. Current Issue with Your Deployment

The URL `https://passionate-communication-production-da98.up.railway.app/test-dashboard.html` appears to be:
- ✅ Frontend is deployed and serving files
- ❌ Backend is not accessible (causing API failures)
- ❌ CORS issues likely present

## Quick Fix Steps

### Option 1: Single Service with Backend + Static Files

If you want a single service, update the backend to serve static files:

1. Copy frontend files to `backend/src/main/resources/static/`
2. Deploy only the backend service
3. Access via backend URL

### Option 2: Fix Current Deployment (Recommended)

1. Deploy backend as separate service
2. Update frontend with backend URL
3. Configure CORS properly

## Files Modified

- ✅ `frontend/src/test-dashboard.html` - Dynamic API URL detection
- ✅ `Dockerfile.frontend` - Simplified nginx config
- 🔄 Need to update backend CORS configuration

## Next Steps

1. Deploy backend service separately on Railway
2. Get backend service URL
3. Update frontend configuration with actual backend URL
4. Test end-to-end functionality