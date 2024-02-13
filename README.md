# bank-customer-management-api
Backend of customer management API for bank system

1. Run application from CustomerManagementApplication.java
2. In memory MySql db will be generated from schema.sql and data will be inserted from data.sql
3. API endpoints:
   1. Get all customers: GET http://localhost:8080/customer
   2. Get customer by id: GET http://localhost:8080/customer/{id}
   3. Create customer: POST http://localhost:8080/customer
   4. Update customer: PUT http://localhost:8080/customer/{id}
   5. Delete customer: DELETE http://localhost:8080/customer/{id}
   6. Get account by id: GET http://localhost:8080/account/{id}
   7. Create account: POST http://localhost:8080/account
   8. Update account: PUT http://localhost:8080/account/{id}
   9. Delete account: DELETE http://localhost:8080/account/{id}
   10. Get account balance: GET http://localhost:8080//account/balance/{account_number}
   11. Withdraw cash: PUT http://localhost:8080//account/balance/{account_number}/withdraw?amount={amount}
   12. Deposit cash: PUT http://localhost:8080//account/balance/{account_number}/deposit?amount={amount}
