#!/bin/bash

# QA Automation Service Hub - Deployment Script
# Usage: ./deploy.sh [basic|monitoring|stop|status|logs]

set -e

COMPOSE_FILE="docker-compose.yml"
PROJECT_NAME="qa-automation-service-hub"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
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

# Function to check if Docker is running
check_docker() {
    if ! docker info >/dev/null 2>&1; then
        print_error "Docker is not running. Please start Docker and try again."
        exit 1
    fi
}

# Function to check if required files exist
check_files() {
    if [ ! -f "$COMPOSE_FILE" ]; then
        print_error "docker-compose.yml not found in current directory"
        exit 1
    fi
}

# Function to deploy basic services (frontend + backend)
deploy_basic() {
    print_status "Starting basic deployment (frontend + backend)..."
    docker-compose up -d backend frontend
    print_success "Basic services started!"
    show_access_info
}

# Function to deploy with monitoring
deploy_monitoring() {
    print_status "Starting full deployment with monitoring..."
    docker-compose --profile monitoring up -d
    print_success "All services started including monitoring!"
    show_access_info
    show_monitoring_info
}

# Function to stop all services
stop_services() {
    print_status "Stopping all services..."
    docker-compose down
    print_success "All services stopped!"
}

# Function to show service status
show_status() {
    print_status "Service Status:"
    docker-compose ps
    echo
    print_status "Service Health:"
    check_service_health "frontend" "http://localhost/"
    check_service_health "backend" "http://localhost:8080/actuator/health"
}

# Function to check individual service health
check_service_health() {
    local service=$1
    local url=$2
    if curl -f "$url" >/dev/null 2>&1; then
        print_success "$service is healthy"
    else
        print_warning "$service is not responding"
    fi
}

# Function to show logs
show_logs() {
    print_status "Showing logs (Press Ctrl+C to exit)..."
    docker-compose logs -f
}

# Function to show access information
show_access_info() {
    echo
    print_success "=== Service Access Information ==="
    echo "Frontend:      http://localhost"
    echo "Backend API:   http://localhost:8080"
    echo "Health Check:  http://localhost:8080/actuator/health"
    echo
}

# Function to show monitoring information
show_monitoring_info() {
    print_success "=== Monitoring Access Information ==="
    echo "Grafana:       http://localhost:3000 (admin/qa-automation-2025)"
    echo "Prometheus:    http://localhost:9090"
    echo
}

# Function to run health checks
health_check() {
    print_status "Running health checks..."
    
    # Wait for services to be ready
    print_status "Waiting for services to start..."
    sleep 10
    
    # Check frontend
    if curl -f http://localhost/ >/dev/null 2>&1; then
        print_success "✓ Frontend is healthy"
    else
        print_error "✗ Frontend is not responding"
    fi
    
    # Check backend
    if curl -f http://localhost:8080/actuator/health >/dev/null 2>&1; then
        print_success "✓ Backend is healthy"
    else
        print_error "✗ Backend is not responding"
    fi
    
    # Check monitoring if running
    if docker-compose ps | grep -q prometheus; then
        if curl -f http://localhost:9090 >/dev/null 2>&1; then
            print_success "✓ Prometheus is healthy"
        else
            print_error "✗ Prometheus is not responding"
        fi
    fi
    
    if docker-compose ps | grep -q grafana; then
        if curl -f http://localhost:3000 >/dev/null 2>&1; then
            print_success "✓ Grafana is healthy"
        else
            print_error "✗ Grafana is not responding"
        fi
    fi
}

# Function to show usage
show_usage() {
    echo "QA Automation Service Hub - Deployment Script"
    echo
    echo "Usage: $0 [command]"
    echo
    echo "Commands:"
    echo "  basic      - Start frontend and backend services only"
    echo "  monitoring - Start all services including monitoring stack"
    echo "  stop       - Stop all services"
    echo "  status     - Show service status and health"
    echo "  logs       - Show real-time logs"
    echo "  health     - Run comprehensive health checks"
    echo "  help       - Show this help message"
    echo
    echo "Examples:"
    echo "  $0 basic      # Start basic services"
    echo "  $0 monitoring # Start with monitoring"
    echo "  $0 status     # Check service status"
    echo "  $0 logs       # View logs"
}

# Main script logic
main() {
    check_docker
    check_files
    
    case "${1:-basic}" in
        "basic")
            deploy_basic
            health_check
            ;;
        "monitoring")
            deploy_monitoring
            health_check
            ;;
        "stop")
            stop_services
            ;;
        "status")
            show_status
            ;;
        "logs")
            show_logs
            ;;
        "health")
            health_check
            ;;
        "help"|"-h"|"--help")
            show_usage
            ;;
        *)
            print_error "Unknown command: $1"
            echo
            show_usage
            exit 1
            ;;
    esac
}

# Run main function with all arguments
main "$@"