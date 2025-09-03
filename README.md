# AI-Powered Fitness Application

A comprehensive full-stack microservices fitness platform built with Spring Boot and React, featuring AI-driven personalized health insights and recommendations.

## 🏗️ Architecture

This application follows a **microservices architecture** pattern with:
- **API Gateway**: Centralized routing and load balancing
- **Service Discovery**: Automatic service registration and discovery
- **Event-Driven Communication**: Asynchronous messaging between services
- **AI Integration**: Intelligent health insights and personalized recommendations

## 🚀 Features

### Core Functionality
- **User Management**: Registration, authentication, and profile management
- **Workout Tracking**: Log exercises, sets, reps, and workout duration
- **Progress Monitoring**: Track fitness goals and performance metrics
- **Health Analytics**: Comprehensive dashboard with progress visualization

### AI-Powered Features
- **Personalized Workout Recommendations**: AI-generated exercise plans based on user goals
- **Health Insights**: Intelligent analysis of workout patterns and progress
- **Smart Notifications**: Contextual reminders and motivation messages
- **Performance Predictions**: AI-driven forecasting of fitness outcomes

## 🛠️ Tech Stack

### Backend Services
- **Spring Boot** - Microservices framework
- **Spring Cloud Gateway** - API Gateway for routing and load balancing
- **Eureka Server (Netflix)** - Service discovery and registration
- **Keycloak** - Identity and access management
- **RabbitMQ (Spring AMQP)** - Asynchronous messaging and event streaming
- **Spring Cloud Config Server** - Centralized configuration management

### Database & Storage
- **PostgreSQL** - Primary database for user data and workout records
- **MySQL** - Secondary database for analytics and reporting

### AI & External APIs
- **Google Gemini API** - AI-powered health insights and recommendations
- **Custom AI Models** - Personalized workout and nutrition suggestions

### Frontend
- **React** - Modern, responsive user interface
- **Component-based Architecture** - Modular and reusable UI components

## 🏛️ System Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React Frontend │    │  API Gateway    │    │ Config Server   │
│                 │◄──►│ (Spring Cloud)  │◄──►│                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                    ┌───────────┼───────────┐
                    │           │           │
            ┌───────▼────┐ ┌────▼─────┐ ┌──▼──────┐
            │User Service│ │  Workout │ │Analytics│
            │            │ │  Service │ │ Service │
            └────────────┘ └──────────┘ └─────────┘
                    │           │           │
            ┌───────▼───────────▼───────────▼───────┐
            │         Message Queue (RabbitMQ)      │
            └───────────────────────────────────────┘
                              │
                    ┌─────────▼─────────┐
                    │   Eureka Server   │
                    │ (Service Registry)│
                    └───────────────────┘
```

## 🔐 Security

- **Keycloak Integration**: Enterprise-grade authentication and authorization
- **JWT Tokens**: Secure API communication
- **Role-Based Access Control**: Granular permissions management
- **API Rate Limiting**: Protection against abuse and overuse

## 📊 Data Flow

1. **User Registration/Login**: Handled through Keycloak authentication service
2. **Workout Logging**: Data flows through API Gateway to Workout Service
3. **AI Processing**: Workout data sent to AI service for analysis via RabbitMQ
4. **Real-time Updates**: Services communicate asynchronously for live updates
5. **Analytics**: Aggregated data processed for insights and reporting

## 🧠 AI Capabilities

- **Workout Optimization**: AI analyzes past workouts to suggest improvements
- **Goal Prediction**: Machine learning models predict achievement timelines
- **Personalized Content**: Dynamic content based on user preferences and progress
- **Health Risk Assessment**: Early warning system for potential health concerns

## 🔄 Development Status

**Currently in active development** - API endpoints are being stabilized and optimized for production deployment.

---

*Built with ❤️ using modern microservices architecture and AI integration*
