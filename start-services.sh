#!/bin/bash

# QA Automation Service Hub - Service Management Script
# Author: Mahesh Gaire
# Description: Start, stop, or restart backend and frontend services

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$SCRIPT_DIR/backend"
FRONTEND_DIR="$SCRIPT_DIR/frontend"
BACKEND_PORT=8080
FRONTEND_PORT=4200
MAVEN_CMD="/opt/homebrew/opt/maven/bin/mvn"
NPM_CMD="/opt/homebrew/bin/npm"

# Log file locations
LOG_DIR="$SCRIPT_DIR/logs"
BACKEND_LOG="$LOG_DIR/backend.log"
FRONTEND_LOG="$LOG_DIR/frontend.log"

# PID file locations
PID_DIR="$SCRIPT_DIR/pids"
BACKEND_PID="$PID_DIR/backend.pid"
FRONTEND_PID="$PID_DIR/frontend.pid"

# Create directories if they don't exist
mkdir -p "$LOG_DIR" "$PID_DIR"

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

# Function to check if a port is in use
is_port_in_use() {
    local port=$1
    lsof -i :$port >/dev/null 2>&1
}

# Function to kill process on port
kill_process_on_port() {
    local port=$1
    local service_name=$2
    
    if is_port_in_use $port; then
        print_warning "Port $port is in use. Killing existing $service_name process..."
        local pid=$(lsof -ti :$port)
        if [ ! -z "$pid" ]; then
            kill -9 $pid 2>/dev/null || true
            sleep 2
            print_success "$service_name process killed (PID: $pid)"
        fi
    fi
}

# Function to check if Maven is available
check_maven() {
    if [ ! -f "$MAVEN_CMD" ]; then
        print_error "Maven not found at $MAVEN_CMD"
        print_error "Please install Maven: brew install maven"
        exit 1
    fi
}

# Function to check if NPM is available
check_npm() {
    if [ ! -f "$NPM_CMD" ]; then
        print_error "NPM not found at $NPM_CMD"
        print_error "Please install Node.js: brew install node"
        exit 1
    fi
}

# Function to start backend
start_backend() {
    print_status "Starting backend service..."
    
    check_maven
    
    if [ ! -d "$BACKEND_DIR" ]; then
        print_error "Backend directory not found: $BACKEND_DIR"
        exit 1
    fi
    
    # Kill existing backend process
    kill_process_on_port $BACKEND_PORT "backend"
    
    # Start backend
    cd "$BACKEND_DIR"
    print_status "Starting Spring Boot application..."
    
    # Start Maven in background and capture PID
    nohup $MAVEN_CMD spring-boot:run > "$BACKEND_LOG" 2>&1 &
    local backend_pid=$!
    echo $backend_pid > "$BACKEND_PID"
    
    print_success "Backend started with PID: $backend_pid"
    print_status "Backend logs: $BACKEND_LOG"
    
    # Wait for backend to start
    print_status "Waiting for backend to start (checking port $BACKEND_PORT)..."
    local max_attempts=30
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if is_port_in_use $BACKEND_PORT; then
            print_success "Backend is ready on port $BACKEND_PORT"
            print_success "Swagger UI: http://localhost:$BACKEND_PORT/swagger-ui.html"
            print_success "Health Check: http://localhost:$BACKEND_PORT/actuator/health"
            return 0
        fi
        
        echo -n "."
        sleep 2
        attempt=$((attempt + 1))
    done
    
    print_error "Backend failed to start within 60 seconds"
    print_error "Check logs: tail -f $BACKEND_LOG"
    return 1
}

# Function to start frontend
start_frontend() {
    print_status "Starting frontend service..."
    
    check_npm
    
    if [ ! -d "$FRONTEND_DIR" ]; then
        print_error "Frontend directory not found: $FRONTEND_DIR"
        exit 1
    fi
    
    # Kill existing frontend process
    kill_process_on_port $FRONTEND_PORT "frontend"
    
    # Start frontend
    cd "$FRONTEND_DIR"
    
    # Install dependencies if node_modules doesn't exist
    if [ ! -d "node_modules" ]; then
        print_status "Installing frontend dependencies..."
        $NPM_CMD install
    fi
    
    print_status "Starting frontend server..."
    
    # Start npm in background and capture PID
    nohup $NPM_CMD start > "$FRONTEND_LOG" 2>&1 &
    local frontend_pid=$!
    echo $frontend_pid > "$FRONTEND_PID"
    
    print_success "Frontend started with PID: $frontend_pid"
    print_status "Frontend logs: $FRONTEND_LOG"
    
    # Wait for frontend to start
    print_status "Waiting for frontend to start (checking port $FRONTEND_PORT)..."
    local max_attempts=15
    local attempt=1
    
    while [ $attempt -le $max_attempts ]; do
        if is_port_in_use $FRONTEND_PORT; then
            print_success "Frontend is ready on port $FRONTEND_PORT"
            print_success "Test Dashboard: http://localhost:$FRONTEND_PORT/test-dashboard.html"
            return 0
        fi
        
        echo -n "."
        sleep 2
        attempt=$((attempt + 1))
    done
    
    print_error "Frontend failed to start within 30 seconds"
    print_error "Check logs: tail -f $FRONTEND_LOG"
    return 1
}

# Function to stop services
stop_services() {
    print_status "Stopping all services..."
    
    # Stop backend
    if [ -f "$BACKEND_PID" ]; then
        local backend_pid=$(cat "$BACKEND_PID")
        if ps -p $backend_pid > /dev/null 2>&1; then
            print_status "Stopping backend (PID: $backend_pid)..."
            kill -TERM $backend_pid 2>/dev/null || true
            sleep 3
            if ps -p $backend_pid > /dev/null 2>&1; then
                kill -9 $backend_pid 2>/dev/null || true
            fi
        fi
        rm -f "$BACKEND_PID"
    fi
    
    # Stop frontend
    if [ -f "$FRONTEND_PID" ]; then
        local frontend_pid=$(cat "$FRONTEND_PID")
        if ps -p $frontend_pid > /dev/null 2>&1; then
            print_status "Stopping frontend (PID: $frontend_pid)..."
            kill -TERM $frontend_pid 2>/dev/null || true
            sleep 2
            if ps -p $frontend_pid > /dev/null 2>&1; then
                kill -9 $frontend_pid 2>/dev/null || true
            fi
        fi
        rm -f "$FRONTEND_PID"
    fi
    
    # Kill any remaining processes on the ports
    kill_process_on_port $BACKEND_PORT "backend"
    kill_process_on_port $FRONTEND_PORT "frontend"
    
    print_success "All services stopped"
}

# Function to show status
show_status() {
    print_status "Service Status:"
    echo
    
    # Backend status
    if is_port_in_use $BACKEND_PORT; then
        local backend_pid=$(lsof -ti :$BACKEND_PORT)
        print_success "✅ Backend: Running (PID: $backend_pid, Port: $BACKEND_PORT)"
        echo "   📚 Swagger UI: http://localhost:$BACKEND_PORT/swagger-ui.html"
        echo "   ❤️  Health Check: http://localhost:$BACKEND_PORT/actuator/health"
    else
        print_warning "❌ Backend: Not running"
    fi
    
    # Frontend status
    if is_port_in_use $FRONTEND_PORT; then
        local frontend_pid=$(lsof -ti :$FRONTEND_PORT)
        print_success "✅ Frontend: Running (PID: $frontend_pid, Port: $FRONTEND_PORT)"
        echo "   🧪 Test Dashboard: http://localhost:$FRONTEND_PORT/test-dashboard.html"
    else
        print_warning "❌ Frontend: Not running"
    fi
    
    echo
    print_status "Log files:"
    echo "   Backend: $BACKEND_LOG"
    echo "   Frontend: $FRONTEND_LOG"
}

# Function to show logs
show_logs() {
    local service=$1
    case $service in
        "backend")
            if [ -f "$BACKEND_LOG" ]; then
                print_status "Backend logs (last 50 lines):"
                tail -50 "$BACKEND_LOG"
            else
                print_warning "Backend log file not found"
            fi
            ;;
        "frontend")
            if [ -f "$FRONTEND_LOG" ]; then
                print_status "Frontend logs (last 50 lines):"
                tail -50 "$FRONTEND_LOG"
            else
                print_warning "Frontend log file not found"
            fi
            ;;
        *)
            print_error "Invalid service. Use 'backend' or 'frontend'"
            ;;
    esac
}

# Function to show usage
show_usage() {
    echo "QA Automation Service Hub - Service Management Script"
    echo
    echo "Usage: $0 [COMMAND] [OPTIONS]"
    echo
    echo "Commands:"
    echo "  start           Start both backend and frontend services"
    echo "  stop            Stop all services"
    echo "  restart         Restart all services"
    echo "  status          Show service status"
    echo "  backend         Start only backend service"
    echo "  frontend        Start only frontend service"
    echo "  logs [service]  Show logs (backend/frontend)"
    echo "  help            Show this help message"
    echo
    echo "Examples:"
    echo "  $0 start        # Start both services"
    echo "  $0 backend      # Start only backend"
    echo "  $0 status       # Check service status"
    echo "  $0 logs backend # Show backend logs"
    echo "  $0 restart      # Restart all services"
}

# Main script logic
case "${1:-start}" in
    "start")
        print_status "🚀 Starting QA Automation Service Hub..."
        start_backend
        sleep 2
        start_frontend
        echo
        show_status
        ;;
    "stop")
        stop_services
        ;;
    "restart")
        print_status "🔄 Restarting QA Automation Service Hub..."
        stop_services
        sleep 2
        start_backend
        sleep 2
        start_frontend
        echo
        show_status
        ;;
    "backend")
        start_backend
        ;;
    "frontend")
        start_frontend
        ;;
    "status")
        show_status
        ;;
    "logs")
        show_logs "$2"
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