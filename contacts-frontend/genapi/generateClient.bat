java -jar openapi-generator-cli-6.1.0.jar generate -i http://localhost:8080/v3/api-docs -g typescript-axios -o ../src/apiclient -p useSingleRequestParameter=true -p supportsES6=true