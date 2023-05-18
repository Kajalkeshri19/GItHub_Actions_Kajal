### Description:
Creating a CI/CD pipeline that will run postman collection using Newman, via GitHub Actions and generating the report as well and storing as an artifact which will get expired after 30 days.

## Steps:
Creating a yml file:
  
     1. Firstly Installing Newman
     > npm install -g newman-reporter-htmlextra

     2. Running Collection
     > newman run "collection name" --reporters cli,,htmlextra --reporter-htmlextra-export report.html
      
     3. Then storing the html report as artifact
     > actiond/upload-artifact@v3
     
     4. Atlast setting the retention period (which is optinal)
     > name: Report
       path: report.html
       retention-days: 30
     
      
