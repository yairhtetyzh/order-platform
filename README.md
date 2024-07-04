Spring boot, Spring AOP, Spring Integration, MongoDB, Swagger UI

# order-platform
run the application first.

Once you get error response when fetch api getShopNear, run this creating index command first
db.shop.createIndex({ location: "2dsphere" })

# swagger
http://localhost:8080/swagger-ui/index.html

