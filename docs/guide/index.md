
# 1. How to Run

1. **Clone Repository:** 
```bash
git clone https://github.com/joeaoregan/TUS-26-MA-CA1-Guitar-Store-API.git
```
2. **Environment Setup:** Create a .env file in the root directory (Project uses java-dotenv).
3. **Run:** (requires Maven)
```bash
./mvnw spring-boot:run
``` 
4. **Local Access Points:**
- **Dashboard:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
- **Test Page:** `http://localhost:8080/hello` (Test project running OK)
- **H2 DB Console:** `http://localhost:8080/h2-console` (Check in-memory database)

The database is automatically pre-populated using `data.sql` for repeatable testing.
