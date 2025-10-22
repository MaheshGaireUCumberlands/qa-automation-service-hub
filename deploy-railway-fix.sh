#!/bin/bash

# Railway Deployment Script for QA Automation Service Hub
# This script deploys the fixed configuration to Railway

echo "🚀 Deploying QA Automation Service Hub to Railway..."

# Check if railway CLI is installed
if ! command -v railway &> /dev/null; then
    echo "❌ Railway CLI not found. Please install it first:"
    echo "npm install -g @railway/cli"
    exit 1
fi

# Check if logged in to Railway
if ! railway whoami &> /dev/null; then
    echo "📝 Please log in to Railway first:"
    railway login
fi

# Deploy to Railway
echo "📦 Deploying application..."
railway deploy

echo "✅ Deployment initiated!"
echo ""
echo "📋 What this deployment includes:"
echo "  ✅ Fixed API URLs for production environment"
echo "  ✅ Backend serves both API and static files"  
echo "  ✅ Proper CORS configuration"
echo "  ✅ Health check endpoints"
echo ""
echo "🔗 Your application will be available at:"
echo "   https://passionate-communication-production-da98.up.railway.app/test-dashboard.html"
echo ""
echo "⏱️  Wait 2-3 minutes for deployment to complete, then test:"
echo "  1. Health check: /actuator/health"
echo "  2. Dashboard: /test-dashboard.html"
echo "  3. API endpoints: /api/v1/..."

# Optional: Open the app after deployment
read -p "🌐 Open the app in browser after deployment? (y/n): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    sleep 30  # Wait a bit for deployment
    open "https://passionate-communication-production-da98.up.railway.app/test-dashboard.html"
fi

echo "🎉 Deployment script completed!"