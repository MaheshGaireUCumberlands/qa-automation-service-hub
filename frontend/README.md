# Frontend - QA Automation Service Hub

Angular 17 frontend dashboard for the QA Automation Service Hub.

## 🚀 Quick Start

### Prerequisites
- Node.js 18+
- npm 8+

### Install Dependencies
```bash
npm install
```

### Run Development Server
```bash
npm start
```

The application will start on `http://localhost:4200`

## 🎨 Features

### Test Data Generation Dashboard
- Interactive form to generate test data
- Real-time streaming of generated data
- Support for multiple data types (user, product, order, address, payment)
- Visual display of generated JSON data

### Analytics & Monitoring
- API connection status indicator
- Statistics on generated data
- Data type usage tracking

### Quick Actions Panel
- View Reports (placeholder)
- Jenkins Jobs Trigger (placeholder)
- Export Data functionality (placeholder)
- Settings configuration (placeholder)

## 🛠️ Tech Stack

- **Angular 17** with standalone components
- **TypeScript** for type safety
- **Tailwind CSS** for styling
- **RxJS** for reactive programming
- **HttpClient** for API communication

## 📱 UI Components

### Main Dashboard
- Header with application branding
- Test data generation form
- Statistics cards
- Quick actions grid

### Data Display
- JSON viewer for generated test data
- Scrollable data list
- Real-time updates

## 🔌 API Integration

Connects to backend Spring Boot APIs:
- `GET /api/v1/testdata/generate/{type}?count={n}` - Generate test data
- `GET /api/v1/testdata/templates` - Get available templates

### CORS Configuration
Backend is configured to accept requests from:
- `http://localhost:4200` (Angular dev server)
- `http://localhost:3000` (React dev server)

## 📁 Project Structure

```
src/
├── app/
│   └── app.component.ts     # Main application component
├── index.html               # HTML template
├── main.ts                  # Application bootstrap
└── styles.css              # Global styles
```

## 🎯 Development Notes

### Standalone Components
This project uses Angular 17's standalone components approach:
- No need for NgModule
- Direct imports in component metadata
- Simplified bootstrapping process

### Styling Approach
- Tailwind CSS for utility-first styling
- Responsive design with mobile-first approach
- Component-scoped styles where needed

## 🚀 Next Steps

1. **Add Routing**: Implement Angular Router for multiple pages
2. **State Management**: Add NgRx or Akita for complex state
3. **Authentication**: Implement login/logout functionality
4. **Charts**: Add Chart.js integration for data visualization
5. **Testing**: Add unit and integration tests
6. **PWA**: Convert to Progressive Web App
7. **Real-time Updates**: WebSocket integration for live data

## 📊 Build & Deployment

### Production Build
```bash
npm run build
```

### Testing
```bash
npm test
```

### Linting
```bash
npm run lint
```

## 🔧 Configuration

### Environment Files
Create environment files for different stages:
- `src/environments/environment.ts` (development)
- `src/environments/environment.prod.ts` (production)

### Proxy Configuration
For development, you can create `proxy.conf.json` to proxy API calls:
```json
{
  "/api/*": {
    "target": "http://localhost:8080",
    "secure": true,
    "changeOrigin": true
  }
}
```