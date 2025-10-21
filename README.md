# QA Automation Service Hub

A full-stack web application that provides reusable QA utilities such as test data generation, report visualization, and Jenkins job triggers.

This project demonstrates both **backend automation with Spring Boot (WebFlux)** and **frontend visualization with Angular/React**, built in a single repo for easy demo and deployment.

---

## 🧰 Tech Stack

### Backend
- Java 17  
- Spring Boot 3 (WebFlux, Actuator, OpenAPI)  
- JUnit 5, Mockito, Cucumber  
- Swagger UI  
- Gatling for load testing  
- Spring Boot Admin for monitoring  

### Frontend
- Angular 17 *(or React 18)*  
- TypeScript  
- Tailwind CSS  
- Axios / HttpClient  
- Chart.js or Recharts  

---

## 🗂️ Project Structure

```
qa-automation-service-hub/
├── backend/ → Spring Boot WebFlux REST APIs
├── frontend/ → Angular/React dashboard
├── docker-compose.yml → Optional combined demo
└── README.md
```

---

## 🧪 Run Locally

### 🚀 **Quick Start (Recommended)**
```bash
# Start both backend and frontend services
./qa-hub up

# Check service status
./qa-hub status

# Stop all services
./qa-hub down
```

### 🔧 **Manual Start (Alternative)**

#### 1️⃣ Start Backend
```bash
cd backend
mvn spring-boot:run
```
Backend runs at → http://localhost:8080/swagger-ui.html

#### 2️⃣ Start Frontend
```bash
cd ../frontend
npm install
npm start
```
Frontend runs at → http://localhost:4200

### 📋 **Service Management**
Use the included shell scripts for easy service management:
- `./qa-hub up` - Start all services
- `./qa-hub down` - Stop all services  
- `./qa-hub status` - Check service status
- `./start-services.sh help` - View all options

See [SERVICE-MANAGEMENT.md](SERVICE-MANAGEMENT.md) for detailed documentation.

---

## ⚙️ Demo Overview

✅ Generate test data asynchronously  
✅ Parse JUnit/Cucumber reports  
✅ Trigger Jenkins jobs via REST API  
✅ View analytics on frontend dashboard  
✅ ELK-ready logging  
✅ Swagger + Admin UI  
✅ **Service management scripts**  
✅ **Automated startup/shutdown**  
✅ **Health monitoring and logs**  

---

## 🚀 Future Enhancements

- Add OpenAI-based test case summarization
- Implement MCP (Model Context Protocol) layer
- JWT-based authentication
- Dockerized full-stack deployment

---

## 👨‍💻 Author

**Mahesh Gaire**  
[GitHub Profile](https://github.com/maheshgaire)

---

## 🧱 **Recommended Approach**

Since you're learning with Copilot:
- Start with the **backend folder** first (Spring Boot WebFlux REST API).  
- Once it's running, scaffold the **frontend** inside `/frontend` using Angular CLI or React create-app.  
- Then link the two (CORS + simple fetch calls).

---

## ⚙️ **IntelliJ + VS Code Workflow**

| Task | Tool |
|------|------|
| Backend (Java, Spring Boot) | IntelliJ IDEA |
| Frontend (Angular/React) | VS Code |
| Combined Git commits / GitHub | Either |