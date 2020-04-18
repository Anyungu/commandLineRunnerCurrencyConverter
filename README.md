# CommandLineRunnerCurrencyCheck

### About
    A commandLine Application to check the validity of a user currency based on a remote csv file
    
### Installation
    Add Java to your host machine
    Install maven to the host machine
    
### Usage/Testing
    clone this repository using: https://github.com/Anyungu/CommandLineRunnerCurrencyCheck.git
    go to the project directory using cd command
    run the following command at the root of the project to install the app: mvn clean install
    now run this command to start the application: mvn spring-boot:run

### Process Flow Theory/Design Theory
    get the user input
    fetch the csv file online
    look for the user input from the contents of the file
    if input is present, stop and return the fndings
    else alrt the user that the currency code is not available for usage
