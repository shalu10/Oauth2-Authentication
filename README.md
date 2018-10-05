# Spring Boot Project !!!
Spring Boot project with OTP and Confirmation link verification.

## Check application running state
Route: **http://localhost:9085/**

## OAuth2 Authentication
### Get New Access Token <br>
    Type:OAuth2.0 
    Grant type:Password Credentials 
    Access token Url:http://localhost:9085/oauth/token
    Username:admin 
    Password:password 
    ClientId:fooClientIdPassword
    ClientPassword:password 
    
## Email Confirmation link verification<br>
1.Generate Confirmation link and send it to e-mail <br>
  Route: **localhost:9085/api/confirm?token=7c6ae716-849a-4a3f-89ce-c866afe9cd22**<br>
  Method: GET <br> 
  
2.validate <br>
  Route: **localhost:9085/api/confirm?token=7c6ae716-849a-4a3f-89ce-c866afe9cd22**<br>
  Method: POST <br> 
  Example Request Payload: { "password": "password" <br>
                            "enable": "true" }<br>
                            
 ## OTP Number verification<br>
 1.Generate OTP Number and send it to mobile<br>
  Route: **localhost:9999/verify/otp?token=8059**<br>
  Method: GET <br> 
  
2.validate <br>
  Route: **localhost:9999/verify/otp?token=8059**<br>
  Method: POST <br> 
  Example Request Payload: { "password": "password" <br>
                              "enable": "true" }<br>
