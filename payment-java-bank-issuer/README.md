
`curl -X POST http://localhost:8080/api/v1/accounts -H "Content-Type: application/json" --data '{"id":"test"}'`

`curl http://localhost:8080/api/v1/accounts/test`

### How to run

`docker build -t bank-issuer .`

`docker run -p 127.0.0.1:8080:8080 -it bank-issuer`