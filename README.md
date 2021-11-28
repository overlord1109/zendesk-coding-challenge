# zendesk-coding-challenge
My submission for Zendesk Engineering Intern Coding challenge

### Prerequisites
- Java JDK version 8 or higher (check by running `java -version`)

### Steps to run
- Clone this project
- Navigate to project folder and run `./gradlew --console=plain run --args='<your-email> <your-token>'`

Please replace \<your-email\> and \<your-token\> with your email and token. This ticket viewer works for https://zccstudents1109.zendesk.com. To use it for your account, please replace `zendesk_base_url` in `app/src/main/resources/application.properties`
