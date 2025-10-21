# 🎯 QA Automation Service Hub - Command Reference

## 🚀 Quick Commands

### Essential Commands
```bash
./qa-hub up          # ⭐ Start everything
./qa-hub status      # 📊 Check what's running  
./qa-hub down        # 🛑 Stop everything
./qa-hub restart     # 🔄 Restart everything
```

### Service-Specific Commands
```bash
./start-services.sh backend     # 🖥️  Start only backend
./start-services.sh frontend    # 🌐 Start only frontend
./start-services.sh logs backend   # 📝 View backend logs
./start-services.sh logs frontend  # 📝 View frontend logs
```

## 🌐 Service URLs

### Backend APIs (Port 8080)
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **Health Check**: http://localhost:8080/actuator/health  
- **API Docs**: http://localhost:8080/v3/api-docs
- **Metrics**: http://localhost:8080/actuator/metrics
- **Test Data API**: http://localhost:8080/api/v1/testdata/generate/user?count=5

### Frontend Dashboard (Port 4200)
- **Test Dashboard**: http://localhost:4200/test-dashboard.html
- **Frontend Root**: http://localhost:4200

## 🧪 Testing Workflow

### 1. Start Services
```bash
./qa-hub up
```

### 2. Test APIs
```bash
# Direct cURL testing
curl http://localhost:8080/actuator/health
curl "http://localhost:8080/api/v1/testdata/generate/user?count=3"

# Browser testing
open http://localhost:8080/swagger-ui.html
open http://localhost:4200/test-dashboard.html
```

### 3. Monitor Services
```bash
# Check status
./qa-hub status

# View logs
./start-services.sh logs backend
./start-services.sh logs frontend

# Real-time log monitoring
tail -f logs/backend.log
tail -f logs/frontend.log
```

### 4. Stop Services
```bash
./qa-hub down
```

## 🔧 Troubleshooting

### Service Won't Start
```bash
# Check logs
./start-services.sh logs backend
./start-services.sh logs frontend

# Manual cleanup
lsof -ti :8080 | xargs kill -9  # Kill backend
lsof -ti :4200 | xargs kill -9  # Kill frontend

# Restart fresh
./qa-hub restart
```

### Dependencies Missing
```bash
# Install Maven and Node.js
brew install maven node

# Verify installation
/opt/homebrew/opt/maven/bin/mvn --version
/opt/homebrew/bin/npm --version
```

## 📁 Generated Files

The scripts automatically create:
```
logs/
├── backend.log     # Spring Boot application logs
└── frontend.log    # Frontend server logs

pids/
├── backend.pid     # Backend process ID
└── frontend.pid    # Frontend process ID
```

## 🎯 Development Tips

### Quick Development Cycle
```bash
# 1. Start services
./qa-hub up

# 2. Make code changes
# ... edit files ...

# 3. Restart to see changes
./qa-hub restart

# 4. Test changes
open http://localhost:8080/swagger-ui.html

# 5. Stop when done
./qa-hub down
```

### Continuous Monitoring
```bash
# Watch service status
watch -n 5 './qa-hub status'

# Monitor backend logs
tail -f logs/backend.log | grep ERROR

# Monitor frontend logs  
tail -f logs/frontend.log
```

## 🎉 Success! 

Your QA Automation Service Hub is now fully operational with powerful service management scripts! 🚀

**What you've accomplished:**
✅ Complete Spring Boot WebFlux backend  
✅ Interactive frontend test dashboard  
✅ Automated service management  
✅ Comprehensive logging and monitoring  
✅ Easy development workflow  
✅ Professional deployment setup