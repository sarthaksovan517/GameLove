# Game Love API

The Game Love API is a RESTful service that allows users to track their favorite games and view the most loved games in the system.

## Features

- Create a new love entry for a player and a game.
- Remove a love entry for a player and a game.
- Fetch all games loved by a player.
- Fetch the most loved games with a specified limit.

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- H2 database
- Maven

## Getting Started

1. Clone or unzip the repository:

2. Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).

3. Configure your database connection in `application.yml`:

   ```
    datasource:
    url: jdbc:h2:file:./data/gamelove
    username: sa
    password: password
    driverClassName: org.h2.Driver
    jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
    ddl-auto: update
    defer-datasource-initialization: true
    h2:
    console:
    enabled: true
   ```

4. Build and run the project:

   ```
   mvn clean install
   mvn spring-boot:run
   ```

5. The API will be accessible at `http://localhost:8081/`.

## Assumptions


```
- There is an existing player table that contains valid playerIds.
- There is an existing game table that contains valid gameIds.
- At any time, a valid player is making a request to the API.

```


## API Key-Based Security

To ensure secure access to the API, include the following headers in each request:
```
 `X-API-KEY`: game-api-key
 `X-API-SECRET`: abcdegh
```
These headers will be used to authenticate and authorize access to the API endpoints.

## API Endpoints

- Create a love entry:
  - Endpoint: `POST /game-love/create`
  - Request body:
    ```json
    {
        "playerId": "player_id",
        "gameId": "game_id"
    }
    ```
  - Headers:
    - `X-API-KEY`: Your API key
    - `X-API-SECRET`: Your API secret
    

- Remove a love entry:
  - Endpoint: `DELETE /game-love/unlove`
  - Request parameters:
    - `playerId`: Player identifier
    - `gameId`: Game identifier
  - Headers:
      - `X-API-KEY`: Your API key
      - `X-API-SECRET`: Your API secret

- Fetch all games loved by a player:
  - Endpoint: `GET /game-love/loved-games/{playerId}`
  - Path parameter:
    - `playerId`: Player identifier
  - Headers:
      - `X-API-KEY`: Your API key
      - `X-API-SECRET`: Your API secret

- Fetch the most loved games with a specified limit:
  - Endpoint: `GET /game-love/most-loved-games?limit={limit}`
  - Query parameter:
    - `limit`: Number of top loved games to fetch
  - Headers:
      - `X-API-KEY`: Your API key
      - `X-API-SECRET`: Your API secret

## Database Schema

The database schema includes a `game_love` table with columns:
- `id` (Primary key)
- `player_id` (Player identifier)
- `game_id` (Game identifier)
- Unique constraint on the combination of `player_id` and `game_id`.

## Contributors

- Sarthak Pattnaik (heyu@dontsp.am)
