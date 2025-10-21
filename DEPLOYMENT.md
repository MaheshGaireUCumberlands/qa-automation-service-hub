# QA Automation Service Hub - Deployment Guide

## Overview
This guide covers deploying the QA Automation Service Hub using Docker and Docker Compose for production environments.

## Prerequisites
- Docker Engine 20.10+ and Docker Compose v2.0+
- 4GB+ RAM available for containers
- Open ports: 80 (frontend), 8080 (backend), 3000 (Grafana), 9090 (Prometheus)

## Quick Start

### 1. Basic Deployment (Frontend + Backend)
```bash
# Clone and navigate to repository
git clone <your-repo-url>
cd qa-automation-service-hub

# Build and start services
docker-compose up -d

# Check service status
docker-compose ps
```

### 2. Full Deployment with Monitoring
```bash
# Start all services including monitoring stack
docker-compose --profile monitoring up -d

# Check all services
docker-compose --profile monitoring ps
```

## Service Access Points

| Service | URL | Purpose |
|---------|-----|---------|
| Frontend | http://localhost | Main application interface |
| Backend API | http://localhost:8080 | REST API and MCP server |
| Health Check | http://localhost:8080/actuator/health | Backend health status |
| Grafana | http://localhost:3000 | Monitoring dashboards (admin/qa-automation-2025) |
| Prometheus | http://localhost:9090 | Metrics collection |

## Environment Configuration

### Backend Environment Variables
```bash
# Required
SPRING_PROFILES_ACTIVE=docker
HF_API_KEY=your-huggingface-api-key

# Optional
SERVER_PORT=8080
LOGGING_LEVEL_ROOT=INFO
MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE=health,info,metrics
```

### Production Configuration
Create a `.env` file in the project root:
```bash
# Production settings
HF_API_KEY=your-production-huggingface-api-key
GRAFANA_ADMIN_PASSWORD=secure-password-here
SPRING_PROFILES_ACTIVE=production
```

## Health Checks and Monitoring

### Service Health
```bash
# Check backend health
curl http://localhost:8080/actuator/health

# Check frontend health
curl http://localhost/

# View service logs
docker-compose logs -f backend
docker-compose logs -f frontend
```

### Monitoring Stack
- **Prometheus**: Collects metrics from Spring Boot Actuator
- **Grafana**: Visualizes application metrics and health
- **Default Dashboards**: Pre-configured for Spring Boot applications

## Deployment Commands

### Development
```bash
# Start in development mode with logs
docker-compose up

# Rebuild and restart specific service
docker-compose up -d --build backend

# View real-time logs
docker-compose logs -f
```

### Production
```bash
# Start in detached mode
docker-compose up -d

# Start with monitoring
docker-compose --profile monitoring up -d

# Scale services (if needed)
docker-compose up -d --scale backend=2

# Update specific service
docker-compose pull backend
docker-compose up -d --no-deps backend
```

### Maintenance
```bash
# Stop all services
docker-compose down

# Stop and remove volumes
docker-compose down -v

# Clean up unused resources
docker system prune -f

# Backup volumes
docker run --rm -v qa-automation-service-hub_grafana_data:/data -v $(pwd):/backup alpine tar czf /backup/grafana-backup.tar.gz -C /data .
```

## Troubleshooting

### Common Issues

1. **Backend won't start**
   ```bash
   # Check logs
   docker-compose logs backend
   
   # Verify environment variables
   docker-compose exec backend env | grep SPRING
   ```

2. **Frontend can't reach backend**
   ```bash
   # Verify network connectivity
   docker-compose exec frontend curl -f http://backend:8080/actuator/health
   
   # Check Nginx configuration
   docker-compose exec frontend cat /etc/nginx/nginx.conf
   ```

3. **Port conflicts**
   ```bash
   # Check what's using ports
   lsof -i :80 -i :8080 -i :3000 -i :9090
   
   # Modify ports in docker-compose.yml if needed
   ```

### Performance Optimization

1. **Resource Limits**
   Add to docker-compose.yml services:
   ```yaml
   deploy:
     resources:
       limits:
         memory: 1G
         cpus: '1.0'
       reservations:
         memory: 512M
         cpus: '0.5'
   ```

2. **Volume Optimization**
   ```bash
   # Use named volumes for better performance
   # Already configured in docker-compose.yml
   ```

## Security Considerations

### Production Security
1. **Change default passwords**
   - Update Grafana admin password
   - Use secure API keys

2. **Network Security**
   ```bash
   # Bind to specific interface (production)
   # Modify ports section in docker-compose.yml:
   ports:
     - "127.0.0.1:8080:8080"  # Backend only on localhost
     - "0.0.0.0:80:80"        # Frontend on all interfaces
   ```

3. **SSL/TLS**
   - Add reverse proxy (nginx/traefik) with SSL certificates
   - Use Let's Encrypt for automatic certificate management

### Updates and Patches
```bash
# Update base images
docker-compose pull
docker-compose up -d

# Update application code
git pull
docker-compose up -d --build
```

## Backup and Recovery

### Data Backup
```bash
# Backup Grafana data
docker run --rm -v qa-automation-service-hub_grafana_data:/data -v $(pwd):/backup alpine tar czf /backup/grafana-$(date +%Y%m%d).tar.gz -C /data .

# Backup Prometheus data  
docker run --rm -v qa-automation-service-hub_prometheus_data:/data -v $(pwd):/backup alpine tar czf /backup/prometheus-$(date +%Y%m%d).tar.gz -C /data .

# Backup application logs
docker run --rm -v qa-automation-service-hub_backend_logs:/data -v $(pwd):/backup alpine tar czf /backup/logs-$(date +%Y%m%d).tar.gz -C /data .
```

### Data Recovery
```bash
# Restore Grafana data
docker run --rm -v qa-automation-service-hub_grafana_data:/data -v $(pwd):/backup alpine tar xzf /backup/grafana-backup.tar.gz -C /data

# Restart services after restore
docker-compose restart grafana
```

## Support and Maintenance

### Log Management
```bash
# Configure log rotation in production
# Add to docker-compose.yml services:
logging:
  driver: "json-file"
  options:
    max-size: "10m"
    max-file: "3"
```

### Health Monitoring
- Backend health: `/actuator/health`
- Prometheus metrics: `/actuator/prometheus` 
- Custom health checks available via Spring Boot Actuator

For issues or questions, check the application logs and monitoring dashboards first.