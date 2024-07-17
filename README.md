# Email Microservice#

Its a generic email service which can be used by any other microservice to send an email.

### Microservice Configuration ###
* PORT - 8882
* Context - /email

### Swagger Url ###
* http://localhost:8882/email/swagger-ui.html#/

### Required Environment Variables  ###

* emailMS_MAIL_USERNAME 
* emailMS_MAIL_PASSWORD
* emailService_MAIL_PORT

* emailMS_DB_URL
* emailMS_DB_USERNAME
* emailMS_DB_PASSWORD

### Required VM Arguments  ###
-Denv=test