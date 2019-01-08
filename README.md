### Implementation of Postgres LTree in Spring Boot app
#### First create the database and a super user manually 
```
CREATE DATABASE tmdb;
CREATE USER tmc WITH ENCRYPTED PASSWORD 'tmc';
GRANT ALL PRIVILEGES ON DATABASE tmdb TO tmc;
ALTER USER tmc WITH SUPERUSER;
```