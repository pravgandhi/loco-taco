#Loco Taco Ordering API

This is a taco ordering application providing a REST API to calculate total and apply discounts.

#How to run locally?

There are different ways to run it locally
	1) mvn spring-boot:run
	2) Run as OrderingServiceApplication
	3) If need, the application can be containerized using DockerFile

###Method: POST
http://localhost:8080/order/total

# Swagger UI
Access the below url to see API Specs
http://localhost:8080/swagger-ui.html

#H2 Database for storing menu
The application has inbuilt 
http://localhost:8080/h2-console


#POST /order/total

###Sample Request
{
	"orderId": "123",
	"details": [{
		"menuId": 1,
		"quantity": 2
	}, {
		"menuId": 3,
		"quantity": 1
	}]
}

###Menu Table
- 1		Veggie Taco		2.5
- 2		Chicken Taco	3.0
- 3 	Beef Taco		3.0
- 4 	Chorizo Taco	3.5
