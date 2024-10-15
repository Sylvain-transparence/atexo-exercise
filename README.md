# Atexo exercise (Numbering System)

## Description

This project is a solution for uniquely numbering registered users.
It allows for configurable numbering criteria based on user attributes such as name, birth date, and a counter.

## Features

- Configure numbering criteria through a web service
- Generate unique numbers for registered users based on the configuration
- Web interface for managing configurations and generating numbers

## Technologies Used

- Backend: Java with Spring Boot
- Frontend: Angular with Tailwind CSS

## Prerequisites

- Java JDK 21 or later
- Node.js and npm (for Angular frontend)
- Maven (for building the project)

## Installation and Setup

1. Backend setup:
   ```
   mvn clean install
   ```
2. Frontend setup:
   ```
   cd numbering-app
   npm install -g @angular/cli
   npm install
   ```

## Running the Application

1. Start the backend:
   ```
   cd numbering-api/target
   java -jar numbering-api-0.0.1-SNAPSHOT.jar
   ```
2. Access the api at `http://localhost:8080/atexo`
3. Start the frontend:
   ```
   cd numbering-app
   ng serve
   ```
4. Access the application at `http://localhost:4200`

## API Endpoints

- POST `/atexo/api/numbering/configuration`: Configure numbering criteria
- POST `/atexo/api/numbering/generate`: Generate a unique number for a registered user

## Testing

- Backend tests: `mvn clean install`

## Project Structure

- `src/main/java/com/exercise/atexo/`: Java source files
    - `controller/`: REST controllers
    - `service/`: Business logic
    - `domain/`: Domain models
    - `mapper/`: DTO to Domain mappers
    - `exception/`: Custom exceptions
- `src/main/resources/`: Configuration files
- `src/test/`: Test files

## Configuration

The numbering system allows configuration of up to 4 criteria, including:

- First Name
- Last Name
- Birth Year
- Counter

Each criterion can be configured with a prefix, suffix, and length.

## Error Handling

The application includes custom error handling for various scenarios, including:

- Invalid configuration exceptions
- Number generation exceptions
- Configuration not found exceptions

## Frontend Features

- Responsive design using Tailwind CSS
- Real-time display of current configuration
- Form for creating and updating numbering configurations
- Interface for generating unique numbers based on user data

