# 🚀 QA Automation Service Hub - Service Management

This directory contains powerful shell scripts to easily manage your QA Automation Service Hub services.

## 📋 Available Scripts

### 1. 🎯 Quick Start Script: `qa-hub`
The simplest way to manage your services:

```bash
./qa-hub up        # Start all services
./qa-hub down      # Stop all services  
./qa-hub restart   # Restart all services
./qa-hub status    # Check service status
```

### 2. 🔧 Full Management Script: `start-services.sh`
Comprehensive service management with advanced options:

```bash
./start-services.sh start          # Start both services
./start-services.sh stop           # Stop all services
./start-services.sh restart        # Restart all services
./start-services.sh status         # Show detailed status
./start-services.sh backend        # Start only backend
./start-services.sh frontend       # Start only frontend
./start-services.sh logs backend   # Show backend logs
./start-services.sh logs frontend  # Show frontend logs
./start-services.sh help           # Show all options
```

## 🎯 Quick Examples

### Start Everything
```bash
# Method 1: Quick start
./qa-hub up

# Method 2: Full script
./start-services.sh start
```

### Check What's Running
```bash
./qa-hub status
# or
./start-services.sh status
```

### View Logs
```bash
./start-services.sh logs backend
./start-services.sh logs frontend
```

### Stop Everything
```bash
./qa-hub down
```

## 📊 What the Scripts Do

### 🎯 Intelligent Port Management
- **Automatically detects** if services are already running
- **Kills existing processes** before starting new ones
- **Waits for services** to be fully ready before continuing

### 📝 Comprehensive Logging
- **Separate log files** for backend and frontend
- **Log rotation** and management
- **Real-time log viewing** with `logs` command

### 🔍 Service Health Checking
- **Port monitoring** to verify services are running
- **PID tracking** for process management
- **Startup verification** with timeout handling

### 🎨 User-Friendly Output
- **Color-coded messages** for easy reading
- **Progress indicators** during startup
- **Clear status reporting** with URLs

## 🗂️ File Structure

```
qa-automation-service-hub/
├── qa-hub                 # Quick start wrapper script
├── start-services.sh      # Full service management script
├── logs/                  # Auto-created log directory
│   ├── backend.log       # Backend service logs
│   └── frontend.log      # Frontend service logs
├── pids/                  # Auto-created PID directory
│   ├── backend.pid       # Backend process ID
│   └── frontend.pid      # Frontend process ID
├── backend/              # Spring Boot application
└── frontend/             # Frontend application
```

## 🔧 Configuration

The scripts automatically detect and use:
- **Maven**: `/opt/homebrew/opt/maven/bin/mvn`
- **NPM**: `/opt/homebrew/bin/npm`
- **Backend Port**: `8080`
- **Frontend Port**: `4200`

## 🚨 Troubleshooting

### Script Won't Run
```bash
# Make sure scripts are executable
chmod +x qa-hub start-services.sh
```

### Services Won't Start
```bash
# Check if required tools are installed
brew install maven node

# View detailed logs
./start-services.sh logs backend
./start-services.sh logs frontend
```

### Port Already in Use
```bash
# The scripts automatically handle this, but if needed:
lsof -ti :8080 | xargs kill -9  # Kill backend
lsof -ti :4200 | xargs kill -9  # Kill frontend
```

### Clean Restart
```bash
# Stop everything and restart fresh
./qa-hub down
sleep 3
./qa-hub up
```

## 🎯 Service URLs

Once started, access your services at:

### 🖥️ Backend Services
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health
- **API Documentation**: http://localhost:8080/v3/api-docs
- **Metrics**: http://localhost:8080/actuator/metrics

### 🌐 Frontend Services
- **Test Dashboard**: http://localhost:4200/test-dashboard.html
- **Frontend Root**: http://localhost:4200

## 💡 Pro Tips

### 1. 🔄 Development Workflow
```bash
# Start everything
./qa-hub up

# Make changes to code
# ...

# Quick restart to see changes
./qa-hub restart
```

### 2. 📊 Monitoring
```bash
# Check status frequently
watch -n 5 './qa-hub status'

# Follow logs in real-time
tail -f logs/backend.log
tail -f logs/frontend.log
```

### 3. 🧪 Testing Workflow
```bash
# Start services
./qa-hub up

# Run your tests
# ...

# Check logs if issues
./start-services.sh logs backend

# Stop when done
./qa-hub down
```

## 🎉 Happy Testing!

Your QA Automation Service Hub is now easy to manage with these powerful scripts. Start developing and testing with confidence! 🚀