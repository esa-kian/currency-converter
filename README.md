# Currency Converter

## Overview
The Currency Converter is a web application that allows users to convert currencies using real-time exchange rates from the **Swop.cx API**. The backend is built with **Java Spring Boot**, while the frontend is implemented using **React.js with TypeScript**. Caching is handled with **Caffeine** to improve performance.

## Features
- Retrieve a list of supported currencies.
- Convert an amount from one currency to another.
- Cache exchange rates for optimized performance.
- Dockerized environment for easy deployment.
- Monitoring with Prometheus and Grafana

## Prerequisites
Ensure you have the following installed:
- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Java 17+](https://adoptium.net/)
- [Node.js 18+](https://nodejs.org/en)
- [Maven](https://maven.apache.org/download.cgi)

## Setup Instructions

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/esa-kian/currency-converter.git
cd currency-converter
```

### 2️⃣ Set Up Environment Variables
Create a `.env` file in the project root and add your Swop.cx API key:
```sh
SWOP_API_KEY=your_actual_api_key
```

### 3️⃣ Run the Application using Docker
To build and start all services:
```sh
docker-compose up --build
```
The backend will be available at `http://localhost:8080` and the frontend at `http://localhost:5173`.

### 4️⃣ Run the Application Manually (Without Docker)

#### **Backend Setup**
```sh
cd server
mvn clean install
export SWOP_API_KEY=your_actual_api_key
mvn spring-boot:run
```
The backend will be available at `http://localhost:8080`.

#### **Frontend Setup**
```sh
cd client
npm install
npm run dev
```
The frontend will be available at `http://localhost:5173`.

## API Endpoints

### 🔹 Get All Currencies
**Request:**
```http
GET /api/currencies
```
**Response:**
```json
[
    {
        "code": "USD",
        "numeric_code": "840",
        "decimal_digits": 2,
        "name": "United States dollar",
        "active": true
    },
    {
        "code": "EUR",
        "numeric_code": "978",
        "decimal_digits": 2,
        "name": "Euro",
        "active": true
    },
    {
        "code": "GBP",
        "numeric_code": "826",
        "decimal_digits": 2,
        "name": "Pound sterling",
        "active": true
    },
]
```

### 🔹 Convert Currency
**Request:**
```http
POST /api/currencies/convert
Content-Type: application/json
```
Body:
```json
{
  "source": "EUR",
  "target": "GBP",
  "amount": 100
}
```
**Response:**
```json
{
  "convertedAmount": "£83.65"
}
```

## Monitoring with Prometheus and Grafana

### Prometheus

Prometheus is used to collect and store metrics. The backend exposes metrics at:

📌 `http://localhost:8080/actuator/prometheus`

Grafana

Grafana provides a visual interface for monitoring metrics.

- Access Grafana at: `http://localhost:3000`

- Default login credentials:

    - **Username:** admin

    - **Password:** admin

- Add Prometheus as a data source:

    - URL: `http://localhost:9090`

    - Access: `Browser`


## Technologies Used
- **Backend:** Java 17, Spring Boot, Caffeine, Maven
- **Frontend:** React.js, TypeScript, Vite
- **Containerization:** Docker, Docker Compose
- **Monitoring:** Prometheus, Grafana

## License
This project is licensed under the MIT License.

