# Dev Http Listener

This project creates a basic HTTP listener that can be used to develop and test connections to external APIs.

## Execution

To run the listener, build it using `mvn clean package` and then run it with `mvn exec:java`.  The port defaults to 8080, but can be altered at runtime by setting the `DEV_HTTP_PORT` environment variable.