Spring boot, Spring AOP, Spring Integration, MongoDB

# order-platform
run the application first.

Once you get error response when fetch api getShopNear, run this creating index command first
db.shop.createIndex({ location: "2dsphere" })

