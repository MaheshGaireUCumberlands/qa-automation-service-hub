# Railway Deployment - Two Service Architecture

Railway should deploy TWO separate services:

## 1. Backend Service (API)
- **Dockerfile**: `Dockerfile.backend`
- **Port**: 8080
- **Serves**: API endpoints only (`/api/*`, `/actuator/*`)
- **Domain**: `https://qa-automation-backend-[id].railway.app`

## 2. Frontend Service (Web UI)
- **Dockerfile**: `Dockerfile.frontend`  
- **Port**: 80
- **Serves**: HTML, CSS, JS files
- **Domain**: `https://qa-automation-frontend-[id].railway.app`

## Current Issue

Your current Railway deployment URL suggests you only have ONE service:
`https://passionate-communication-production-da98.up.railway.app`

## Solution

You need to:
1. **Create Backend Service** on Railway using `Dockerfile.backend`
2. **Create Frontend Service** on Railway using `Dockerfile.frontend`
3. **Configure Frontend** to call the backend service URL
4. **Update CORS** in backend to allow frontend domain

## Backend Environment Variables
```
SPRING_PROFILES_ACTIVE=production
SERVER_PORT=8080
HF_API_KEY=demo-mode
ALLOWED_ORIGINS=https://your-frontend-domain.railway.app
```

## Frontend Environment Variables
```
BACKEND_URL=https://your-backend-domain.railway.app
```