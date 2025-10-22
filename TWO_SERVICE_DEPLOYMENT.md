# 🚀 Two-Service Railway Deployment Guide

## The Problem
You're trying to serve both frontend and backend from one service, which is causing conflicts. The proper architecture is:

## ✅ Correct Architecture
```
Frontend Service (nginx) ←→ Backend Service (Spring Boot)
     (Port 80)                    (Port 8080)
   Serves HTML/CSS/JS           Serves API endpoints
```

## 🔧 Deployment Steps

### Step 1: Create Backend Service
1. Go to Railway Dashboard
2. Create new project or service
3. Connect your GitHub repo
4. Service name: `qa-automation-backend`
5. Use `Dockerfile.backend`
6. Set environment variables:
   - `SPRING_PROFILES_ACTIVE=production`
   - `SERVER_PORT=8080` 
   - `HF_API_KEY=demo-mode`

### Step 2: Create Frontend Service  
1. In same Railway project, add new service
2. Service name: `qa-automation-frontend`
3. Use `Dockerfile.frontend`
4. Set environment variables:
   - `BACKEND_URL=https://your-backend-service-url.railway.app`

### Step 3: Update CORS in Backend
Add frontend domain to allowed origins in Railway backend environment:
- `ALLOWED_ORIGINS=https://your-frontend-service-url.railway.app`

### Step 4: Test
- Frontend: `https://your-frontend-service.railway.app/test-dashboard.html`
- Backend Health: `https://your-backend-service.railway.app/actuator/health`

## 🎯 Current Issue

Your current single service deployment is trying to do both jobs, causing:
- ❌ Static file serving conflicts
- ❌ API routing issues  
- ❌ CORS problems

## 📋 Alternative: Quick Fix for Single Service

If you want to keep one service, we can make the backend serve static files properly by:
1. Removing the WebController conflicts
2. Properly configuring Spring Boot static resources
3. Making sure the frontend files are copied correctly

Which approach do you prefer?
- **Option A**: Two services (recommended, proper architecture)
- **Option B**: Single service with proper static file configuration