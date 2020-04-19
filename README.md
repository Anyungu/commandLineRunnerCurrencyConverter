# CommandLineRunnerCurrencyConverter

### About
    A commandLine Application to convert from 1 currency to another
    
### Installation
    Add Java to your host machine
    Install maven to the host machine
    
### Usage/Testing
    clone this repository using: https://github.com/Anyungu/commandLineRunnerCurrencyConverter.git
    go to the project directory using cd command
    run the following command at the root of the project to install the app: mvn clean install
    now run this command to start the application: mvn spring-boot:run

### Process Flow Theory/Design Theory
    get the user input
    fetch the csv file online
    look for the user input from the contents of the file
    if input is present, convert to user language and to user currency
    else alert the user that the currency/language code is not available for usage
