# reward-point-service
A sample service written in java with usage of spring boot.


## Requirements
1. java 11
2. maven

## Build project:
`mvn clean install`

## Run
`java -jar target/reward-point-service-0.0.1-SNAPSHOT.jar`

## Endpoints
### Add new financial transaction:  
`POST /transaction `  
Example:  
`curl --location --request POST 'http://localhost:8080/transaction'  
--header 'Content-Type: application/json'  
--data-raw '{
    "customerId": "customer1",
    "amount": "112.11",
    "createDateTime": "2021-06-01T10:10:00"
}'`  

### Calculate and get reward points for customer:
`GET /customer/{customerId}/rewardpoints`  
Example:  
`curl --location --request GET 'http://localhost:8080/customer/customer1/rewardpoints'`  
Examle response  
`{
    "customerId": "customer1",
    "points": {
        "total": 220,
        "monthly": {
            "2021-06": 74,
            "2021-07": 120,
            "2021-08": 26
        }
    }
}`

