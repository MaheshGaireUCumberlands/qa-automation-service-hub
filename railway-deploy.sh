#!/bin/bash

# Railway Deployment Script for QA Automation Service Hub
# Usage: ./railway-deploy.sh

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Railway CLI is installed
check_railway_cli() {
    if ! command -v railway &> /dev/null; then
        print_error "Railway CLI not found. Installing..."
        npm install -g @railway/cli
        print_success "Railway CLI installed!"
    else
        print_success "Railway CLI found"
    fi
}

# Login to Railway
railway_login() {
    print_status "Logging into Railway..."
    railway login
    print_success "Logged into Railway!"
}

# Deploy to Railway
deploy_to_railway() {
    print_status "Deploying to Railway..."
    
    # Check if we're in a Railway project
    if [ ! -f ".railway" ]; then
        print_status "No Railway project found. Creating new project..."
        railway init
    fi
    
    # Deploy the application
    print_status "Starting deployment..."
    railway up
    
    print_success "Deployment initiated!"
    print_status "Check deployment status at: https://railway.app/dashboard"
}

# Set environment variables
set_environment_variables() {
    print_status "Setting up environment variables..."
    
    # Prompt for HuggingFace API key if not set
    if [ -z "$HF_API_KEY" ]; then
        print_warning "HF_API_KEY not found in environment"
        echo -n "Enter your HuggingFace API key: "
        read -s HF_API_KEY
        echo
    fi
    
    # Set environment variables in Railway
    railway variables set HF_API_KEY="$HF_API_KEY"
    railway variables set SPRING_PROFILES_ACTIVE="production"
    railway variables set SERVER_PORT="8080"
    railway variables set RAILWAY_ENVIRONMENT="production"
    
    print_success "Environment variables set!"
}

# Show deployment URLs
show_deployment_info() {
    print_success "=== Deployment Complete! ==="
    echo
    print_status "Your QA Automation Service Hub is now live on Railway!"
    echo
    print_status "To get your deployment URLs, run:"
    echo "  railway status"
    echo
    print_status "To view logs, run:"
    echo "  railway logs"
    echo
    print_status "To open your app in browser, run:"
    echo "  railway open"
    echo
    print_status "Railway Dashboard: https://railway.app/dashboard"
}

# Main deployment flow
main() {
    echo "🚀 Railway Deployment Script for QA Automation Service Hub"
    echo "=========================================================="
    echo
    
    check_railway_cli
    railway_login
    set_environment_variables
    deploy_to_railway
    show_deployment_info
    
    print_success "Deployment script completed successfully!"
}

# Handle script arguments
case "${1:-deploy}" in
    "deploy")
        main
        ;;
    "status")
        railway status
        ;;
    "logs")
        railway logs
        ;;
    "open")
        railway open
        ;;
    "vars")
        railway variables
        ;;
    "help"|"-h"|"--help")
        echo "Railway Deployment Script"
        echo
        echo "Usage: $0 [command]"
        echo
        echo "Commands:"
        echo "  deploy    - Deploy application to Railway (default)"
        echo "  status    - Show deployment status"
        echo "  logs      - Show application logs"
        echo "  open      - Open application in browser"
        echo "  vars      - Show environment variables"
        echo "  help      - Show this help message"
        ;;
    *)
        print_error "Unknown command: $1"
        echo "Run '$0 help' for usage information"
        exit 1
        ;;
esac