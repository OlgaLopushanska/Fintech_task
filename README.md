This is my solution for the Java Test Task.
At the first start of the application, currencies table is created in privat_db database 
and information from the public API for privatbank, monobank and minfin is uploaded to it for today and for the last year in the currencies EUR, USD.
I chose these currencies because they are presented in all resources and the calculation of the average result will be representative. 
The public API that I used as resources reflected in the application.properties file, the API for the minfin needs to be changed because the token is already expired. 
In order for the application not to fail due to expired minfin token I commented out the lines associated with it in the CurrencyServiceUtils file, row 31, 41.
The uploading archive data is slow due to resource time-outs, I have used thread.sleep set to 1 minute to avoid exceptions.
I created 2 API to get information from database. You can visualize it with http://localhost:8080/swagger-ui/index.html.
First API http://localhost:8080/api/currencies/today, it retrieves currency rates from database for privat, mono, minfin and calculates average for each currency.
Second API http://localhost:8080/api/currencies/period/04.05.2023, it retrieves currency rates from database starting from today till the day that you write " +
"and calculates average for each currency and source.
It is necessary to write data it correct format such as dd.MM.yyyy or you would get custom exception.